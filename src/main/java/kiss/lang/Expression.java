package kiss.lang;

public abstract class Expression {

	public abstract Type getType(); 
	
	/**
	 * Evaluate an expression within an environment, interpreter style
	 * 
	 * @param e
	 * @return
	 */
	public abstract Object eval(Environment e);
}
