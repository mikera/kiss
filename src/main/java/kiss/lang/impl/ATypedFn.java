package kiss.lang.impl;

import kiss.lang.KFn;
import kiss.lang.Type;
import kiss.lang.type.AFunctionType;
import kiss.lang.type.FunctionType;

public class ATypedFn extends KFn {
	private final AFunctionType type;

	public ATypedFn(Type returnType, Type... paramTypes) {
		type=FunctionType.create(returnType, paramTypes);
	}
	
	public ATypedFn(Type returnType, Type[] paramTypes, boolean variadic) {
		if (variadic) {
			type=FunctionType.createVariadic(returnType, paramTypes);			
		} else {
			type=FunctionType.create(returnType, paramTypes);
		}
	}

	@Override public Type getReturnType() {
		return type.getReturnType();
	}
	
	@Override public Type getParamType(int i) {
		return type.getParamType(i);
	}
}
