package kiss.lang.impl;

public class RecurResult implements IExitResult {
	public Object[] values;
	
	public RecurResult(Object[] values) {
		this.values=values;
	}

}
