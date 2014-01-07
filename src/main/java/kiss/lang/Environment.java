package kiss.lang;

import java.util.Iterator;

import clojure.lang.APersistentMap;
import clojure.lang.IMapEntry;
import clojure.lang.IPersistentCollection;
import clojure.lang.IPersistentMap;
import clojure.lang.ISeq;
import clojure.lang.PersistentHashMap;
import clojure.lang.RT;
import clojure.lang.Symbol;

public class Environment extends APersistentMap {
	
	public static final Environment EMPTY = new Environment();
	
	public final PersistentHashMap map;

	public Environment() {
		map=PersistentHashMap.EMPTY;
	}
	
	@Override
	public IPersistentMap assoc(Object key, Object val) {
		throw new UnsupportedOperationException();
	}

	@Override
	public IPersistentMap assocEx(Object key, Object val) {
		throw new UnsupportedOperationException();
	}

	@Override
	public IPersistentMap without(Object key) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	@Override
	public Iterator iterator() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean containsKey(Object key) {
		return map.containsKey(key);
	}

	@Override
	public IMapEntry entryAt(Object key) {
		Mapping m=(Mapping) map.get(key);
		if (m==null) return null;
		return m.toMapEntry(key);
	}

	@Override
	public int count() {
		return map.count();
	}

	@Override
	public IPersistentCollection empty() {
		return EMPTY;
	}

	@Override
	public ISeq seq() {
		return RT.seq(iterator());
	}

	@Override
	public Object valAt(Object key) {
		Mapping m=(Mapping) map.get(key);
		if (m==null) return null;
		return m.getValue();
	}

	@Override
	public Object valAt(Object key, Object notFound) {
		Mapping m=(Mapping) map.get(key);
		if (m==null) return notFound;
		return m.getValue();
	}

}
