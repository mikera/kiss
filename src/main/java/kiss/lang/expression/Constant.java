package kiss.lang.expression;

import kiss.lang.Environment;
import kiss.lang.Expression;
import kiss.lang.type.JavaType;

public class Constant<T> extends Expression {
	private final T value;
	private final JavaType<T> type;
	
	private Constant(T value) {
		this.value=value;
		type=JavaType.analyse(value);
	}
	
	public T getValue() {
		return value;
	}
	
	@Override
	public JavaType<T> getType() {
		return type;
	}
	
	public Constant<T> create(T value) {
		return new Constant<T>(value);
	}

	@Override
	public Object eval(Environment e) {
		return value;
	}
}
