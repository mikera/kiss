package kiss.lang.type;

import kiss.lang.Type;

/**
 * The type of the value null
 * 
 * @author Mike
 *
 */
public class NullType extends Type {

	public static final Type INSTANCE = new NullType();

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
		if (t instanceof Maybe) return this;
		if (t instanceof NullType) return this;
		return Nothing.INSTANCE;
	}

}
