package kiss.lang.expression;

import clojure.lang.IPersistentMap;
import clojure.lang.IPersistentSet;
import kiss.lang.Environment;
import kiss.lang.Expression;
import kiss.lang.Type;
import kiss.lang.impl.KissException;
import kiss.lang.impl.KissUtils;
import kiss.lang.type.JavaType;
import kiss.lang.type.Nothing;

/**
 * An expression which casts the type of its body to a specified Type
 * 
 * Will throw a compile time exception if the cast is impossible
 * Will throw a runtime exception if the Java cast fails.
 * 
 * @author Mike
 *
 */
public class Cast extends Expression {
	Type type;
	Expression body;
	
	private Cast(Type type, Expression body) {
		this.type=type;
		this.body=body;
	}
	
	public static Cast create(Type type, Expression body) {
		Type bt=body.getType();
		if (bt.intersection(type)==Nothing.INSTANCE) {
			throw new KissException("Can't cast type "+bt+" to "+type);
		}
		return new Cast(type,body.specialise(type));
	}
	
	public static Cast create(Class<?> klass, Expression body) {
		return create(JavaType.create(klass),body);
	}
	
	@Override
	public Type getType() {
		return type;
	}
	
	@Override
	public Environment compute(Environment d, IPersistentMap bindings) {
		Environment ev= body.compute(d, bindings);
		Object result=ev.getResult();
		if (type.checkInstance(result)) {
			throw new KissException("Can't cast value of class "+KissUtils.typeName(result)+" to "+type);
			
		}
		return ev;
	}
	
	@Override
	public Expression optimise() {
		Expression b=body.optimise();
		Type bt=body.getType();
		if (b.isConstant()) {
			Object val=b.eval();
			if (type.checkInstance(val)) throw new KissException("Impossible to cast value "+val+" to type: "+type);
			// TODO: is this logic sound? what about interface casts?
			return b;
		} 
		Type t=type;
		if (t.contains(bt)) t=bt;
		if ((b==body)&&(t==type)) return this;
		return create(t,b);
	}
	
	@Override
	public boolean isPure() {
		return body.isPure();
	}

	@Override
	public Expression specialise(Type type) {
		if (type==this.type) return this;
		if (type.contains(this.type)) return this;
		Type it = type.intersection(this.type);
		if (it==Nothing.INSTANCE) return null;
		
		return create(it,body.specialise(it));
	}

	@Override
	public IPersistentSet accumulateFreeSymbols(IPersistentSet s) {
		s=body.accumulateFreeSymbols(s);
		return s;
	}

	@Override
	public Expression substitute(IPersistentMap bindings) {
		Expression nBody=body.substitute(bindings);
		if (nBody==body) return this;
		if (nBody==null) return null;
		return create(type,nBody);
	}
	
	@Override
	public void validate() {
		// OK?
	}

}
