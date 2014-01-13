package kiss.lang.type;

import kiss.lang.KFn;
import kiss.lang.Mapping;
import kiss.lang.Type;
import clojure.lang.IFn;

/**
 * Type representing a fixed arity function
 * 
 * Covers both Kiss and Clojure functions
 * 
 * @author Mike
 *
 */
public class FunctionType extends Type {

	private final Type returnType;
	private final Type[] paramTypes;
	
	private FunctionType(Type returnType) {
		this.returnType=returnType;
		this.paramTypes=Type.EMPTY_TYPE_ARRAY;
	}
	
	private FunctionType(Type returnType, Type[] paramTypes) {
		this.returnType=returnType;
		this.paramTypes=paramTypes;
	}
	
	public static FunctionType create(Type returnType, Mapping[] params) {
		int n=params.length;
		Type[] ptypes=new Type[n];
		for (int i=0; i<n; i++) {
			ptypes[i]=params[i].getType();
		}
		return new FunctionType(returnType,ptypes);
	}
	
	public static FunctionType create(Type returnType, Type... types) {
		int n=types.length;
		Type[] ptypes=new Type[n];
		for (int i=0; i<n; i++) {
			ptypes[i]=types[i];
		}
		return new FunctionType(returnType,ptypes);
	}
	
	public boolean hasArity(int n) {
		// TODO: think about multi-arity?
		return n==getArity();
	}
	
	public int getArity() {
		return paramTypes.length;
	}
	
	private Type getReturnType() {
		return returnType;
	}

	@Override
	public boolean checkInstance(Object o) {
		if (o instanceof KFn) {
			KFn fn=(KFn)o;
			if (!(returnType.contains(fn.getReturnType()))) return false;
		} else {
			if (o instanceof IFn) return true;
		}
		return false;
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
			
			int n=getArity();
			if (ft.getArity()!=n) return false;
					
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
			int n=getArity();
			if (ft.getArity()!=n) return Nothing.INSTANCE;
			
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

}
