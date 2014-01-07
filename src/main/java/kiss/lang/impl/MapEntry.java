package kiss.lang.impl;

import java.util.Map.Entry;

import clojure.lang.IMapEntry;
import clojure.lang.Symbol;

public class MapEntry implements IMapEntry {
	private final Symbol key;
	private Object value;

	public MapEntry(Symbol key2, Object value) {
		this.key=key2;
		this.value=value;
	}
	
	public static Entry<Symbol, Object> create(Symbol sym, Object o) {
		return new MapEntry(sym,o);
	}

	public Symbol getKey() {
		return key;
	}

	public void setKey(Object key) {
		throw new UnsupportedOperationException();
	}

	public Object getValue() {
		return value;
	}

	public Object setValue(Object value) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Object key() {
		return key;
	}

	@Override
	public Object val() {
		return value;
	}

}