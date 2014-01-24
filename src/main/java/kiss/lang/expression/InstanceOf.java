package kiss.lang.expression;

import clojure.lang.IPersistentMap;
import clojure.lang.IPersistentSet;
import kiss.lang.Environment;
import kiss.lang.Expression;
import kiss.lang.Type;
import kiss.lang.type.JavaType;
import kiss.lang.type.Nothing;

public class InstanceOf extends Expression {

	private final JavaType<?> type;
	private final Expression body;
	
	private InstanceOf(JavaType<?> type, Expression body) {
		this.type=type;
		this.body=body;
	}
	
	public static Expression create(Type t, Expression body) {
		JavaType type=t.toJavaType();
		return new InstanceOf(type,body);
	}
	
	public InstanceOf update(Type t, Expression body) {
		JavaType type=t.toJavaType();
		if ((type==this.type)&&(body==this.body)) return this;
		return new InstanceOf(type,body);
	}
	
	@Override
	public Type getType() {
		// TODO: primitive boolean? Or Kiss Bool type?
		return JavaType.BOOLEAN;
	}
	
	@Override
	public Expression optimise() {
		Type bt=body.getType();
		if (type.contains(bt)) return Constant.TRUE;
		if (type.intersection(bt)==Nothing.INSTANCE) return Constant.FALSE;
		return this;
	}

	@Override
	public Expression specialise(Type type) {
		return this;
	}

	@Override
	public Expression substitute(IPersistentMap bindings) {
		return update(type,body.substitute(bindings));
	}

	@Override
	public Environment compute(Environment d, IPersistentMap bindings) {
		d=body.compute(d, bindings);
		return d.withResult(type.checkInstance(d.getResult()));
	}

	@Override
	public IPersistentSet accumulateFreeSymbols(IPersistentSet s) {
		return body.accumulateFreeSymbols(s);
	}

	@Override
	public void validate() {
		// TODO Auto-generated method stub
		
	}

}
