package kiss.lang.type;

import kiss.lang.Type;
import kiss.lang.impl.KissException;

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
	public Type getReturnType() {
		// not a function, so can't return anything
		return Nothing.INSTANCE;
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
	public boolean canBeFalsey() {
		return false;
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
	public boolean cannotBeFalsey() {
		return true;
	}
	
	@Override
	public boolean cannotBeNull() {
		return true;
	}

	@Override
	public boolean cannotBeTruthy() {
		return true;
	}


	@Override
	public Type inverse() {
		return Anything.INSTANCE;
	}

	@Override
	public Type union(Type t) {
		return t;
	}
	
	@Override
	public void validate() {
		if (this!=Nothing.INSTANCE) throw new KissException(this+ " should be a singleton!");
	}

	@Override
	public Object cast(Object a) {
		throw new ClassCastException("Can't cast to Nothing!");
	}

}
