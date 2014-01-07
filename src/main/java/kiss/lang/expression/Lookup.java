package kiss.lang.expression;

import java.util.Map.Entry;

import kiss.lang.Environment;
import kiss.lang.Expression;
import kiss.lang.Type;
import kiss.lang.impl.KissException;
import clojure.lang.RT;
import clojure.lang.Symbol;
import clojure.lang.Var;

public class Lookup extends Expression {
	private final Symbol sym;
	
	private Lookup(Symbol sym) {
		this.sym=sym;
	}

	public static Expression create(Symbol symbol) {
		return new Lookup(symbol);
	}

	@Override
	public Type getType() {
		return null;
	}

	@Override
	public Object eval(Environment e) {
		Entry<Symbol, Object> o=e.entryAt(sym);
		if (o!=null) return o.getValue();
		 
		Var v=RT.var(sym.getNamespace(),sym.getName());
		if (v!=null) return v.deref();
		
		throw new KissException("Cannot lookup symbol "+sym+" in environment");
	}

}
