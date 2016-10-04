package kiss.lang.impl;

import clojure.lang.IPersistentMap;
import clojure.lang.PersistentHashMap;
import clojure.lang.Symbol;
import kiss.lang.Environment;
import kiss.lang.Expression;
import kiss.lang.KFn;
import kiss.lang.Result;

/**
 * Intermediate lambda representation
 * 
 * @author Mike
 */
public class LambdaFn extends KFn {

	private final Symbol[] params;
	private final Expression body;
	private final Environment env;
	private final int arity;

	public LambdaFn(Environment env, Expression body, Symbol[] params) {
		this.env=env;
		this.body = body;
		this.params = params;
		this.arity = params.length;
	}

	public static KFn create(Environment env, Expression body, Symbol[] params) {
		return new LambdaFn(env, body, params);
	}
	
	public Object invokeArray(Object... args) {
		if (args.length!=arity) throwArity(args.length);
		IPersistentMap bindings=PersistentHashMap.EMPTY;
		for (int i=0; i<arity; i++) {
			bindings=bindings.assoc(params[i], args[i]);
		}
		Result r=body.interpret(env, bindings);
		
		// handle recursion
		while (true) {
			if (r instanceof ReturnResult) return ((ReturnResult) r).value;
			if (!(r instanceof RecurResult)) break;
			RecurResult re=(RecurResult) r;
			for (int i=0; i<arity; i++) {
				bindings=bindings.assoc(params[i], re.values[i]);
			}
			r=body.interpret(r.getEnvironment(),bindings);
		}
		return r.getResult();
	}

	@Override
	public Object invoke() {
		return invokeArray();
	}

	@Override
	public Object invoke(Object arg1) {
		return invokeArray(arg1);
	}

	@Override
	public Object invoke(Object arg1, Object arg2) {
		return invokeArray(arg1,arg2);
	}

	@Override
	public Object invoke(Object arg1, Object arg2, Object arg3) {
		return invokeArray(arg1,arg2,arg3);
	}

	@Override
	public Object invoke(Object arg1, Object arg2, Object arg3, Object arg4) {
		return invokeArray(arg1,arg2,arg3,arg4);
	}

	@Override
	public Object invoke(Object arg1, Object arg2, Object arg3, Object arg4,
			Object arg5) {
		return invokeArray(arg1,arg2,arg3,arg4,arg5);
	}

	@Override
	public Object invoke(Object arg1, Object arg2, Object arg3, Object arg4,
			Object arg5, Object arg6) {
		return invokeArray(arg1,arg2,arg3,arg4,arg5,arg6);
	}

	@Override
	public Object invoke(Object arg1, Object arg2, Object arg3, Object arg4,
			Object arg5, Object arg6, Object arg7) {
		return invokeArray(arg1,arg2,arg3,arg4,arg5,arg6,arg7);
	}

	@Override
	public Object invoke(Object arg1, Object arg2, Object arg3, Object arg4,
			Object arg5, Object arg6, Object arg7, Object arg8) {
		return invokeArray(arg1,arg2,arg3,arg4,arg5,arg6,arg7,arg8);
	}

	@Override
	public Object invoke(Object arg1, Object arg2, Object arg3, Object arg4,
			Object arg5, Object arg6, Object arg7, Object arg8, Object arg9) {
		return invokeArray(arg1,arg2,arg3,arg4,arg5,arg6,arg7,arg8,arg9);
	}

	@Override
	public Object invoke(Object arg1, Object arg2, Object arg3, Object arg4,
			Object arg5, Object arg6, Object arg7, Object arg8, Object arg9,
			Object arg10) {
		return invokeArray(arg1,arg2,arg3,arg4,arg5,arg6,arg7,arg8,arg9,arg10);
	}

	@Override
	public Object invoke(Object arg1, Object arg2, Object arg3, Object arg4,
			Object arg5, Object arg6, Object arg7, Object arg8, Object arg9,
			Object arg10, Object arg11) {
		return invokeArray(arg1,arg2,arg3,arg4,arg5,arg6,arg7,arg8,arg9,arg10,arg11);
	}

	@Override
	public Object invoke(Object arg1, Object arg2, Object arg3, Object arg4,
			Object arg5, Object arg6, Object arg7, Object arg8, Object arg9,
			Object arg10, Object arg11, Object arg12) {
		return invokeArray(arg1,arg2,arg3,arg4,arg5,arg6,arg7,arg8,arg9,arg10,arg11,arg12);
	}

	@Override
	public Object invoke(Object arg1, Object arg2, Object arg3, Object arg4,
			Object arg5, Object arg6, Object arg7, Object arg8, Object arg9,
			Object arg10, Object arg11, Object arg12, Object arg13) {
		return invokeArray(arg1,arg2,arg3,arg4,arg5,arg6,arg7,arg8,arg9,arg10,arg11,arg12,arg13);
	}

