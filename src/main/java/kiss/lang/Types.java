package kiss.lang;

import kiss.lang.type.Anything;
import kiss.lang.type.JavaType;
import kiss.lang.type.Nothing;

/**
 * Static constant types
 * 
 * @author Mike
 */
public class Types {
	public static final Type NUMBER=JavaType.create(Number.class);
	public static final Type STRING=JavaType.create(String.class);
	public static final Type BOOLEAN=JavaType.create(Boolean.class);

	public static final Type ANYTHING=Anything.INSTANCE;
	public static final Type NOTHING=Nothing.INSTANCE;
}
