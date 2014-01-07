package kiss.lang.impl;

import kiss.lang.Type;

/**
 * The type of the value null
 * 
 * @author Mike
 *
 */
public class NullType extends Type {

	public static final Type INSTANCE = new NullType();

	@Override
	public boolean checkInstance(Object o) {
		return o==null;
	}

	@Override
	public Class<?> getJavaType() {
		return Void.TYPE;
	}

}
