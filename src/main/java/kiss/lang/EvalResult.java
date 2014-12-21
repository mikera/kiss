package kiss.lang;

import kiss.lang.impl.IExitResult;

/**
 * Base class for evaluation result types
 */
public final class EvalResult extends Result {
	protected final Object value;

	public EvalResult(Environment env, Object value) {
		super(env);
		this.value=value;
	}
	
	public EvalResult(Environment env) {
		this(env,null);
	}

	public EvalResult() {
		this(Environment.EMPTY,null);
	}

	/**
	 * Returns true if the current result is an exit condition. This includes recur and return conditions.
	 * 
	 * @return
	 */
	public boolean isExiting() {
		return value instanceof IExitResult;
	}
	
	
	public Object getResult() {
		return value;
	}

	public EvalResult withResult(Object a) {
		return new EvalResult(env,a);
	}

}
