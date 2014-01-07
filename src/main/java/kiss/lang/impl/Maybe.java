package kiss.lang.impl;

import kiss.lang.Type;

public class Maybe extends Type{	
	private Type type;

	private Maybe(Type t) {
		this.type=t;
	}
	
	public Type create(Type t) {
		if (t instanceof NullType) {
			return NullType.INSTANCE;
		}
		return new Maybe(t);
	}

	@Override
	public boolean checkInstance(Object o) {
		return (o==null)||type.checkInstance(o);
	}
}
