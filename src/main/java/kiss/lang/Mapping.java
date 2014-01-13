package kiss.lang;

import java.util.Set;

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
	final Type type;
	final Object value;
	public Set<Symbol> dependencies;
	
	private Mapping(Object value, Type type) {
		this.type=type;
		this.value=value;
	}
	
	private Mapping(Object val) {
		this(val,JavaType.analyse(val));
	}

	public static Object create(Object val) {
		return new Mapping(val);
	}
	
	public Object getValue() {
		return value;
	}

	public IMapEntry toMapEntry(Object key) {
		return new MapEntry((Symbol)key,value);
	}

	public Type getType() {
		return type;
	}

}
