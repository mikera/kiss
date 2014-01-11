package kiss.lang.type;

import kiss.lang.Type;

public class Not extends Type {

	private Type type;

	private Not(Type t) {
		this.type=t;
	}
	
	public static Type create(Type t) {
		return t.inverse();
	}
	
	public static Not createNew(Type t) {
		return new Not(t);
	}

	@Override
	public boolean checkInstance(Object o) {
		return !(type.checkInstance(o));
	}

	@Override
	public Class<?> getJavaType() {
		return Object.class;
	}

	@Override
	public boolean maybeNull() {
		return !(type.maybeNull());
	}

	@Override
	public boolean maybeTruthy() {
		return false;
	}

	@Override
	public boolean maybeFalsey() {
		return maybeNull();
	}

	@Override
	public boolean contains(Type t) {
		return false;
	}

	@Override
	public Type intersection(Type t) {
		if (t instanceof Anything) return this;
		// TODO: better specialisation via Intersection?
		return t;
	}

	@Override
	public Type inverse() {
		return type;
	}
	
	@Override
	public boolean isWellBehaved() {
		// not a well behaved type
		return false;
	}
}
