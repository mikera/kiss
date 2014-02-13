package kiss.lang.impl;

import java.io.StringReader;
import java.util.ArrayList;

import kiss.lang.Environment;
import kiss.lang.Expression;
import kiss.lang.KFn;
import clojure.lang.IFn;
import clojure.lang.IPersistentVector;
import clojure.lang.ISeq;
import clojure.lang.LineNumberingPushbackReader;
import clojure.lang.LispReader;
import clojure.lang.RT;
import clojure.lang.Symbol;

public class KissUtils {

	public static Object read(String s) {
		return LispReader.read(new LineNumberingPushbackReader(new StringReader(s)), false, null, false);
	}
	

	public static Object eval(String s) {
		Object form=read(s);
		Expression ex=kiss.lang.Compiler.compile(form);
		return ex.eval();
	}
	
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

	public static java.util.List<Object> asList(ISeq s) {
		ArrayList<Object> al=new ArrayList<Object>();
		while (s!=null) {
			al.add(s.first());
			s=s.next();
		}
		return al;
	}
}
