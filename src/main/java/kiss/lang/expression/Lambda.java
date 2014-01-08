package kiss.lang.expression;

import kiss.lang.Environment;
import kiss.lang.Expression;
import kiss.lang.KFn;
import kiss.lang.Type;
import kiss.lang.impl.LambdaFn;
import kiss.lang.type.FunctionType;
import clojure.lang.IPersistentMap;
import clojure.lang.Symbol;

public class Lambda extends Expression {

	private FunctionType type;
	private Expression body;
	@SuppressWarnings("unused")
	private Type[] types;
	private Symbol[] syms;
	
	private Lambda(Expression body, Symbol[] syms, Type[] types) {
		this.body=body;
		this.types=types;
		this.type=FunctionType.create(body.getType(), types);
		this.syms=syms;
	}
	
	@Override
	public Type getType() {
		return type;
	}

	@Override
	public Environment compute(Environment d, IPersistentMap bindings) {
		KFn fn=LambdaFn.create(d,body,syms);
		return d.withResult(fn);
	}
}
