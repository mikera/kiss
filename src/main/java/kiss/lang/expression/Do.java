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
		// specialise based on the last expression in the do block, since this defines the return value
		if (length==0) return null;
		Expression end=exps[length-1];
		Expression send=end.specialise(type);
		if (send==null) return null;
		if (send==end) return this;
		Expression[] nexps=exps.clone();
		nexps[length-1]=send;
		return create(nexps);
	}
	
	@Override
	public Expression substitute(IPersistentMap bindings) {
		int i=0;
		Expression nx=null;
		for (;i<length; i++) {
			Expression x=exps[i];
			nx=x.substitute(bindings);
			if (nx==null) return null;
			if (nx!=x) break;
		}
		if (i==length) return this; // no changes
		Expression[] nexps=exps.clone();
		nexps[i++]=nx;
		for (;i<length; i++) {
			Expression x=exps[i];
			nx=x.substitute(bindings);
			if (nx==null) return null;
			nexps[i]=nx;
		}
		return create(nexps);
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
