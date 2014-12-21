package kiss.lang.expression;

import kiss.lang.Environment;
import kiss.lang.EvalResult;
import kiss.lang.Expression;
import kiss.lang.Type;
import kiss.lang.impl.KissException;
import kiss.lang.impl.ReturnResult;
import kiss.lang.type.Nothing;
import clojure.lang.IPersistentMap;
import clojure.lang.IPersistentSet;

/**
 * A function return.
 * 
 * Executing a return statement returns from the current function with the given value.
 * 
 * @author Mike
 */
public class Return<T> extends Expression {
	private final Expression value;
	
	private Return(Expression value) {
		this.value=value;
	}
	
	public static <T> Return<T> create(Expression value) {
		return new Return<T>(value);
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
		throw new KissException("Can't evaluate return");
	}

	@Override
	public EvalResult interpret(Environment d, IPersistentMap bindings) {
		EvalResult r=value.interpret(d, bindings);
		if (r.isExiting()) return r;
		Object o=r.getResult();
		EvalResult re = r.withResult(new ReturnResult(o));
		return re;
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
