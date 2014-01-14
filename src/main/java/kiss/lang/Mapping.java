package kiss.lang;

import java.util.Set;

import kiss.lang.expression.Constant;
import kiss.lang.impl.MapEntry;
import kiss.lang.type.JavaType;
import clojure.lang.IMapEntry;
import clojure.lang.Symbol;

/**
 * A kiss Environment mapping
 * 
 * @author Mike
 *
 */
public class Mapping {
	private final Type type;
	private final Expression exp;
	private final Object value;
	public Set<Symbol> dependencies;
	
	private Mapping(Expression exp, Object value, Type type) {
		this.type=type;
		this.exp=exp;
		this.value=value;
	}
	
	public static Object create(Object val) {
		return new Mapping(Constant.create(val),val,JavaType.analyse(val));
	}
	
	public Object getValue() {
		return value;
	}
	
	public Expression getExpression() {
		return exp;
	}

	public IMapEntry toMapEntry(Object key) {
		return new MapEntry((Symbol)key,value);
	}

	public Type getType() {
		return type;
	}

}
