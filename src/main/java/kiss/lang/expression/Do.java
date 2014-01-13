package kiss.lang.expression;

import clojure.lang.IPersistentMap;
import clojure.lang.IPersistentSet;
import kiss.lang.Environment;
import kiss.lang.Expression;
import kiss.lang.Type;

public class Do extends kiss.lang.Expression {
	private final Expression[] exps;
	private final int length;
	
	private Do(Expression[] exps) {
		this.exps=exps;
		length=exps.length;
	}
	
	public static Do create(Expression... exps) {
		return new Do(exps);
	}
	
	@Override
	public Type getType() {
		return exps[length-1].getType();
	}

	@Override
	public Expression specialise(Type type) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Environment compute(Environment e, IPersistentMap bindings) {
		for (int i=0; i<length; i++) {
			e=exps[i].compute(e,bindings);
		}
		return e;
	}

	@Override
	public IPersistentSet getFreeSymbols(IPersistentSet s) {
		for (int i=0; i<length; i++) {
			s=exps[i].getFreeSymbols(s);
		}
		return s;
	}

}
