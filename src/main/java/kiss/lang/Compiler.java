package kiss.lang;

/**
 * Kiss compiler
 * 
 * @author Mike
 *
 */
public class Compiler {

	public static Expression compile(Environment e, Object form) {
		// TODO: handle macro expansion here? does this need an environment?
		Expression ex=Analyser.analyse(form);
		Expression opt = ex.optimise();
		return opt;
	}
}
