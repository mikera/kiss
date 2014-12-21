package kiss.lang.impl;

import kiss.lang.Environment;

/**
 * Class representing a return statement result.
 * 
 * @author Mike
 *
 */
public class ReturnResult extends ExitResult {
	public Object value;
	
	public ReturnResult(Environment env, Object value) {
		super(env);
		this.value=value;
	}

	public String toString() {
		return "(ReturnResult "+value.toString()+")";
	}
}
