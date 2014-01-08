package kiss.lang.expression;

import clojure.lang.IPersistentMap;
import clojure.lang.Symbol;
import kiss.lang.Environment;
import kiss.lang.Expression;
import kiss.lang.Type;

public class Let extends Expression {

	private final Symbol sym;
	private final Expression value;
	private final Expression body;

	public Let(Symbol sym, Expression value, Expression body) {
		this.sym=sym;
		this.value=value;
		this.body=body;
	}

	public static Let create(Symbol sym, Expression value, Expression body) {
		return new Let(sym,value,body);
	}
	
	@Override
	public Type getType() {
		return body.getType();
	}
	
	@Override
	public Environment compute(Environment d, IPersistentMap bindings) {
		d=value.compute(d, bindings);
		bindings=bindings.assoc(sym, d.getResult());
		return body.compute(d, bindings);
	}

}
