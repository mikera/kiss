package kiss.lang;

import kiss.lang.expression.Constant;
import kiss.lang.expression.Let;
import kiss.lang.expression.Lookup;
import clojure.lang.IPersistentVector;
import clojure.lang.ISeq;
import clojure.lang.RT;
import clojure.lang.Symbol;

/**
 * Kiss language analyser
 * 
 * @author Mike
 */
public class Analyser {

	/**
	 * Converts a Kiss form into an Expression
	 * 
	 * @param form
	 * @return
	 */
	public static Expression analyse(Object form) {
		if (form instanceof Symbol) return Lookup.create((Symbol)form);
		if (form instanceof ISeq) return analyseList((ISeq)form);
		return Constant.create(form);
	}

	private static Expression analyseList(ISeq form) {
		if (form.count()==0) return Constant.create(form);
		Object first=form.first();
		
		if (first instanceof Symbol) {
			Symbol s=(Symbol)first;
			if (s.equals(Symbols.LET)) {
				IPersistentVector v=KissUtils.expectVector(RT.second(form));
				Symbol sym=KissUtils.expectSymbol(v.nth(0));
				
				return Let.create(sym, analyse(v.nth(1)), analyse(RT.nth(form, 2)));
			}
		}
		
		throw new KissException("Unexpected form: "+form);
	}
}
