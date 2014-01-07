package kiss.lang;

import kiss.lang.expression.Constant;

/**
 * Kiss language analyser
 * 
 * @author Mike
 */
public class Analyser {

	public static Expression analyse(Object form) {
		return Constant.create(form);
	}
}
