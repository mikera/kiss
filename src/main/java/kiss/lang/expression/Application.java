package kiss.lang.expression;

import clojure.lang.ArraySeq;
import clojure.lang.IFn;
import kiss.lang.Environment;
import kiss.lang.Expression;
import kiss.lang.KissException;
import kiss.lang.Type;
import kiss.lang.type.AnyType;

public class Application extends Expression {
	private final Expression func;
	private final Expression[] params;
	
	private Application(Expression func, Expression[] params) {
		this.func=func;
		this.params=params;
	}
	
	public static Expression create(Expression func, Expression[] params) {
		return new Application(func,params.clone());
	}
	
	@SuppressWarnings("unused")
	@Override
	public Type getType() {
		Type ft=func.getType();
		// TODO: specialise function return type
		return AnyType.INSTANCE;
	}

	@Override
	public Object eval(Environment e) {
		Object o=func.eval(e);
		if (!(o instanceof IFn)) throw new KissException("Not a function!");
		IFn fn=(IFn)o;
		
		int n=params.length;
		Object[] args=new Object[n];
		for (int i=0; i<n; i++) {
			args[i]=params[i].eval(e);
		}
		
		return fn.applyTo(ArraySeq.create(args));
	}


}
