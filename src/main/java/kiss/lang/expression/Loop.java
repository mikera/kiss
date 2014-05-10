package kiss.lang.expression;

import clojure.lang.IPersistentMap;
import clojure.lang.IPersistentSet;
import clojure.lang.Symbol;
import kiss.lang.Environment;
import kiss.lang.Expression;
import kiss.lang.Type;

/**
 * A let expression, creates a local lexical binding
 * 
 * @author Mike
 */
public class Loop extends Expression {

	private final Symbol[] syms;
	private final Expression[] initials;
	private final Expression body;

	public Loop(Symbol[] syms, Expression[] initials, Expression body) {
		this.syms=syms;
		this.initials=initials;
		this.body=body;
	}

	public static Loop create(Symbol[] syms, Expression[] initials, Expression body) {
		return new Loop(syms,initials,body);
	}
	
	public Loop update(Symbol[] syms, Expression[] initials, Expression body) {
		Expression[] nis =this.initials;
		for (int i=0; i<initials.length; i++) {
			if (nis[i]!=initials[i]) {
				nis=initials;
				break;
			}
		}
		if ((this.syms==syms)&&(this.body==body)&&(this.initials==nis)) return this;
		return create(syms, nis,body);
	}
	
	@Override
	public Expression optimise() {
		Expression b=body.optimise();
		Expression[] is=initials.clone();
		for (int i=0; i<is.length; i++) {
			is[i]=is[i].optimise();
		}
		
		return update(syms,is,b);
	}
	
	@Override
	public Type getType() {
		return body.getType();
	}
	
	@Override
	public boolean isPure() {
		if (!body.isPure()) return false;
		for (Expression i:initials) {
			if (!i.isPure()) return false;
		}
		return true;
	}
	
	@Override
	public Environment compute(Environment d, IPersistentMap bindings) {
		for (int i=0; i<initials.length; i++) {
			d=initials[i].compute(d, bindings);
			Object result=d.getResult();
			bindings=bindings.assoc(syms[i], result);
		}
		while (true) {
			d=body.compute(d, bindings);
			Object ro=d.getResult();
			if (!(ro instanceof RecurResult)) return d;
			RecurResult rr=(RecurResult) ro;
			for (int i=0; i<syms.length; i++) {
				bindings=bindings.assoc(syms[i], rr.values[i]);
			}
		}		
	}
	
	public static final class RecurResult {
		public final Object[] values;
		
		public RecurResult(Object... values) {
			this.values=values;
		}
	}
	
	@Override
	public Expression specialise(Type type) {
		Expression newBody=body.specialise(type);
		return update(syms,initials,newBody);
	}
		
	@Override
	public Expression substitute(IPersistentMap bindings) {
		Expression[] nis=initials.clone();
		for (int i=0; i<initials.length; i++) {
			nis[i]=initials[i].substitute(bindings);
			bindings=bindings.without(syms[i]);
		}
		Expression nbody=body.substitute(bindings);
		if (nbody==null) return null;
		
		return update(syms,nis,nbody);
	}
	
	@Override
	public IPersistentSet accumulateFreeSymbols(IPersistentSet s) {
		s=body.accumulateFreeSymbols(s);
		s=s.disjoin(syms);
		for (Expression i: initials) {
			s=i.accumulateFreeSymbols(s);
		}
		return s;
	}
	
	@Override
	public void validate() {
		// OK?
	}


}
