package kiss.lang.impl;

import kiss.lang.Type;
import kiss.lang.type.AFunctionType;
import clojure.lang.IFn;
import clojure.lang.RT;

/**
 * Wrapped for Clojure functions that adds a Kiss type
 * @author Mike
 *
 */
public class WrappedFn extends ATypedFn {
	private final IFn fn;
	
	public WrappedFn(IFn fn, Type returnType, Type... paramTypes) {
		super(returnType,paramTypes);
		this.fn=fn;
	}
	
	public WrappedFn(IFn fn, AFunctionType t) {
		super(t.getReturnType(),t.getParamTypes(),t.isVariadic());
		this.fn=fn;
	}

	@Override
	public Object invoke() {
		return fn.invoke();
	}

	@Override
	public Object invoke(Object arg1) {
		return fn.invoke(arg1);
	}

	@Override
	public Object invoke(Object arg1, Object arg2) {
		return fn.invoke(arg1,arg2);
	}

	@Override
	public Object invoke(Object arg1, Object arg2, Object arg3) {
		return fn.invoke(arg1,arg2,arg3);
	}

	@Override
	public Object invoke(Object arg1, Object arg2, Object arg3, Object arg4) {
		return fn.invoke(arg1,arg2,arg3,arg4);
	}

	@Override
	public Object invoke(Object arg1, Object arg2, Object arg3, Object arg4,
			Object arg5) {
		return fn.invoke(arg1,arg2,arg3,arg4,arg5);
	}

	@Override
	public Object invoke(Object arg1, Object arg2, Object arg3, Object arg4,
			Object arg5, Object arg6) {
		return fn.invoke(arg1,arg2,arg3,arg4,arg5,arg6);
	}

	@Override
	public Object invoke(Object arg1, Object arg2, Object arg3, Object arg4,
			Object arg5, Object arg6, Object arg7) {
		return fn.invoke(arg1,arg2,arg3,arg4,arg5,arg6,arg7);
	}

	@Override
	public Object invoke(Object arg1, Object arg2, Object arg3, Object arg4,
			Object arg5, Object arg6, Object arg7, Object arg8) {
		return fn.invoke(arg1,arg2,arg3,arg4,arg5,arg6,arg7,arg8);
	}

	@Override
	public Object invoke(Object arg1, Object arg2, Object arg3, Object arg4,
			Object arg5, Object arg6, Object arg7, Object arg8, Object arg9) {
		return fn.invoke(arg1,arg2,arg3,arg4,arg5,arg6,arg7,arg8,arg9);
	}

	@Override
	public Object invoke(Object arg1, Object arg2, Object arg3, Object arg4,
			Object arg5, Object arg6, Object arg7, Object arg8, Object arg9,
			Object arg10) {
		return fn.invoke(arg1,arg2,arg3,arg4,arg5,arg6,arg7,arg8,arg9,arg10);
	}

	@Override
	public Object invoke(Object arg1, Object arg2, Object arg3, Object arg4,
			Object arg5, Object arg6, Object arg7, Object arg8, Object arg9,
			Object arg10, Object arg11) {
		return fn.invoke(arg1,arg2,arg3,arg4,arg5,arg6,arg7,arg8,arg9,arg10,arg11);
	}

	@Override
	public Object invoke(Object arg1, Object arg2, Object arg3, Object arg4,
			Object arg5, Object arg6, Object arg7, Object arg8, Object arg9,
			Object arg10, Object arg11, Object arg12) {
		return fn.invoke(arg1,arg2,arg3,arg4,arg5,arg6,arg7,arg8,arg9,arg10,arg11,arg12);
	}

	@Override
	public Object invoke(Object arg1, Object arg2, Object arg3, Object arg4,
			Object arg5, Object arg6, Object arg7, Object arg8, Object arg9,
			Object arg10, Object arg11, Object arg12, Object arg13) {
		return fn.invoke(arg1,arg2,arg3,arg4,arg5,arg6,arg7,arg8,arg9,arg10,arg11,arg12,arg13);
	}

