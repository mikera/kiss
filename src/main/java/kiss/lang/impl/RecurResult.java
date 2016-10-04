package kiss.lang.impl;

import java.util.Arrays;

import kiss.lang.Environment;

public class RecurResult extends ExitResult {
	public Object[] values;
	
	public RecurResult(Environment env, Object[] values) {
		super(env);
		this.values=values;
	}

	@Override
	public String toString() {
		return "(RecurResult "+Arrays.toString(values)+")";
	}

}
