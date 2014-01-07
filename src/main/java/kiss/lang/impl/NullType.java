package kiss.lang.impl;

import kiss.lang.Type;

public class NullType extends Type {

	public static final Type INSTANCE = new NullType();

	@Override
	public boolean checkInstance(Object o) {
		return o==null;
	}

	@Override
	public Class getJavaType() {
		return Void.TYPE;
	}

}
