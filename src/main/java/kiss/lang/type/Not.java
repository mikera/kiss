package kiss.lang.type;

import kiss.lang.Type;

/**
 * Represents the inverse of a given type
 * 
 * @author Mike
 */
public class Not extends Type {

	private Type type;

	private Not(Type t) {
		this.type=t;
	}
	
	public static Type create(Type t) {
		if (t instanceof Nothing) return Anything.INSTANCE;
		if (t instanceof Anything) return Nothing.INSTANCE;
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
	public Class<?> getJavaClass() {
		return Object.class;
	}

	@Override
	public boolean canBeNull() {
		return (type.cannotBeNull());
	}
	
	@Override
	public boolean cannotBeNull() {
		return (type.canBeNull());
	}

	@Override
	public boolean canBeTruthy() {
		return type.cannotBeTruthy();
	}

	@Override
	public boolean canBeFalsey() {
		return type.cannotBeFalsey();
	}

	@Override
	public boolean contains(Type t) {
		return false;
	}

	@Override
	public Type intersection(Type t) {
		if (t instanceof Not) {
			return Union.create(((Not)t).type,type).inverse();
		}
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
