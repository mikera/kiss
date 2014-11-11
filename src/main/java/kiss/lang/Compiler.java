package kiss.lang;

import kiss.lang.impl.LambdaFn;

/**
 * Kiss compiler
 * 
 * @author Mike
 *
 */
public class Compiler {

	public static KFn compile(Environment e, Expression ex) {
		// TODO: handle macro expansion here? does this need an environment?
		Expression opt = ex.optimise();
		return LambdaFn.create(e, opt, Symbols.EMPTY_SYMBOL_ARRAY);
	}
	
	public static KFn compile(Expression ex) {
		return compile(Environment.EMPTY,ex);
	}
}
