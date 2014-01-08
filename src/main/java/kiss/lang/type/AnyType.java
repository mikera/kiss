package kiss.lang.type;

import kiss.lang.Type;

public class AnyType extends Type {
	public static final AnyType INSTANCE=new AnyType();
	public static final Type MAYBE=Maybe.create(INSTANCE);
	
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
		if (t instanceof NullType) return false;
		return true;
	}

}
