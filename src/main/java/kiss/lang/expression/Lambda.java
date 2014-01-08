package kiss.lang.expression;

import java.util.Map.Entry;

import kiss.lang.Environment;
import kiss.lang.Expression;
import kiss.lang.KFn;
import kiss.lang.Type;
import kiss.lang.impl.LambdaFn;
import kiss.lang.type.FunctionType;
import clojure.lang.IPersistentMap;
import clojure.lang.ISeq;
import clojure.lang.Symbol;

/**
 * A lambda expression, equivalent to Clojure "fn" 
 * 
 * Notes:
 * - only supports fixed arity at present 
 * 
 * @author Mike
 */
public class Lambda extends Expression {

	private FunctionType type;
	private Expression body;
	@SuppressWarnings("unused")
	private Type[] types;
	private Symbol[] syms;
	
	private Lambda(Expression body, Symbol[] syms, Type[] types) {
		this.body=body;
		this.types=types;
		this.type=FunctionType.create(body.getType(), types);
		this.syms=syms;
	}
	
	public static Lambda create(Expression body, Symbol[] syms, Type[] types) {
		return new Lambda(body,syms,types);
	}
	
	@Override
	public Type getType() {
		return type;
	}

	@Override
	public Environment compute(Environment d, IPersistentMap bindings) {
		// TODO is this sensible? capture the dynamic environment at exact point of lambda creation?
		Environment e=d;
		for (ISeq s= bindings.seq(); s!=null; s=s.next()) {
			Entry<?, ?> me=(Entry<?, ?>)s.first();
			e=e.assoc(me.getKey(),me.getValue());
		}
		
		KFn fn=LambdaFn.create(e,body,syms);
		return d.withResult(fn);
	}
}
