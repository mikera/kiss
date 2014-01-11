package kiss.lang.type;

import kiss.lang.Type;

public class Reference extends Type {

	public static final Reference INSTANCE = new Reference();

	@Override
	public boolean checkInstance(Object o) {
		return true;
	}

	@Override
	public Class<?> getJavaType() {
		return Object.class;
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
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean contains(Type t) {
		// TODO filter out primitives?
		return true;
	}

	@Override
	public Type intersection(Type t) {
		// TODO filer out primitives?
		return t;
	}

	@Override
	public Type inverse() {
		// TODO what about primitives?
		return Nothing.INSTANCE;
	}

}
