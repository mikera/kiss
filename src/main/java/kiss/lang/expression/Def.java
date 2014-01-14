package kiss.lang.expression;

import clojure.lang.IPersistentMap;
import clojure.lang.IPersistentSet;
import clojure.lang.Symbol;
import kiss.lang.Environment;
import kiss.lang.Expression;
import kiss.lang.Type;

/**
 * A kiss "def" expression.
 * 
 * Alters the current immutable environment, binding the specified symbol to a new Expression
 * 
 * The result is the value of the new Expression
 * 
 * @author Mike
 *
 */
public class Def extends Expression {

	private final Symbol sym;
	private final Expression body;
	
	private Def(Symbol sym, Expression body) {
		this.sym=sym;
		this.body=body;
	}

	public static Def create(Symbol sym, Expression body) {
		return new Def(sym,body);
	}
	
	@Override
	public Type getType() {
		return body.getType();
	}

	@Override
	public Expression specialise(Type type) {
		Expression b=body.specialise(type);
		if ((b==body)||(b==null)) return this;
		return new Def(sym,b);
	}

	@Override
	public Environment compute(Environment d, IPersistentMap bindings) {
		return d.define(sym,body,bindings);
	}
	
	@Override
	public IPersistentSet getFreeSymbols(IPersistentSet s) {
		s=body.getFreeSymbols(s);
		return s;
	}
	
	@Override
	public Expression substitute(IPersistentMap bindings) {
		Expression nBody=body.substitute(bindings);
		if (nBody==body) return this;
		if (nBody==null) return null;
		return create(sym,nBody);
	}

	@Override
	public void validate() {
		// OK?
	}

}
