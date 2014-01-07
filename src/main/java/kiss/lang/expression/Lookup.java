package kiss.lang.expression;

import java.util.Map.Entry;

import kiss.lang.Environment;
import kiss.lang.Expression;
import kiss.lang.KissException;
import kiss.lang.Type;
import clojure.lang.Symbol;

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
		if (o==null) throw new KissException("Cannot lookup symbol "+sym+" in environment");
		return o.getValue();
	}

}
