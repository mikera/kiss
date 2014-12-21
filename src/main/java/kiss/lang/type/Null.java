package kiss.lang.type;

import kiss.lang.Type;
import kiss.lang.impl.KissException;

/**
 * The type of the value null
 * 
 * @author Mike
 *
 */
public class Null extends Type {

	public static final Null INSTANCE = new Null();
	
	private Null() {
		// nothing to do
	}

	@Override
	public boolean checkInstance(Object o) {
		return o==null;
	}
	
	@Override
	public Object cast(Object a) {
		if (a!=null) throw new ClassCastException("Can't cast non-null object to null");
		return null;
	}

	@Override
	public Class<?> getJavaClass() {
		return Void.TYPE;
	}
	
	@Override
	public Type getReturnType() {
		return Nothing.INSTANCE;
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
	public boolean cannotBeTruthy() {
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
	
	@Override
	public void validate() {
		if (this!=Null.INSTANCE) throw new KissException(this+ " should be a singleton!");
	}

}
