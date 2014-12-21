package kiss.lang.impl;

import kiss.lang.Environment;
import kiss.lang.Result;

public abstract class ExitResult extends Result {
	
	public ExitResult(Environment env) {
		super(env);
	}

	@Override
	public boolean isExiting() {
		return true;
	}
	
	@Override
	public final Object getResult() {
		throw new UnsupportedOperationException("Can't get result value from ExitResult");
	}
}
