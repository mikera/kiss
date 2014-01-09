package kiss.lang.type;

import kiss.lang.Type;

/**
 * Maybe type, represents the type of values that may be either null or non-null values of another type
 * 
 * @author Mike
 *
 */
public class Maybe extends Type{	
	Type type;

	private Maybe(Type t) {
		this.type=t;
	}
	
	public static Type create(Type t) {
		if (t instanceof NullType) {
			return NullType.INSTANCE;
		}
		return new Maybe(t);
	}

	@Override
	public boolean checkInstance(Object o) {
		return (o==null)||type.checkInstance(o);
	}

	@Override
	public Class<?> getJavaType() {
		return type.getJavaType();
	}

	@Override
	public boolean contains(Type t) {
		return NullType.INSTANCE.contains(t)||type.contains(t);
	}

	@Override
	public Type intersection(Type t) {
		// handle possible null cases
		if (t instanceof NullType) return NullType.INSTANCE;
		if (t instanceof Maybe) {
			Type mt=((Maybe)t).type;
			Type it = type.intersection(mt);
			if (it==type) return this;
			if (it==mt) return t;
			return Maybe.create(it);
		}
		
		return type.intersection(t);
	}
}
