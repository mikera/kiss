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
		if (t.maybeNull()) return this;
		return Nothing.INSTANCE;
	}

	@Override
	public boolean maybeNull() {
		return true;
	}

	@Override
	public boolean maybeTruthy() {
		return false;
	}

	@Override
	public boolean maybeFalsey() {
		return true;
	}

	@Override
	public Type inverse() {
		// TODO: what about primitives?
		return Something.INSTANCE;
	}

}
