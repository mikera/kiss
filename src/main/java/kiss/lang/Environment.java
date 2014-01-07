package kiss.lang;

import java.util.Iterator;

import clojure.lang.APersistentMap;
import clojure.lang.IMapEntry;
import clojure.lang.IPersistentCollection;
import clojure.lang.IPersistentMap;
import clojure.lang.ISeq;
import clojure.lang.Symbol;

public class Environment extends APersistentMap {
	
	public static final Environment EMPTY = new Environment();

	@Override
	public IPersistentMap assoc(Object key, Object val) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IPersistentMap assocEx(Object key, Object val) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IPersistentMap without(Object key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Iterator iterator() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean containsKey(Object key) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public IMapEntry entryAt(Object key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int count() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public IPersistentCollection empty() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ISeq seq() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object valAt(Object key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object valAt(Object key, Object notFound) {
		// TODO Auto-generated method stub
		return null;
	}

}
