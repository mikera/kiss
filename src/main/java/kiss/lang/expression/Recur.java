package kiss.lang.expression;

import kiss.lang.Environment;
import kiss.lang.Expression;
import kiss.lang.Result;
import kiss.lang.Type;
import kiss.lang.impl.EvalResult;
import kiss.lang.impl.KissException;
import kiss.lang.impl.RecurResult;
import kiss.lang.type.Nothing;
import clojure.lang.IPersistentMap;
import clojure.lang.IPersistentSet;

/**
 * A recursion in tail position
 * 
 * @author Mike
 */
public class Recur<T> extends Expression {
	private final Expression[] values;
	
	private Recur(Expression[] values) {
		this.values=values;
	}
	
	public static <T> Recur<T> create(Expression[] values) {
		return new Recur<T>(values);
	}
	
	@Override
	public Type getType() {
		return Nothing.INSTANCE;
	}
	
	@Override
	public boolean isConstant() {
		return true;
	}
	
	@Override
	public boolean isPure() {
		return false;
	}

	@Override
	public Object eval(Environment e) {
		throw new KissException("Can't evaluate recur");
	}

	@Override
	public Result interpret(Environment d, IPersistentMap bindings) {
		int n=values.length;
		Object[] rs=new Object[n];
		for (int i=0; i<n; i++) {
			Result t=values[i].interpret(d, bindings);
			if (t.isExiting()) return t;
			d=t.getEnvironment();
			rs[i]=t.getResult();
		}
		return new RecurResult(d,rs);
	}

	@Override
	public Expression specialise(Type type) {
		return this;
	}
	
	@Override
	public IPersistentSet accumulateFreeSymbols(IPersistentSet s) {
		return s;
	}

	@Override
	public Expression substitute(IPersistentMap bindings) {
		return this;
	}
	
	@Override
	public void validate() {
		// TODO: anything to validate?
	}

}
