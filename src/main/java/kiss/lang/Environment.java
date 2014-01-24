package kiss.lang;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import kiss.lang.expression.Constant;
import kiss.lang.impl.KissException;
import kiss.lang.impl.KissUtils;
import clojure.lang.APersistentMap;
import clojure.lang.IMapEntry;
import clojure.lang.IPersistentCollection;
import clojure.lang.IPersistentMap;
import clojure.lang.IPersistentSet;
import clojure.lang.ISeq;
import clojure.lang.PersistentHashMap;
import clojure.lang.PersistentHashSet;
import clojure.lang.RT;
import clojure.lang.Symbol;

/**
 * This is the immutable environment used by the Kiss compiler
 * 
 * Design intent: 
 *  - Behaves like an immutable map of Symbols -> Values
 *  - Maintains a dependency graph for recompilation
 *  - Maintains a "result" value, so it can include an expression return value
 *  
 * It is a first class object, but probably shouldn't be messed with outside of the kiss.core functions.
 * 
 * @author Mike
 *
 */
public final class Environment extends APersistentMap {
	private static final long serialVersionUID = -2048617052932290067L;
	
	public static final Environment EMPTY = new Environment();
	
	public final IPersistentMap map; // Symbol -> Mapping 
	public final IPersistentMap deps; // Symbol -> set of Symbols
	public final IPersistentMap backDeps; // Symbol -> set of Symbols
	public final Object result;

	private Environment() {
		this(PersistentHashMap.EMPTY,PersistentHashMap.EMPTY,PersistentHashMap.EMPTY,null);
	}
	
	private Environment(IPersistentMap map, IPersistentMap deps, IPersistentMap backDeps, Object result) {
		this.map=map;
		this.result=result;
		this.deps=deps;
		this.backDeps=backDeps;
	}
	
	public Environment withResult(Object value) {
		if (value==result) return this;
		return new Environment(map,deps,backDeps,value);
	}
	
	/**
	 * Redefine a symbol in this environment to a new Expression.
	 * 
	 * @param key
	 * @param body
	 * @return
	 */
	public Environment define(Symbol key, Expression body) {
		return define(key,body,PersistentHashMap.EMPTY);
	}
	
	public Environment define(Symbol key, Expression body, IPersistentMap bindings) {
		// handle define with local bindings
		if (bindings.count()>0) {
			Expression newBody=body.substitute(bindings);
			if (newBody!=body) newBody=newBody.optimise(); // re-optimise if needed
			body=newBody;
		}
		
		// manage dependency updates
		IPersistentMap deps=this.deps;
		IPersistentMap backDeps=this.backDeps;
		
		IPersistentSet free=body.accumulateFreeSymbols(PersistentHashSet.EMPTY);
		
		IPersistentSet oldDeps=(IPersistentSet) deps.valAt(key);
		if ((oldDeps==null)) oldDeps=PersistentHashSet.EMPTY;

		deps=deps.assoc(key, free);
		backDeps=updateBackDeps(key,backDeps,oldDeps,free);
		
		// check if free variables can be satisfied from the current environment 
		for (ISeq s=RT.seq(free);s!=null; s=s.next()) {
			Symbol sym=(Symbol) s.first();
			if (map.containsKey(sym)) {
				free=free.disjoin(sym);
			}
		}
		
		if (free.count()==0) {
			Environment newEnv=body.compute(this, bindings);
			Object value=newEnv.getResult();
			return new Environment(map.assoc(key, Mapping.createExpression(body, value)),deps,backDeps,value);
		} else {
			return new Environment(map.assoc(key, Mapping.createExpression(body, null)),deps,backDeps,null);	
		}
	}
	

