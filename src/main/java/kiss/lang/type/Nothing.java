package kiss.lang.type;

import kiss.lang.Type;

/**
 * A type that has no possible instances.
 * 
 * @author Mike
 *
 */
public class Nothing extends Type {

	public static final Type INSTANCE = new Nothing();
	
	private Nothing() {
		// nothing to do, this is a singleton
	}

	@Override
	public boolean checkInstance(Object o) {
		// nothing is an instance of this type
		return false;
	}

	@Override
	public Class<?> getJavaClass() {
		// TODO figure out if this is correct?
		return Void.TYPE;
	}

	@Override
	public boolean contains(Type t) {
		// nothing is an instance of this type
		return t==this;
	}

	@Override
	public Type intersection(Type t) {
		return Nothing.INSTANCE;
	}

	@Override
	public boolean canBeNull() {
		return false;
	}

	@Override
	public boolean canBeTruthy() {
		return false;
	}

	@Override
	public boolean canBeFalsey() {
		return false;
	}

	@Override
	public Type inverse() {
		// TODO Auto-generated method stub
		return Anything.INSTANCE;
	}

	@Override
	public Type union(Type t) {
		return t;
	}

}
