package kiss.lang;

/**
 * Kiss compiler
 * 
 * @author Mike
 *
 */
public class Compiler {

	public static Expression compile(Environment e, Expression ex) {
		// TODO: handle macro expansion here? does this need an environment?
		Expression opt = ex.optimise();
		return opt;
	}
	
	public static Expression compile(Expression ex) {
		return compile(Environment.EMPTY,ex);
	}
}
