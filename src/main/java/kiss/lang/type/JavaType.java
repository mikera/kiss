package kiss.lang.type;

import kiss.lang.Type;

/**
 * JavaType represents the type of non-null values that are of a specific Java type.
 * 
 * @author Mike
 *
 */
public class JavaType<T> extends Type {
	final Class<T> klass;
	
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
		return (o!=null)&&klass.isInstance(o);
	}

	@Override
	public Class<T> getJavaType() {
		return klass;
	}

	@Override
	public boolean contains(Type t) {
		if (t==this) return true;

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

	@Override
	public Type intersection(Type t) {
		if ((t==this)||(t instanceof Anything)) return this;

		if (t instanceof Null) return Nothing.INSTANCE;
		if (t instanceof Maybe) {
			return ((Maybe)t).intersection(this);
		}
		if (t instanceof JavaType) {
			JavaType<?> jt=(JavaType<?>)t;
			if (this.klass==jt.klass) return this;
			if (this.contains(t)) return t;
			if (t.contains(this)) return this;
			return Nothing.INSTANCE;
		}
		return t.intersection(this);
	}

	@Override
	public boolean canBeNull() {
		return false;
	}
	
	@Override
	public boolean cantBeNull() {
		return true;
	}

	@Override
	public boolean canBeTruthy() {
		return true;
	}

	@Override
	public boolean canBeFalsey() {
		return klass.isAssignableFrom(Boolean.class);
	}

	@Override
	public Type inverse() {
		return Not.createNew(this);
	}

	@Override
	public String toString() {
		return "(JavaType "+klass.toString()+")";
	}

	@Override
	public Type union(Type t) {
		if (t==this) return this;
		if (t instanceof JavaType) {
			JavaType<?> jt=(JavaType<?>) t;
			if (jt.klass==this.klass) return this;
			if (jt.klass.isAssignableFrom(this.klass)) return jt;
			if (this.klass.isAssignableFrom(jt.klass)) return this;
		}
		if (t instanceof Null) {
			return Maybe.create(this);
		}
		return super.union(t);
	}

}
