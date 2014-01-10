package kiss.lang.expression;

import clojure.lang.IPersistentMap;
import kiss.lang.Environment;
import kiss.lang.Expression;
import kiss.lang.Type;
import kiss.lang.type.JavaType;
import kiss.lang.type.Null;

/**
 * A typed constant value in an expression
 * 
 * @author Mike
 */
public class Constant<T> extends Expression {
	private final T value;
	private final Type type;
	
	private Constant(Type type, T value) {
		this.value=value;
		this.type=type;
	}
	
	private Constant(T value) {
		this ((value==null)?Null.INSTANCE:JavaType.analyse(value),value);
	}
	
	public static <T> Constant<T> create(T value) {
		return new Constant<T>(value);
	}

	
	public static <T> Constant<T> create(Type type, T value) {
		return new Constant<T>(value);
	}

	
	public T getValue() {
		return value;
	}
	
	@Override
	public Type getType() {
		return type;
	}

	@Override
	public Object eval(Environment e) {
		return value;
	}

	@Override
	public Environment compute(Environment d, IPersistentMap bindings) {
		return d.withResult(value);
	}

	@Override
	public Expression specialise(Type type) {
		if (type==this.type) return this;
		if (type.checkInstance(value)) return Constant.create(type.intersection(this.type),value); 
 		return null;
	}
}
