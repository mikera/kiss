package kiss.lang.type;

import kiss.lang.Type;
import kiss.lang.impl.KissException;

public class Union extends ACompoundType {

	protected Union(Type[] types) {
		super(types);
	}
	
	/**
	 * Compress an array of types to create a Union data structure. May destroy the passed array.
	 * @param types
	 * @return
	 */
	private static Type[] compress(Type... types) {
		int n=types.length;
		int found=0;
		for (int i=0; i<n; i++) {
			Type t=types[i];
			if ((t==null)||(t instanceof Nothing)) {
				types[i]=null;
				continue;
			}
			found++;
			for (int j=0; j<i; j++) {
				// try to eliminate already contained types
				Type jt=types[j];
				if ((jt!=null)&&jt.contains(t)) {
					types[i]=null;
					found--;
					break;
				}
			}
		}
		if (found==n) return types; // no compression needed
		
		Type[] nts=new Type[found];
		found=0;
		for (int i=0; i<n; i++) {
			Type t=types[i];
			if (t==null) continue;
			nts[found++]=t;
		}
		if (found!=nts.length) throw new RuntimeException("This shouldn't happen! found="+found+ " nts.length="+nts.length);
		return nts;		
	}
	
	private Type include(Type t) {
		int n=types.length;
		for (int i=0; i<n; i++) {
			Type ct=types[i];
			if (ct.contains(t)) return this;
		}
		// TODO: smarter union, then t contains current union members
		Type[] nts=new Type[n+1];
		System.arraycopy(types, 0, nts, 0, n);
		nts[n]=t;
		return new Union(nts);
	}
	
	public static Type create(Type... types) {
		types=compress(types);
		if (types.length==0) return Nothing.INSTANCE;
		if (types.length==1) return types[0];
		return new Union(types);
	}

	@Override
	public boolean checkInstance(Object o) {
		for (int i=0; i<types.length; i++) {
			if (types[i].checkInstance(o)) return true;
		}
		return false;
	}

	@Override
	public Class<?> getJavaClass() {
		return Object.class;
	}

	@Override
	public boolean canBeNull() {
		for (int i=0; i<types.length; i++) {
			if (types[i].canBeNull()) return true;
		}
		return false;
	}

	@Override
	public boolean canBeTruthy() {
		for (int i=0; i<types.length; i++) {
			if (types[i].canBeTruthy()) return true;
		}
		return false;
	}

	@Override
	public boolean canBeFalsey() {
		for (int i=0; i<types.length; i++) {
			if (types[i].canBeFalsey()) return true;
		}
		return false;
	}

	@Override
	public boolean contains(Type t) {
		for (int i=0; i<types.length; i++) {
			if (types[i].contains(t)) return true;
		}
		return false;
	}

	@Override
	public Type intersection(Type t) {
		if (t==this) return this;
		int n=types.length;
		Type[] invs=new Type[n];
		for (int i=0; i<n; i++) {
			invs[i]=types[i].intersection(t);
		}
		return create(invs);
	}


	@Override
	public Type inverse() {
		int n=types.length;
		Type[] invs=new Type[n];
		for (int i=0; i<n; i++) {
			invs[i]=types[i].inverse();
		}
		return Intersection.create(invs);
	}

	@Override
	public Type union(Type t) {
		return include(t);
	}
	
	@Override
	public void validate() {
		int n=types.length;
		if (n<2) throw new KissException("Union must have at least two members");
		for (int i=0; i<n; i++) {
			Type t=types[i];
			t.validate();
			if (t instanceof Nothing) throw new KissException(this+ " should not contain Nothing type");
		}
	}

	@Override
	public String toString() {
		StringBuilder sb=new StringBuilder();
		sb.append("(U");
		for (int i=0; i<types.length; i++) {
			sb.append(' ');
			sb.append(types[i].toString());
		}
		sb.append(')');
		return sb.toString();
	}

}
