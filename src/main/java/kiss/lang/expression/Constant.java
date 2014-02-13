package kiss.lang.expression;

import kiss.lang.Environment;
import kiss.lang.Expression;
import kiss.lang.Type;
import kiss.lang.impl.KissException;
import kiss.lang.type.Null;
import kiss.lang.type.Value;
import clojure.lang.IPersistentMap;
import clojure.lang.IPersistentSet;

/**
 * A typed constant value in an Expression
 * 
 * @author Mike
 */
public class Constant<T> extends Expression {
	private final T value;
	private final Type type;
	
	public static final Constant<?> NULL=new Constant<Object>(Null.INSTANCE,null);
	public static final Constant<Boolean> FALSE =new Constant<Boolean>(Boolean.FALSE);
	public static final Constant<Boolean> TRUE =new Constant<Boolean>(Boolean.TRUE);
	
	private Constant(Type type, T value) {
		this.value=value;
		this.type=type;
	}
	
	private Constant(T value) {
		this ((value==null)?Null.INSTANCE:Value.create(value),value);
	}
	
	@SuppressWarnings("unchecked")
	public static <T> Constant<T> create(T value) {
		if (value==null) return (Constant<T>) NULL;
		return new Constant<T>(value);
	}

	@SuppressWarnings("unchecked")
	public static <T> Constant<T> create(Type type, T value) {
		if ((value==null)&&(type instanceof Null)) return (Constant<T>) NULL;
		return new Constant<T>(type,value);
	}

	
	public T getValue() {
		return value;
	}
	
	@Override
	public Type getType() {
		return type;
	}
	
	@Override
	public boolean isConstant() {
		return true;
	}
	
	@Override
	public boolean isPure() {
		return true;
	}

	@Override
	public Object eval(Environment e) {
		return value;
	}
	
	@Override
	public Object eval() {
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
	
	@Override
	public IPersistentSet accumulateFreeSymbols(IPersistentSet s) {
		return s;
	}

	@Override
	public Expression substitute(IPersistentMap bindings) {
		return this;
	}
	
	@Override
	public void validate() {
		if (!type.checkInstance(value)) throw new KissException("Mismatched type!");
	}

}
