package kiss.lang;

public abstract class Expression {

	public abstract Type getType(); 
	
	public abstract Object eval(Environment e);
}
