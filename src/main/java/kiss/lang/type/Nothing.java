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
	public Class<?> getJavaType() {
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
	public boolean maybeNull() {
		return false;
	}

	@Override
	public boolean maybeTruthy() {
		return false;
	}

	@Override
	public boolean maybeFalsey() {
		return false;
	}

}
