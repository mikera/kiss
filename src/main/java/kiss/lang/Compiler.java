package kiss.lang;

/**
 * Kiss compiler
 * 
 * @author Mike
 *
 */
public class Compiler {

	public static Expression compile(Object form) {
		Expression ex=Analyser.analyse(form);
		return ex.optimise();
	}
}
