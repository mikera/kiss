package kiss.lang.impl;

import kiss.lang.KFn;
import kiss.lang.Type;

public class ATypedFn extends KFn {
	private final Type returnType;
	private final Type[] paramTypes;

	public ATypedFn(Type returnType, Type... paramTypes) {
		this.returnType=returnType;
		this.paramTypes=paramTypes;
	}

	
	@Override public Type getReturnType() {
		return returnType;
	}
	
	@Override public Type getParamType(int i) {
		return paramTypes[i];
	}
}
