package kiss.lang.expression;

import kiss.lang.Environment;
import kiss.lang.Expression;
import kiss.lang.Type;
import kiss.lang.impl.KissException;
import kiss.lang.type.Anything;
import clojure.lang.IPersistentMap;
import clojure.lang.IPersistentSet;
import clojure.lang.RT;
import clojure.lang.Symbol;
import clojure.lang.Var;

/**
 * An expression representing a lookup in the current Kiss Environment
 * @author Mike
 *
 */
public class ClojureLookup extends Expression {
	private final Symbol sym;
	
	private ClojureLookup(Symbol sym) {
		this.sym=sym;
	}

	public static Expression create(Symbol symbol) {
		return new ClojureLookup(symbol);
	}
	
	public static Expression create(String symName) {
		return create(Symbol.intern(symName));
	}

	@Override
	public Type getType() {
		return Anything.INSTANCE;
	}

	@Override
	public Environment compute(Environment e, IPersistentMap bindings) {
		try {
			Var v=RT.var(sym.getNamespace(),sym.getName());
			if (v!=null) return e.withResult(v.deref());
		} catch (Throwable t) {
			String err="Error trying to lookp var "+sym+" "; 
			err+=" with Environment "+e.toString();
			throw new KissException(err,t);
		}
		
		throw new KissException("Cannot find Clojure symbol "+sym+" in environment");
	}

	@Override
	public Expression specialise(Type type) {
		return Cast.create(type, this);
	}
	
	@Override
	public Expression substitute(IPersistentMap bindings) {
		return this;
	}
	
	@Override
	public IPersistentSet accumulateFreeSymbols(IPersistentSet s) {
		return s;
	}

	@Override
	public void validate() {
		// OK?
	}


}
