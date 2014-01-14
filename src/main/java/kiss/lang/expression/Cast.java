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
	public Expression specialise(Type type) {
		if (type==this.type) return this;
		if (type.contains(this.type)) return this;
		Type it = type.intersection(this.type);
		if (it==Nothing.INSTANCE) return null;
		
		return create(it,body.specialise(it));
	}

	@Override
	public IPersistentSet getFreeSymbols(IPersistentSet s) {
		s=body.getFreeSymbols(s);
		return s;
	}

	@Override
	public Expression substitute(IPersistentMap bindings) {
		Expression nBody=body.substitute(bindings);
		if (nBody==body) return this;
		if (nBody==null) return null;
		return create(type,nBody);
	}
}