	@Override
	public Object invoke(Object arg1, Object arg2, Object arg3, Object arg4,
			Object arg5, Object arg6, Object arg7, Object arg8, Object arg9,
			Object arg10, Object arg11, Object arg12, Object arg13, Object arg14) {
		return invokeArray(arg1,arg2,arg3,arg4,arg5,arg6,arg7,arg8,arg9,arg10,arg11,arg12,arg13,arg14);
	}

	@Override
	public Object invoke(Object arg1, Object arg2, Object arg3, Object arg4,
			Object arg5, Object arg6, Object arg7, Object arg8, Object arg9,
			Object arg10, Object arg11, Object arg12, Object arg13,
			Object arg14, Object arg15) {
		return invokeArray(arg1,arg2,arg3,arg4,arg5,arg6,arg7,arg8,arg9,arg10,arg11,arg12,arg13,arg14,arg15);
	}

	@Override
	public Object invoke(Object arg1, Object arg2, Object arg3, Object arg4,
			Object arg5, Object arg6, Object arg7, Object arg8, Object arg9,
			Object arg10, Object arg11, Object arg12, Object arg13,
			Object arg14, Object arg15, Object arg16) {
		return invokeArray(arg1,arg2,arg3,arg4,arg5,arg6,arg7,arg8,arg9,arg10,arg11,arg12,arg13,arg14,arg15,arg16);
	}

	@Override
	public Object invoke(Object arg1, Object arg2, Object arg3, Object arg4,
			Object arg5, Object arg6, Object arg7, Object arg8, Object arg9,
			Object arg10, Object arg11, Object arg12, Object arg13,
			Object arg14, Object arg15, Object arg16, Object arg17) {
		return invokeArray(arg1,arg2,arg3,arg4,arg5,arg6,arg7,arg8,arg9,arg10,arg11,arg12,arg13,arg14,arg15,arg16,arg17);
	}

	@Override
	public Object invoke(Object arg1, Object arg2, Object arg3, Object arg4,
			Object arg5, Object arg6, Object arg7, Object arg8, Object arg9,
			Object arg10, Object arg11, Object arg12, Object arg13,
			Object arg14, Object arg15, Object arg16, Object arg17, Object arg18) {
		return invokeArray(arg1,arg2,arg3,arg4,arg5,arg6,arg7,arg8,arg9,arg10,arg11,arg12,arg13,arg14,arg15,arg16,arg17,arg18);
	}

	@Override
	public Object invoke(Object arg1, Object arg2, Object arg3, Object arg4,
			Object arg5, Object arg6, Object arg7, Object arg8, Object arg9,
			Object arg10, Object arg11, Object arg12, Object arg13,
			Object arg14, Object arg15, Object arg16, Object arg17,
			Object arg18, Object arg19) {
		return invokeArray(arg1,arg2,arg3,arg4,arg5,arg6,arg7,arg8,arg9,arg10,arg11,arg12,arg13,arg14,arg15,arg16,arg17,arg18,arg19);
	}

	@Override
	public Object invoke(Object arg1, Object arg2, Object arg3, Object arg4,
			Object arg5, Object arg6, Object arg7, Object arg8, Object arg9,
			Object arg10, Object arg11, Object arg12, Object arg13,
			Object arg14, Object arg15, Object arg16, Object arg17,
			Object arg18, Object arg19, Object arg20) {
		return invokeArray(arg1,arg2,arg3,arg4,arg5,arg6,arg7,arg8,arg9,arg10,arg11,arg12,arg13,arg14,arg15,arg16,arg17,arg18,arg19,arg20);
	}

	@Override
	public Object invoke(Object arg1, Object arg2, Object arg3, Object arg4,
			Object arg5, Object arg6, Object arg7, Object arg8, Object arg9,
			Object arg10, Object arg11, Object arg12, Object arg13,
			Object arg14, Object arg15, Object arg16, Object arg17,
			Object arg18, Object arg19, Object arg20, Object... args) {
		int m=args.length;
		Object[] as=new Object[20+m];
		as[0]=arg1;
		as[1]=arg2;
		as[2]=arg3;
		as[3]=arg4;
		as[4]=arg5;
		as[5]=arg6;
		as[6]=arg7;
		as[7]=arg8;
		as[8]=arg9;
		as[9]=arg10;
		as[10]=arg11;
		as[11]=arg12;
		as[12]=arg13;
		as[13]=arg14;
		as[14]=arg15;
		as[15]=arg16;
		as[16]=arg17;
		as[17]=arg18;
		as[18]=arg19;
		as[19]=arg20;
		System.arraycopy(args, 0, as, 20, m);
		return invokeArray(as);
	}

}
