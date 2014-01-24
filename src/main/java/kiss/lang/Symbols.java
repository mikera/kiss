package kiss.lang;

import clojure.lang.Symbol;

/**
 * Static class containing Kiss symbols for reserved words
 *  
 * @author Mike
 *
 */
public class Symbols {
	// primitive expressions
	public static final Symbol LET= Symbol.intern("let");
	public static final Symbol FN= Symbol.intern("fn");
	public static final Symbol IF = Symbol.intern("if");
	public static final Symbol NIL = Symbol.intern("nil");
	public static final Symbol TRUE = Symbol.intern("true");
	public static final Symbol FALSE = Symbol.intern("false");
	public static final Symbol DEF = Symbol.intern("def");
	public static final Symbol DO = Symbol.intern("do");
	public static final Symbol CAST = Symbol.intern("cast");
	
	
	// type symbols
	public static final Symbol U = Symbol.intern("U");
	public static final Symbol I = Symbol.intern("I");
	public static final Symbol ANY = Symbol.intern("Any");
	public static final Symbol NOTHING = Symbol.intern("Nothing");
	public static final Symbol VALUE = Symbol.intern("Value");
	public static final Symbol FN_TYPE = Symbol.intern("Fn");
}
