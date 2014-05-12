package kiss.lang.impl;

import java.util.Arrays;

public class RecurResult implements IExitResult {
	public Object[] values;
	
	public RecurResult(Object[] values) {
		this.values=values;
	}

	public String toString() {
		return "(RecurResult "+Arrays.toString(values)+")";
	}
}
