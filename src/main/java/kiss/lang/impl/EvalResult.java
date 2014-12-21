package kiss.lang.impl;

import kiss.lang.Environment;
import kiss.lang.Result;

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
	@Override
	public boolean isExiting() {
		return value instanceof IExitResult;
	}
	
	@Override
	public Object getResult() {
		return value;
	}
}
