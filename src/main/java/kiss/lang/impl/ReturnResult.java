package kiss.lang.impl;

/**
 * Class representing a return statement result.
 * 
 * @author Mike
 *
 */
public class ReturnResult implements IExitResult {
	public Object value;
	
	public ReturnResult(Object value) {
		this.value=value;
	}

	public String toString() {
		return "(ReturnResult "+value.toString()+")";
	}
}
