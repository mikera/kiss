package kiss.lang.expression;

import clojure.lang.ArraySeq;
import clojure.lang.IFn;
import clojure.lang.IPersistentMap;
import clojure.lang.RT;
import kiss.lang.Environment;
import kiss.lang.Expression;
import kiss.lang.Type;
import kiss.lang.impl.KissException;
import kiss.lang.impl.KissUtils;
import kiss.lang.type.Something;

public class Application extends Expression {
	private final Expression func;
	private final Expression[] params;
	private final int arity;
	
	private Application(Expression func, Expression[] params) {
		this.func=func;
		this.params=params;
		this.arity=params.length;
	}
	
	public static Expression create(Expression func, Expression[] params) {
		return new Application(func,params.clone());
	}
	
	public Expression optimise() {
		Expression nFunc=func.optimise();
		Expression[] nParams=new Expression[arity];
		boolean maybeApply=true;
		for (int i=0; i<arity; i++) {
			Expression on=params[i].optimise();;
			nParams[i]=on;
			if ((on instanceof Constant)) {
				// OK
			} else {
				maybeApply=false;
			}
		}
		if ((maybeApply)&&nFunc instanceof Constant) {
			IFn fn=(IFn) ((Constant)nFunc).getValue();
			if (KissUtils.isPureFn(fn)) {
				Object[] ps=new Object[arity];
				for (int i=0; i<arity; i++) {
					ps[i]=((Constant)nParams[i]).getValue();
				}
				return Constant.create(fn.applyTo(RT.seq(ps)));
			}
		}
		if (nFunc!=func) return Application.create(nFunc, nParams);
		for (int i=0; i<arity; i++) {
			if (params[i]!=nParams[i]) return Application.create(nFunc, nParams);
		}
		return this;
	}
	
	@SuppressWarnings("unused")
	@Override
	public Type getType() {
		Type ft=func.getType();
		// TODO: specialise function return type
		return Something.INSTANCE;
	}

	@Override
	public Environment compute(Environment d, IPersistentMap bindings) {
		d=func.compute(d, bindings);
		Object o=d.getResult();
		if (!(o instanceof IFn)) throw new KissException("Not a function!");
		IFn fn=(IFn)o;
		
		int n=params.length;
		Object[] args=new Object[n];
		for (int i=0; i<n; i++) {
			d=params[i].compute(d, bindings);
			args[i]=d.getResult();
		}
		
		return d.withResult(fn.applyTo(ArraySeq.create(args)));
	}

	@Override
	public Expression specialise(Type type) {
		// TODO Better specialisation of lambda application
		return Cast.create(type, this);
	}


}
