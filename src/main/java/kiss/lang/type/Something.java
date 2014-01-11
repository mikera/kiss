package kiss.lang.type;

import kiss.lang.Type;

/**
 * Type that represents any non-null reference
 * 
 * @author Mike
 */
public class Something extends Type {
	public static final Something INSTANCE=new Something();
	
	private Something() {
		// nothing to do, this is a singleton
	}
	
	@Override
	public boolean checkInstance(Object o) {
		return (o!=null);
	}

	@Override
	public Class<?> getJavaType() {
		return Object.class;
	}

	@Override
	public boolean contains(Type t) {
		// we just need to eliminate null possibilities
		if (t instanceof Maybe)	return false;
		if (t instanceof Null) return false;
		return true;
	}

	@Override
	public Type intersection(Type t) {
		if ((t==this)||(t instanceof Anything)||(t instanceof Reference)) return this;

		if ((t instanceof Null)||(t instanceof Nothing)) return Nothing.INSTANCE;
		if (t instanceof Maybe) return ((Maybe)t).type;
		return t;
	}

	@Override
	public boolean canBeNull() {
		return false;
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
		// TODO what about primitives?
		return Null.INSTANCE;
	}

}
