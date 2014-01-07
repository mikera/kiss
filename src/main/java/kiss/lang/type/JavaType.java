package kiss.lang.type;

import kiss.lang.Type;

/**
 * JavaType represents the type of non-null values that are of a specific Java type.
 * 
 * @author Mike
 *
 */
public class JavaType<T> extends Type {
	private final Class<T> klass;
	
	public JavaType(Class<T> c) {
		klass=c;
	}
	
	public static <T> JavaType<T> analyse(T val) {
		return new JavaType<T>((Class<T>) val.getClass());
	}
	

	@Override
	public boolean checkInstance(Object o) {
		return klass.isInstance(o);
	}

	@Override
	public Class<T> getJavaType() {
		return klass;
	}
}
