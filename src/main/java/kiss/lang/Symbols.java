package kiss.lang;

import clojure.lang.Symbol;

/**
 * Static class containing Kiss symbols for reserved words
 *  
 * @author Mike
 *
 */
public class Symbols {

	public static final Symbol LET= Symbol.intern("let");
	public static final Symbol FN= Symbol.intern("fn");
	public static final Symbol IF = Symbol.intern("if");
	public static final Symbol NIL = Symbol.intern("nil");
	public static final Symbol TRUE = Symbol.intern("true");
	public static final Symbol FALSE = Symbol.intern("false");
	public static final Symbol DEF = Symbol.intern("def");
	public static final Symbol DO = Symbol.intern("do");
}