	private IPersistentMap updateBackDeps(Symbol key,IPersistentMap backDeps,
			IPersistentSet oldDeps, IPersistentSet newDeps) {
		if (oldDeps==newDeps) return backDeps;
		
		// add new back dependencies
		for (ISeq s=newDeps.seq(); s!=null; s=s.next()) {
			Symbol sym=(Symbol)s.first();
			if (oldDeps.contains(sym)) continue;
			IPersistentSet bs=(IPersistentSet) backDeps.valAt(sym);
			if (bs==null) bs=PersistentHashSet.EMPTY;
			backDeps=backDeps.assoc(sym, bs.cons(key));
		}
		
		// remove old back dependencies
		for (ISeq s=oldDeps.seq(); s!=null; s=s.next()) {
			Symbol sym=(Symbol)s.first();
			if (newDeps.contains(sym)) continue;
			IPersistentSet bs=(IPersistentSet) backDeps.valAt(sym);
			bs=bs.disjoin(key);
			if (bs.count()==0) {
				backDeps=backDeps.without(sym);
			} else {
				backDeps=backDeps.assoc(sym, bs);
			}
		}
		
		return backDeps;
	}

	@Override
	public IPersistentMap without(Object key) {
		Mapping m=getMapping(key);
		if (m==null) return this;
		return new Environment(map.without(key),deps,backDeps, result);
	}
	
	@Override
	public Environment assoc(Object key, Object val) {
		return define((Symbol) key,Constant.create(val));
	}
	

	@Override
	public Environment assocEx(Object key, Object val) {
		return define((Symbol) key,Constant.create(val));
	}

	
	public Mapping getMapping(Object key) {
		return (Mapping)map.valAt(key);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Iterator<?> iterator() {
		return new EnvioronmentIterator(map.iterator());
	}
	
	private static final class EnvioronmentIterator implements Iterator<Entry<Symbol,Object>> {
		final Iterator<Entry<Symbol,Mapping>> source;
		
		private EnvioronmentIterator(Iterator<Entry<Symbol,Mapping>> vs) {
			this.source=vs;
		}
		
		@Override
		public boolean hasNext() {
			return source.hasNext();
		}

		@SuppressWarnings("unchecked")
		@Override
		public Entry<Symbol,Object> next() {
			Entry<Symbol,Mapping> entry=source.next();
			Mapping m=entry.getValue();
			return m.toMapEntry(entry.getKey());
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException("Immutable!");
		}		
	}
	
	/**
	 * Merges a second environment into this one
	 * @param e
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Environment merge(Environment e) {
		Environment result=this;
		for (Object o : e.map) {
			Map.Entry<Symbol, Mapping> ent=(Entry<Symbol, Mapping>) o;
			result=result.define(ent.getKey(), ent.getValue().getExpression());
		}
		return result;
	}

	@Override
	public boolean containsKey(Object key) {
		return map.containsKey(key);
	}

	@Override
	public IMapEntry entryAt(Object key) {
		Mapping m=getMapping(key);
		if (m==null) return null;
		return m.toMapEntry(key);
	}

	@Override
	public int count() {
		return map.count();
	}

	@Override
	public Environment empty() {
		return EMPTY;
	}

	@Override
	public ISeq seq() {
		return clojure.lang.IteratorSeq.create(iterator());
	}

	@Override
	public Object valAt(Object key) {
		Mapping m=getMapping(key);
		if (m==null) return null;
		return m.getValue();
	}

	@Override
	public Object valAt(Object key, Object notFound) {
		Mapping m=getMapping(key);
		if (m==null) return notFound;
		return m.getValue();
	}

	public Object getResult() {
		return result;
	}

	@SuppressWarnings("unchecked")
	public void validate() {
		for (Object o : map) {
			Map.Entry<Symbol, Mapping> ent=(Entry<Symbol, Mapping>) o;
			Symbol key=ent.getKey();
			Mapping m=ent.getValue();
			if (m==null) throw new KissException("Unexcpected null mapping for symbol: "+key);
			IPersistentSet free=m.getExpression().accumulateFreeSymbols(PersistentHashSet.EMPTY);
			IPersistentSet ds=(IPersistentSet) deps.valAt(key);
			if (!free.equiv(ds)) {
				throw new KissException("Mismatched dependencies for symbol: "+key+" free="+free+" deps="+ds);
			}
			
			for (ISeq s=ds.seq(); s!=null; s=s.next()) {
				Symbol sym=(Symbol)s.first();
				IPersistentSet bs=(IPersistentSet) backDeps.valAt(sym);
				if (!bs.contains(key)) throw new KissException("Missing back dependency from "+sym+"=>"+key);
			}
		}
	}
}
