package kiss.lang.type;

import kiss.lang.Type;

public class ExactValue<T> extends Type {
	private final T value;
	private final Class<T> klass;
	
	private ExactValue(T value) {
		this.value=value;
		this.klass=(Class<T>) value.getClass();
	}
	
	public static <T> Type create(T value) {
		if (value==null) return Null.INSTANCE;
		return new ExactValue<T>(value);
	}
	
	@Override
	public boolean checkInstance(Object o) {
		return value.equals(o);
	}
	
	@Override
	public Class<T> getJavaType() {
		return klass;
	}
	@Override
	public boolean maybeNull() {
		return false;
	}
	
	@Override
	public boolean maybeTruthy() {
		return value!=Boolean.FALSE;
	}
	
	@Override
	public boolean maybeFalsey() {
		return value==Boolean.FALSE;
	}
	
	@Override
	public boolean contains(Type t) {
		if (t==this) return true;
		if (t instanceof Nothing) return true;
		if (t instanceof ExactValue) {
			ExactValue<?> ev=(ExactValue<?>) t;
			if (ev.klass!=this.klass) return false;
			return ev.value.equals(this.value);
		}
		return false;
	}
	
	@Override
	public Type intersection(Type t) {
		if (t.checkInstance(value)) return this;
		return Nothing.INSTANCE;
	}

}
