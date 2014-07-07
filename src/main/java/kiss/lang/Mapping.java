package kiss.lang;

import kiss.lang.expression.Constant;
import kiss.lang.impl.KissException;
import kiss.lang.impl.MapEntry;
import kiss.lang.type.JavaType;
import clojure.lang.IMapEntry;
import clojure.lang.IPersistentSet;
import clojure.lang.PersistentHashSet;
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
	private final IPersistentSet unboundDeps;
	
	private Mapping(Expression exp, Object value, Type type, IPersistentSet unbound) {
		this.type=type;
		this.exp=exp;
		this.value=value;
		this.unboundDeps=unbound;
	}
	
	public static Object create(Object val) {
		return new Mapping(Constant.create(val),val,JavaType.analyse(val),null);
	}
	
	public static Object createExpression(Expression ex, Object val, IPersistentSet unbound) {
		return new Mapping(ex,val,ex.getType(),unbound);
	}
	
	public Object getValue() {
		if (unboundDeps==null) {
			return value;
		} else {
			throw new KissException("Free symbols cannot be resolved: "+unboundDeps.toString());
		}
	}
	
	public boolean isBound() {
		return (unboundDeps==null);
	}
	
	public Object maybeValue() {
		return value;
	}
	
	public Expression getExpression() {
		return exp;
	}

	public IMapEntry toMapEntry(Object key) {
		if (!isBound()) throw new KissException("Free symbols cannot be resolved: "+unboundDeps.toString());
		return new MapEntry((Symbol)key,value);
	}

	public Type getType() {
		return type;
	}

	public IPersistentSet getUnbound() {
		return (unboundDeps==null)?PersistentHashSet.EMPTY:unboundDeps;
	}
}
