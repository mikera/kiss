package kiss.lang;

public abstract class Expression {

	public abstract Type getType(); 
	
	/**
	 * Evaluate an expression within an environment, interpreter style
	 * 
	 * @param e
	 * @return
	 */
	public Object eval(Environment e) {
		return compute(e).getResult();
	}
	
	/**
	 * Compute the effect of this expression, returning a new Environment
	 */
	public abstract Environment compute(Environment d);
}
