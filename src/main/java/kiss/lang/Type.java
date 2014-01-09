package kiss.lang;

import kiss.lang.type.AnyType;
import clojure.lang.Symbol;

/**
 * Abstract base class for Kiss types
 * 
 * @author Mike
 *
 */
public abstract class Type {	
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

	/**
	 * Returns true if another type t is contained within this type.
	 * 
	 * Equivalently this means:
	 * - t is a subtype of this type
	 * - every instance of t is an instance of this type
	 *  
	 * @param t
	 * @return
	 */
	public abstract boolean contains(Type t);

	/**
	 * Returns the intersection of this type with another type
	 * @param t
	 * @return
	 */
	public abstract Type intersection(Type t);
	
	/**
	 * Resolves a Clojure tag symbol into a type
	 * @param s
	 * @return
	 */
	public static Type resolveTag(Symbol s) {
		// TODO: extract type hints from Clojure symbols
		return AnyType.MAYBE;
	}

}
