package kiss.lang;

import clojure.lang.Symbol;
import clojure.lang.Var;
import mikera.cljutils.Clojure;
import kiss.lang.expression.Constant;
import kiss.lang.type.FunctionType;
import kiss.lang.type.JavaType;

public class RT {
	public static Environment ENVIRONMENT=Environment.EMPTY;
	

	public static <T> JavaType<T> type(Class<T> klass) {
		return JavaType.create(klass);
	}
	
	public static void importClojure(String name, Type t) {
		Var cv=Clojure.var(name);
		if (cv==null) throw new IllegalArgumentException("Can't import Clojure var: "+name);
		Object value=cv.deref();
		if (t instanceof FunctionType) {
			
		}
		ENVIRONMENT=ENVIRONMENT.define(Symbol.create(name), Constant.create(t, value));
	}
	
	static {
		importClojure ("+", FunctionType.create(type(Number.class), type(Number.class), type(Number.class)));
	}

}
