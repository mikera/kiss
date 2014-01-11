package kiss.lang.type;

import kiss.lang.Type;

/**
 * Maybe type, represents the type of values that may be either null or non-null values of another type
 * 
 * Specialises Reference to a specific subset of non-null types
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
		if ((t instanceof Null)||(t instanceof Nothing)) {
			return Null.INSTANCE;
		}
		if (t instanceof Maybe) {
			return t;
		}
		return new Maybe(t.intersection(Reference.INSTANCE));
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
		if (t==this) return true;
		
		if (t instanceof Maybe) {
			return type.contains(((Maybe)t).type);
		}
		return Null.INSTANCE.contains(t)||type.contains(t);
	}

	@Override
	public Type intersection(Type t) {
		if ((t==this)||(t instanceof Anything)||(t instanceof Reference)) return this;
		
		// handle possible null cases
		if (t instanceof Null) return Null.INSTANCE;
		if (t instanceof Maybe) {
			Type mt=((Maybe)t).type;
			Type it = type.intersection(mt);
			if (it==type) return this;
			if (it==mt) return t;
			return Maybe.create(it);
		}
		
		return type.intersection(t);
	}

	@Override
	public boolean maybeNull() {
		return true;
	}

	@Override
	public boolean maybeTruthy() {
		return true;
	}

	@Override
	public boolean maybeFalsey() {
		return true;
	}

	@Override
	public Type inverse() {
		return Not.createNew(this);
	}
}
