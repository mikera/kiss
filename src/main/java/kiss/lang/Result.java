package kiss.lang;

public class Result {
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
}
