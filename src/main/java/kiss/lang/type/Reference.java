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
	public boolean canBeNull() {
		return true;
	}

	@Override
	public boolean canBeTruthy() {
		return true;
	}

	@Override
	public boolean canBeFalsey() {
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
