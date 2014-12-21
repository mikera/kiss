package kiss.lang;

import kiss.lang.impl.EvalResult;

/**
 * Abstract base class for evaluation results.
 * 
 * Encapsulates the resulting environment, plus any result information
 * @author Mike
 *
 */
public abstract class Result {
	protected final Environment env;

	public Result(Environment env) {
		this.env=env;
	}
	
	public Environment getEnvironment() {
		return env;
	}

	public void validate() {
		env.validate();
	}
	
	public abstract Object getResult();

	public abstract boolean isExiting();
	
	public final EvalResult withResult(Object a) {
		return new EvalResult(env,a);
	}
}
