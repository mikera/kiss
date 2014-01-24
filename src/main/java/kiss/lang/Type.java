package kiss.lang;

import kiss.lang.impl.KissUtils;
import kiss.lang.type.Anything;
import kiss.lang.type.JavaType;
import kiss.lang.type.Nothing;
import kiss.lang.type.Union;
import clojure.lang.Symbol;

/**
 * Abstract base class for Kiss types
 * 
 * @author Mike
 *
 */
public abstract class Type {	
	public static final Type[] EMPTY_TYPE_ARRAY = new Type[0];

	/**
	 * Creates a type by parsing a given String
	 */
	public static Type parse(String s) {
		return Analyser.analyseType(KissUtils.read(s));
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
	public abstract Class<?> getJavaClass();
	
	/**
	 * Returns true if this is a JVM primitive type
	 * @return
	 */
	public boolean isPrimitive() {
		return false;
	}
	
	/**
	 * Return true if this type provably contains the null value
	 * @return
	 */
	public abstract boolean canBeNull();
	
	/**
	 * Returns true if this type provably contains at least one truthy value
	 * @return
	 */
	public abstract boolean canBeTruthy();
	
	/**
	 * Returns true if this type provably contains at least one falsey value
	 * @return
	 */
	public abstract boolean canBeFalsey();

	/**
	 * Returns true if another type t is provably contained within this type.
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
	 * Returns the union of this type with another type
	 * @param t
	 * @return
	 */
	public Type union(Type t) {
		if (t instanceof Nothing) return this;
		return Union.create(this,t);
	}
	
	/**
	 * Resolves a Clojure tag symbol into a type
	 * @param s
	 * @return
	 */
	public static Type resolveTag(Symbol s) {
		// TODO: extract type hints from Clojure symbols
		return Anything.INSTANCE;
	}

	@Override
	public boolean equals(Object o) {
		if (o==this) return true;
		if (!(o instanceof Type)) return false;
		Type t=(Type)o;
		return t.contains(this)&&this.contains(t);
	}

	@Override
	public int hashCode() {
		return super.hashCode();
	}
	
	public abstract void validate();

	public abstract Type inverse();

	public boolean isWellBehaved() {
		return true;
	}

	public boolean cannotBeNull() {
		return false;
	}

	public boolean cannotBeFalsey() {
		return false;
	}

	public boolean cannotBeTruthy() {
		return false;
	}

	public JavaType<?> toJavaType() {
		return JavaType.create(this.getJavaClass());
	}
}
