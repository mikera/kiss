package kiss.lang;

import clojure.lang.Symbol;
import kiss.lang.expression.Constant;
import kiss.lang.expression.Lookup;

/**
 * Kiss language analyser
 * 
 * @author Mike
 */
public class Analyser {

	public static Expression analyse(Object form) {
		if (form instanceof Symbol) return Lookup.create((Symbol)form);
		return Constant.create(form);
	}
}
