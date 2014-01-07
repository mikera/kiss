package kiss.lang.expression;

import kiss.lang.type.JavaType;

public class Constant<T> {
	private final T value;
	private final JavaType<T> type;
	
	private Constant(T value) {
		this.value=value;
		type=JavaType.analyse(value);
	}
	
	public T getValue() {
		return value;
	}
	
	public JavaType<T> gettype() {
		return type;
	}
	
	public Constant<T> create(T value) {
		return new Constant<T>(value);
	}
}
