package kiss.lang.impl;

import java.util.ArrayList;

import kiss.lang.Environment;
import kiss.lang.KFn;
import clojure.lang.IFn;
import clojure.lang.IPersistentVector;
import clojure.lang.ISeq;
import clojure.lang.RT;
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

	public static String typeName(Object result) {
		if (result==null) return "Null";
		return result.getClass().toString();
	}

	public static boolean truthy(Object o) {
		return (o!=null)&&(o!=Boolean.FALSE);
	}

	public static ISeq createSeq(Object... vals) {
		ArrayList<Object> al=new ArrayList<Object>();
		for (Object o: vals) {
			al.add(o);
		}
		return RT.seq(al);
	}

	public static boolean isPureFn(IFn fn) {
		if (fn instanceof KFn) {
			return ((KFn)fn).isPure();
		}
		return false;
	}

	public static boolean equalsWithNulls(Object a, Object b) {
		return (a==b)||((a!=null)&&a.equals(b));
	}

	public static boolean isClojureVar(Symbol sym) {
		String ns=sym.getNamespace();
		if (ns==null) return false;
		String name=sym.getName();
		return (RT.var(ns, name)!=null);
	}

	/**
	 * Trick function used to clear local values, enabling GC
	 */
	public static Environment ret1(Environment ret, Environment nil) {
		return ret;
	}
}
