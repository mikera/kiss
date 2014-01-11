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
	public boolean canBeNull() {
		return (type.cantBeNull());
	}
	
	@Override
	public boolean cantBeNull() {
		return (type.canBeNull());
	}

	@Override
	public boolean canBeTruthy() {
		return false;
	}

	@Override
	public boolean canBeFalsey() {
		return canBeNull();
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

	@Override
	public Type union(Type t) {
		// TODO any applicable optimisations here?
		return super.union(t);
	}
}
