package kiss.lang.type;

import kiss.lang.Type;

/**
 * A type that represents any value, including null 
 * 
 * @author Mike
 */
public class Anything extends Type {
	public static final Anything INSTANCE=new Anything();

	@Override
	public boolean checkInstance(Object o) {
		return true;
	}

	@Override
	public Class<?> getJavaType() {
		return Object.class;
	}

	@Override
	public boolean contains(Type t) {
		return true;
	}

	@Override
	public Type intersection(Type t) {
		return t;
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
		return Nothing.INSTANCE;
	}

}
