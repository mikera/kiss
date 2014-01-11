package kiss.lang.type;

import kiss.lang.Type;

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
	
	public static Intersection create(Type... types) {
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
	public Class<?> getJavaType() {
		return Object.class;
	}

	@Override
	public boolean maybeNull() {
		for (int i=0; i<types.length; i++) {
			if (!types[i].maybeNull()) return false;
		}
		return true;
	}

	@Override
	public boolean maybeTruthy() {
		for (int i=0; i<types.length; i++) {
			if (!types[i].maybeTruthy()) return false;
		}
		return true;
	}

	@Override
	public boolean maybeFalsey() {
		for (int i=0; i<types.length; i++) {
			if (!types[i].maybeFalsey()) return false;
		}
		return true;
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
			if (t==types[i]) return this;
			if (types[i].contains(t)) {
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

}
