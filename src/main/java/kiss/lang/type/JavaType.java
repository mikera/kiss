package kiss.lang.type;

import kiss.lang.Type;

/**
 * JavaType represents the type of non-null values that are of a specific Java type.
 * 
 * @author Mike
 *
 */
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
	public Class<?> getJavaType() {
		return klass;
	}
}
