package kiss.lang.impl;

import clojure.lang.IPersistentVector;
import clojure.lang.Symbol;

public class KissUtils {

	public static IPersistentVector expectVector(Object x) {
		if (x instanceof IPersistentVector) {
			return (IPersistentVector)x;
		} else {
			throw new KissException("Expected vector but got: "+x);
		}
	}

	public static Symbol expectSymbol(Object x) {
		if (x instanceof Symbol) {
			return (Symbol)x;
		} else {
			throw new KissException("Expected vector but got: "+x);
		}
	}

}
