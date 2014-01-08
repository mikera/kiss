package kiss.lang.expression;

import clojure.lang.IPersistentMap;
import kiss.lang.Environment;
import kiss.lang.Expression;
import kiss.lang.Type;
import kiss.lang.type.JavaType;
import kiss.lang.type.NullType;

/**
 * A typed constant value in an expression
 * 
 * @author Mike
 */
public class Constant<T> extends Expression {
	private final T value;
	private final Type type;
	
	private Constant(T value) {
		this.value=value;
		if (value==null) {
			type=NullType.INSTANCE;
		} else {
			type=JavaType.analyse(value);
		}
	}
	
	public T getValue() {
		return value;
	}
	
	@Override
	public Type getType() {
		return type;
	}
	
	public static <T> Constant<T> create(T value) {
		return new Constant<T>(value);
	}

	@Override
	public Object eval(Environment e) {
		return value;
	}

	@Override
	public Environment compute(Environment d, IPersistentMap bindings) {
		return d.withResult(value);
	}
}
