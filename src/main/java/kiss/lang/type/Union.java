package kiss.lang.type;

import kiss.lang.Type;

public class Union extends ACompoundType {

	protected Union(Type[] types) {
		super(types);
	}
	
	public static Type create(Type... types) {
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
	public Class<?> getJavaType() {
		return Object.class;
	}

	@Override
	public boolean maybeNull() {
		for (int i=0; i<types.length; i++) {
			if (types[i].maybeNull()) return true;
		}
		return false;
	}

	@Override
	public boolean maybeTruthy() {
		for (int i=0; i<types.length; i++) {
			if (types[i].maybeTruthy()) return true;
		}
		return false;
	}

	@Override
	public boolean maybeFalsey() {
		for (int i=0; i<types.length; i++) {
			if (types[i].maybeFalsey()) return true;
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

}
