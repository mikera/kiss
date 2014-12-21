 package kiss.lang.type;

import kiss.lang.KFn;
import kiss.lang.Type;
import kiss.lang.impl.KissException;
import kiss.lang.impl.Mapping;
import clojure.lang.IFn;

/**
 * Type representing a fixed arity function
 * 
 * Covers both Kiss (KFn) and Clojure (Ifn) functions, however type checking is only
 * supported on Kiss functions.
 * 
 * @author Mike
 *
 */
public class FunctionType extends AFunctionType {

	private final Type returnType;
	private final Type[] paramTypes;
	private final int minArity;
	private final boolean variadic;
	
	private FunctionType(Type returnType) {
		this(returnType,Type.EMPTY_TYPE_ARRAY,true);
	}
	
	private FunctionType(Type returnType, Type[] paramTypes, boolean variadic) {
		this.returnType=returnType;
		this.paramTypes=paramTypes;
		this.variadic=variadic;
		this.minArity=paramTypes.length - (variadic?1:0);
	}
	
	public static FunctionType create(Type returnType, Mapping[] params) {
		int n=params.length;
		Type[] ptypes=new Type[n];
		for (int i=0; i<n; i++) {
			ptypes[i]=params[i].getType();
		}
		return new FunctionType(returnType,ptypes,false);
	}
	
	public static FunctionType create(Type returnType, Type... types) {
		int n=types.length;
		Type[] ptypes=new Type[n];
		for (int i=0; i<n; i++) {
			ptypes[i]=types[i];
		}
		return new FunctionType(returnType,ptypes,false);
	}
	
	public static FunctionType createVariadic(Type returnType, Type... types) {
		int n=types.length;
		Type[] ptypes=new Type[n];
		for (int i=0; i<n; i++) {
			ptypes[i]=types[i];
		}
		return new FunctionType(returnType,ptypes,true);
	}
	
	public boolean hasArity(int n) {
		if (variadic) {
			return n>=minArity;
		} else {
			return n==minArity;
		}
	}
	
	public Type getReturnType() {
		return returnType;
	}

	@Override
	public boolean checkInstance(Object o) {
		if (o instanceof KFn) {
			KFn fn=(KFn)o;
			if (!returnType.contains(fn.getReturnType())) return false; // covariance on return type
			for (int i=0; i<minArity; i++) {
				if (!fn.getParamType(i).contains(paramTypes[i])) return false; // contravariance on parameter type				
			}
			return true;
		} else if (o instanceof IFn) {
			// we can't assume anything about an arbitrary IFn.....
			 return true;
		} else {
			// not a function, so return false
			return false;			
		}
	}

	@Override
	public Class<?> getJavaClass() {
		return IFn.class;
	}

	@Override
	public boolean contains(Type t) {
		if (t==this) return true;
		
		if (t instanceof FunctionType) {
			FunctionType ft=(FunctionType)t;
			
			// TODO: handle variable arity
			int n=getMinArity();
			if (ft.getMinArity()!=n) return false;
					
			if (!returnType.contains(ft.returnType)) return false;
			
			for (int i=0; i<n; i++) {
				if (!(ft.paramTypes[i].contains(paramTypes[i]))) return false;
			}
			return true;
		} else {
			return false;
		}
	}

	@Override
	public Type intersection(Type t) {
		if ((t==this)||(t instanceof Anything)) return this;
		
		if (t instanceof FunctionType) {
			FunctionType ft=(FunctionType)t;
			// TODO: handle variable arity
			int n=getMinArity();
			if (ft.getMinArity()!=n) return Nothing.INSTANCE;
			
			Type rt=getReturnType().intersection(ft.returnType);
			if (rt instanceof Nothing) return Nothing.INSTANCE;
			boolean match=(rt==returnType);
			
			Type[] ips=new Type[n];
			for (int i=0; i<n ; i++) {
				Type it=paramTypes[i].intersection(ft.paramTypes[i]);
				ips[i]=it;
				match &= (it==paramTypes[i]);
			}
			if (match) return this;
			return create(rt,ips);
		}
		if (t instanceof JavaType) {
			JavaType<?> jt = (JavaType<?>) t;
			if (jt.klass.isAssignableFrom(KFn.class)) {
				return this;
			} else if (KFn.class.isAssignableFrom(jt.klass)) {
				return jt;
			} else {
				return Nothing.INSTANCE;
			}
		}
		
		return t.intersection(this);
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
		return false;
	}
	
	@Override
	public boolean cannotBeFalsey() {
		return true;
	}

	@Override
	public Type inverse() {
		return Not.createNew(this);
	}

	@Override
	public Type union(Type t) {
		if (t==this) return t;
		return Union.create(this,t);
	}

	@Override
	public void validate() {
		if (variadic) {
			if (minArity!=paramTypes.length-1) throw new KissException("Mismatched arity count (variadic)");
		} else {
			if (minArity!=paramTypes.length) throw new KissException("Mismatched arity count (non-variadic)");
		}
		
	}

	@Override
	public Type getParamType(int i) {
		if (variadic&&i>=minArity) i=minArity;
		return paramTypes[i];
	}

	@Override
	public boolean isVariadic() {
		return variadic;
	}

	@Override
	public int getMinArity() {
		return minArity;
	}

}
