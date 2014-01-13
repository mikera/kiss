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
	public Class<?> getJavaClass() {
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
	public boolean canBeNull() {
		return true;
	}

	@Override
	public boolean canBeTruthy() {
		return true;
	}

	@Override
	public boolean canBeFalsey() {
		return true;
	}

	@Override
	public Type inverse() {
		return Nothing.INSTANCE;
	}

	@Override
	public Type union(Type t) {
		return this;
	}

}
