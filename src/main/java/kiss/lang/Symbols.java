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
	public static final Symbol INSTANCE = Symbol.intern("instance?");	
	public static final Symbol VAR = Symbol.intern("var");	
	public static final Symbol LOOP = Symbol.intern("loop");	
	public static final Symbol RECUR = Symbol.intern("recur");	
	public static final Symbol QUOTE = Symbol.intern("quote");	
	public static final Symbol TRY = Symbol.intern("try");	
	public static final Symbol THROW = Symbol.intern("throw");	
	public static final Symbol NEW = Symbol.intern("new");	
	public static final Symbol SET_BANG = Symbol.intern("set!");	
	public static final Symbol DOT = Symbol.intern(".");	
	public static final Symbol DOTDOT = Symbol.intern("..");	
	
	
	// type symbols
	public static final Symbol U = Symbol.intern("U");
	public static final Symbol I = Symbol.intern("I");
	public static final Symbol ANY = Symbol.intern("Any");
	public static final Symbol NOTHING = Symbol.intern("Nothing");
	public static final Symbol VALUE = Symbol.intern("Value");
	public static final Symbol FN_TYPE = Symbol.intern("Fn");

}
