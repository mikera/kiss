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
	
	public static <T> JavaType<T> create(Class<T> c) {
		return new JavaType<T>(c);
	}
	

	@Override
	public boolean checkInstance(Object o) {
		return klass.isInstance(o);
	}

	@Override
	public Class<T> getJavaType() {
		return klass;
	}

	@Override
	public boolean contains(Type t) {
		if (t instanceof JavaType) {
			JavaType<?> jt=(JavaType<?>)t;
			if (klass==jt.klass) return true;
			return klass.isAssignableFrom(jt.klass);
		} else {
			// TODO: check logic
			// not a Java type, so can't contain?
			return false;
		}
	}


}
