package kiss.lang.expression;

import clojure.lang.IPersistentMap;
import clojure.lang.IPersistentSet;
import clojure.lang.PersistentHashMap;
import clojure.lang.PersistentHashSet;
import clojure.lang.Symbol;
import kiss.lang.Environment;
import kiss.lang.Expression;
import kiss.lang.Type;

/**
 * A let expression, creates a local lexical binding
 * 
 * @author Mike
 */
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
	
	public Let update(Symbol sym, Expression value, Expression body) {
		if ((this.sym==sym)&&(this.body==body)&&(this.value==value)) return this;
		return create(sym, value,body);
	}
	
	@Override
	public Expression optimise() {
		Expression b=body.optimise();
		Expression v=value.optimise();
		if (value.isPure()) {
			IPersistentSet bfree= b.accumulateFreeSymbols(PersistentHashSet.EMPTY);
			if (!(bfree.contains(sym))) {
				return b;
			}
			if (value.isConstant()) {
				return b.substitute(PersistentHashMap.EMPTY.assoc(sym,value.eval())).optimise();
			}
		}
		return update(sym,v,b);
	}
	
	@Override
	public Type getType() {
		return body.getType();
	}
	
	@Override
	public boolean isPure() {
		if (!body.isPure()) return false;
		if (!value.isPure()) return false;
		return true;
	}
	
	@Override
	public Environment compute(Environment d, IPersistentMap bindings) {
		d=value.compute(d, bindings);
		Object result=d.getResult();
		bindings=bindings.assoc(sym, result);
		return body.compute(d, bindings);
	}
	
	@Override
	public Expression specialise(Type type) {
		Expression newBody=body.specialise(type);
		return update(sym,value,newBody);
	}
		
	@Override
	public Expression substitute(IPersistentMap bindings) {
		Expression nv=value.substitute(bindings);
		if (nv==null) return null;
		bindings=bindings.without(sym);
		Expression nbody=body.substitute(bindings);
		if (nbody==null) return null;
		
		return update(sym,nv,nbody);
	}
	
	@Override
	public IPersistentSet accumulateFreeSymbols(IPersistentSet s) {
		s=body.accumulateFreeSymbols(s);
		s=s.disjoin(sym);
		s=value.accumulateFreeSymbols(s);
		return s;
	}
	
	@Override
	public void validate() {
		// OK?
	}


}
