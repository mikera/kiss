package kiss.lang.type;

import kiss.lang.Type;
import kiss.lang.impl.KissException;

/**
 * Intersection type. 
 * 
 * @author Mike
 *
 */
public class Intersection extends ACompoundType {

	protected Intersection(Type[] types) {
		super(types);
	}
	
	private Intersection extendWith(Type t) {
		int n=types.length;
		Type[] nts=new Type[n+1];
		System.arraycopy(types, 0, nts, 0, n);
		nts[n]=t;
		return new Intersection(nts);
	}
	
	private Intersection replaceWith(int i, Type t) {
		int n=types.length;
		Type[] nts=new Type[n];
		System.arraycopy(types, 0, nts, 0, n);
		nts[i]=t;
		return new Intersection(nts);
	}
	
	public static Type create(Type... types) {
		if (types.length==0) return Anything.INSTANCE;
		if (types.length==1) return types[0];
		return new Intersection(types);
	}

	@Override
	public boolean checkInstance(Object o) {
		for (int i=0; i<types.length; i++) {
			if (!types[i].checkInstance(o)) return false;
		}
		return true;
	}

	@Override
	public Class<?> getJavaClass() {
		Class<?> c=types[0].getJavaClass();
		for (int i=1; i<types.length; i++) {
			Class<?> ci=types[i].getJavaClass();
			if (c.isAssignableFrom(ci)) {
				c=ci;
			}
		}
		return c;
	}

	@Override
	public boolean canBeNull() {
		for (int i=0; i<types.length; i++) {
			if (!types[i].canBeNull()) return false;
		}
		return true;
	}

	@Override
	public boolean cannotBeTruthy() {
		for (int i=0; i<types.length; i++) {
			if (types[i].cannotBeTruthy()) return true;
		}
		return false;
	}

	@Override
	public boolean cannotBeFalsey() {
		for (int i=0; i<types.length; i++) {
			if (types[i].cannotBeFalsey()) return true;
		}
		return false;
	}

	@Override
	public boolean contains(Type t) {
		for (int i=0; i<types.length; i++) {
			if (!types[i].contains(t)) return false;
		}
		return true;
	}

	@Override
	public Type intersection(Type t) {
		if (t instanceof Anything) return this;
		if (t instanceof Nothing) return t;

		int n=types.length;
		int cc=0;
		int rep=-1;
		for (int i=0; i<n; i++) {
			Type ti=types[i];
			if (t==ti) return this;
			if (t.contains(ti)) return this;
			if (ti.contains(t)) {
				cc++;
				rep=i;
			}
		}
		if (cc==n) return t; // fully contained by all types
		if (rep>=0) return replaceWith(rep,t);
		return extendWith(t);
	}

	@Override
	public Type inverse() {
		return Not.createNew(this);
	}
	
	@Override
	public boolean isWellBehaved() {
		// not a well behaved type
		return false;
	}

	@Override
	public Type union(Type t) {
		return super.union(t);
	}
	
	@Override
	public String toString() {
		StringBuilder sb=new StringBuilder();
		sb.append("(I");
		for (int i=0; i<types.length; i++) {
			sb.append(' ');
			sb.append(types[i].toString());
		}
		sb.append(')');
		return sb.toString();
	}

	@Override
	public boolean canBeTruthy() {
		return checkInstance(Boolean.TRUE);
	}

	@Override
	public boolean canBeFalsey() {
		return (checkInstance(null)||(checkInstance(Boolean.FALSE)));
	}
	
	@Override
	public void validate() {
		int n=types.length;
		if (n<2) throw new KissException("Intersection must have at least two members");
		for (int i=0; i<n; i++) {
			Type t=types[i];
			t.validate();
			if (t instanceof Anything) throw new KissException(this+ " should not contain Anything");
		}
	}

}
