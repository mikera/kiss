package kiss.lang;

import kiss.lang.impl.JavaType;
import kiss.lang.impl.NullType;

public abstract class Type {

	public static Type analyse(Object val) {
		if (val==null) return NullType.INSTANCE;
		return new JavaType(val.getClass());
	}
	
	public abstract boolean checkInstance(Object o);
	
	public abstract Class<?> getJavaType();
	
	public boolean isPrimitive() {
		return false;
	}

}
