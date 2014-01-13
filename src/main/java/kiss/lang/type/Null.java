package kiss.lang.type;

import kiss.lang.Type;

/**
 * The type of the value null
 * 
 * @author Mike
 *
 */
public class Null extends Type {

	public static final Type INSTANCE = new Null();
	
	private Null() {
		// nothing to do
	}

	@Override
	public boolean checkInstance(Object o) {
		return o==null;
	}

	@Override
	public Class<?> getJavaType() {
		return Void.TYPE;
	}

	@Override
	public boolean contains(Type t) {
		return t==INSTANCE;
	}

	@Override
	public Type intersection(Type t) {
		if (t.canBeNull()) return this;
		return Nothing.INSTANCE;
	}

	@Override
	public boolean canBeNull() {
		return true;
	}

	@Override
	public boolean canBeTruthy() {
		return false;
	}

	@Override
	public boolean cantBeTruthy() {
		return true;
	}
	
	@Override
	public boolean canBeFalsey() {
		return true;
	}

	@Override
	public Type inverse() {
		// TODO: what about primitives?
		return Something.INSTANCE;
	}

	@Override
	public Type union(Type t) {
		if (t.checkInstance(null)) return t;
		return Maybe.create(t);
	}

}
