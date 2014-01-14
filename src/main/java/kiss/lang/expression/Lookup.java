package kiss.lang.expression;

import java.util.Map.Entry;

import kiss.lang.Environment;
import kiss.lang.Expression;
import kiss.lang.Type;
import kiss.lang.impl.KissException;
import kiss.lang.type.Anything;
import clojure.lang.IPersistentCollection;
import clojure.lang.IPersistentMap;
import clojure.lang.IPersistentSet;
import clojure.lang.RT;
import clojure.lang.Symbol;
import clojure.lang.Var;

public class Lookup extends Expression {
	private final Symbol sym;
	
	private Lookup(Symbol sym) {
		this.sym=sym;
	}

	public static Expression create(Symbol symbol) {
		return new Lookup(symbol);
	}
	
	public static Expression create(String symName) {
		return create(Symbol.intern(symName));
	}

	@Override
	public Type getType() {
		return Anything.INSTANCE;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Environment compute(Environment e, IPersistentMap bindings) {
		Entry<Symbol, Object> lb=bindings.entryAt(sym);
		if (lb!=null) return e.withResult(lb.getValue());

		Entry<Symbol, Object> o=e.entryAt(sym);
		if (o!=null) return e.withResult(o.getValue());
		 
		try {
			Var v=RT.var(sym.getNamespace(),sym.getName());
			if (v!=null) return e.withResult(v.deref());
		} catch (Throwable t) {
			String err="Error trying to lookp var "+sym+" "; 
			err+=" with Environment "+e.toString();
			throw new KissException(err,t);
		}
		
		throw new KissException("Cannot lookup symbol "+sym+" in environment");
	}

	@Override
	public Expression specialise(Type type) {
		return Cast.create(type, this);
	}
	
	@Override
	public Expression substitute(IPersistentMap bindings) {
		if(bindings.containsKey(sym)) {
			return Constant.create(bindings.valAt(sym));
		}
		return this;
	}
	
	@Override
	public IPersistentSet getFreeSymbols(IPersistentSet s) {
		s=(IPersistentSet) ((IPersistentCollection)s).cons(sym);
		return s;
	}


}
