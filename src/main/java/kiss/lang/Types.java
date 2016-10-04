package kiss.lang;

import kiss.lang.type.Anything;
import kiss.lang.type.JavaType;
import kiss.lang.type.Nothing;
import kiss.lang.type.Null;

/**
 * Static constant types
 * 
 * @author Mike
 */
public class Types {
	public static final JavaType<?> NUMBER = JavaType.NUMBER;
	public static final JavaType<?> STRING = JavaType.STRING;
	public static final JavaType<?> BOOLEAN = JavaType.BOOLEAN;
	public static final JavaType<?> SYMBOL = JavaType.SYMBOL;
	public static final JavaType<?> KEYWORD = JavaType.KEYWORD;

	public static final Anything ANYTHING = Anything.INSTANCE;
	public static final Nothing NOTHING = Nothing.INSTANCE;
	public static final Null NULL = Null.INSTANCE;
	public static final Type TYPE = JavaType.KISS_TYPE;
}
