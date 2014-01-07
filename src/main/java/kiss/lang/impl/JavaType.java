package kiss.lang.impl;

import kiss.lang.Type;

@SuppressWarnings("rawtypes")
public class JavaType extends Type {
	private final Class klass;
	
	public JavaType(Class c) {
		klass=c;
	}

	@Override
	public boolean checkInstance(Object o) {
		return klass.isInstance(o);
	}

	@Override
	public Class getJavaType() {
		return klass;
	}
}
