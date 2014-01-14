package kiss.lang;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import kiss.lang.expression.Constant;
import clojure.lang.APersistentMap;
import clojure.lang.IMapEntry;
import clojure.lang.IPersistentCollection;
import clojure.lang.IPersistentMap;
import clojure.lang.IPersistentSet;
import clojure.lang.ISeq;
import clojure.lang.PersistentHashMap;
import clojure.lang.PersistentHashSet;
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
		if (bindings.count()>0) {
			body=body.substitute(bindings);
			body=body.optimise();
		}
		
		IPersistentSet free=body.getFreeSymbols(PersistentHashSet.EMPTY);
		
		if (free.count()==0) {
			Environment newEnv=body.compute(this, bindings);
			Object value=newEnv.getResult();
			return new Environment(map.assoc(key, Mapping.createExpression(Constant.create(value), value)),deps,backDeps,value);
			
		} else {
			// partial expression count
			Environment newEnv=body.compute(this, bindings);
			Object value=newEnv.getResult();
			return new Environment(map.assoc(key, Mapping.createExpression(Constant.create(value), value)),deps,backDeps,value);
		}
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


}