	@Override
	public Object invoke(Object arg1, Object arg2, Object arg3, Object arg4,
			Object arg5, Object arg6, Object arg7, Object arg8, Object arg9,
			Object arg10, Object arg11, Object arg12, Object arg13, Object arg14) {
		return fn.invoke(arg1,arg2,arg3,arg4,arg5,arg6,arg7,arg8,arg9,arg10,arg11,arg12,arg13,arg14);
	}

	@Override
	public Object invoke(Object arg1, Object arg2, Object arg3, Object arg4,
			Object arg5, Object arg6, Object arg7, Object arg8, Object arg9,
			Object arg10, Object arg11, Object arg12, Object arg13,
			Object arg14, Object arg15) {
		return fn.invoke(arg1,arg2,arg3,arg4,arg5,arg6,arg7,arg8,arg9,arg10,arg11,arg12,arg13,arg14,arg15);
	}

	@Override
	public Object invoke(Object arg1, Object arg2, Object arg3, Object arg4,
			Object arg5, Object arg6, Object arg7, Object arg8, Object arg9,
			Object arg10, Object arg11, Object arg12, Object arg13,
			Object arg14, Object arg15, Object arg16) {
		return fn.invoke(arg1,arg2,arg3,arg4,arg5,arg6,arg7,arg8,arg9,arg10,arg11,arg12,arg13,arg14,arg15,arg16);
	}

	@Override
	public Object invoke(Object arg1, Object arg2, Object arg3, Object arg4,
			Object arg5, Object arg6, Object arg7, Object arg8, Object arg9,
			Object arg10, Object arg11, Object arg12, Object arg13,
			Object arg14, Object arg15, Object arg16, Object arg17) {
		return fn.invoke(arg1,arg2,arg3,arg4,arg5,arg6,arg7,arg8,arg9,arg10,arg11,arg12,arg13,arg14,arg15,arg16,arg17);
	}

	@Override
	public Object invoke(Object arg1, Object arg2, Object arg3, Object arg4,
			Object arg5, Object arg6, Object arg7, Object arg8, Object arg9,
			Object arg10, Object arg11, Object arg12, Object arg13,
			Object arg14, Object arg15, Object arg16, Object arg17, Object arg18) {
		return fn.invoke(arg1,arg2,arg3,arg4,arg5,arg6,arg7,arg8,arg9,arg10,arg11,arg12,arg13,arg14,arg15,arg16,arg17,arg18);
	}

	@Override
	public Object invoke(Object arg1, Object arg2, Object arg3, Object arg4,
			Object arg5, Object arg6, Object arg7, Object arg8, Object arg9,
			Object arg10, Object arg11, Object arg12, Object arg13,
			Object arg14, Object arg15, Object arg16, Object arg17,
			Object arg18, Object arg19) {
		return fn.invoke(arg1,arg2,arg3,arg4,arg5,arg6,arg7,arg8,arg9,arg10,arg11,arg12,arg13,arg14,arg15,arg16,arg17,arg18,arg19);
	}

	@Override
	public Object invoke(Object arg1, Object arg2, Object arg3, Object arg4,
			Object arg5, Object arg6, Object arg7, Object arg8, Object arg9,
			Object arg10, Object arg11, Object arg12, Object arg13,
			Object arg14, Object arg15, Object arg16, Object arg17,
			Object arg18, Object arg19, Object arg20) {
		return fn.invoke(arg1,arg2,arg3,arg4,arg5,arg6,arg7,arg8,arg9,arg10,arg11,arg12,arg13,arg14,arg15,arg16,arg17,arg18,arg19,arg20);
	}

	@Override
	public Object invoke(Object arg1, Object arg2, Object arg3, Object arg4,
			Object arg5, Object arg6, Object arg7, Object arg8, Object arg9,
			Object arg10, Object arg11, Object arg12, Object arg13,
			Object arg14, Object arg15, Object arg16, Object arg17,
			Object arg18, Object arg19, Object arg20, Object... args) {
		return fn.invoke(arg1,arg2,arg3,arg4,arg5,arg6,arg7,arg8,arg9,arg10,arg11,arg12,arg13,arg14,arg15,arg16,arg17,arg18,arg19,arg20,args);
	}
	
	public Object invokeArray(Object... args) {
		return fn.applyTo((RT.seq(KissUtils.ret1(args,args=null))));
	}
}
