package kiss.lang;

import kiss.lang.expression.Constant;
import kiss.lang.impl.KissException;
import kiss.lang.impl.MapEntry;
import kiss.lang.type.JavaType;
import clojure.lang.IMapEntry;
import clojure.lang.IPersistentSet;
import clojure.lang.Symbol;

/**
 * A Kiss Environment mapping
 * 
 * @author Mike
 *
 */
public class Mapping {
	private final Type type;
	private final Expression exp;
	private final Object value;
	private final IPersistentSet free;
	
	private Mapping(Expression exp, Object value, Type type, IPersistentSet free) {
		this.type=type;
		this.exp=exp;
		this.value=value;
		this.free=free;
	}
	
	public static Object create(Object val) {
		return new Mapping(Constant.create(val),val,JavaType.analyse(val),null);
	}
	
	public static Object createExpression(Expression ex, Object val, IPersistentSet freeSymbols) {
		return new Mapping(ex,val,ex.getType(),freeSymbols);
	}
	
	public Object getValue() {
		if (free==null) {
			return value;
		} else {
			throw new KissException("Free symbols cannot be resolved: "+free.toString());
		}
	}
	
	public boolean isBound() {
		return (free==null);
	}
	
	public Object maybeValue() {
		return value;
	}
	
	public Expression getExpression() {
		return exp;
	}

	public IMapEntry toMapEntry(Object key) {
		if (!isBound()) throw new KissException("Free symbols cannot be resolved: "+free.toString());
		return new MapEntry((Symbol)key,value);
	}

	public Type getType() {
		return type;
	}
}
