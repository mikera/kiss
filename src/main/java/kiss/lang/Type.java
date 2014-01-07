package kiss.lang;

import kiss.lang.impl.JavaType;
import kiss.lang.impl.NullType;

public abstract class Type {

	public static Type analyse(Object val) {
		if (val==null) return NullType.INSTANCE;
		return new JavaType(val.getClass());
	}
	
	/**
	 * Check if an object is an instance of this type
	 * 
	 * @param o
	 * @return
	 */
	public abstract boolean checkInstance(Object o);
	
	/**
	 * Returns the most specific Java class or interface that can represent all instances of this type
	 * @return
	 */
	public abstract Class<?> getJavaType();
	
	/**
	 * Returns true if this is a JVM primitive type
	 * @return
	 */
	public boolean isPrimitive() {
		return false;
	}

}
