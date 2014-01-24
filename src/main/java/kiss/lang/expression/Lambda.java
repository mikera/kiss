package kiss.lang.expression;

import java.util.Arrays;
import java.util.Map.Entry;

import kiss.lang.Environment;
import kiss.lang.Expression;
import kiss.lang.KFn;
import kiss.lang.Type;
import kiss.lang.impl.LambdaFn;
import kiss.lang.type.Anything;
import kiss.lang.type.FunctionType;
import clojure.lang.IPersistentMap;
import clojure.lang.IPersistentSet;
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

	public static final Lambda IDENTITY=create(Lookup.create("x"),new Symbol[] {Symbol.intern("x")},new Type[] {Anything.INSTANCE});
	
	private final FunctionType type;
	private final Expression body;
	private final Type[] types;
	private final Symbol[] syms;
	private KFn compiled=null;
	
	private Lambda(Expression body, Symbol[] syms, Type[] types) {
		this.body=body;
		this.types=types;
		this.type=FunctionType.create(body.getType(), types);
		this.syms=syms;
	}
	
	public static Lambda create(Expression body, Symbol[] syms, Type[] types) {
		return new Lambda(body,syms,types);
	}
	
	public Lambda update(Expression body, Symbol[] syms, Type[] types) {
		if ((body==this.body)&&(Arrays.equals(syms, this.syms))&&(Arrays.equals(types, this.types))) return this;
		return new Lambda(body,syms,types);
	}
	
	@Override
	public Type getType() {
		return type;
	}

	@Override
	public Environment compute(Environment d, IPersistentMap bindings) {
		if (compiled!=null) return d.withResult(compiled);
		
		// TODO is this sensible? capture the dynamic environment at exact point of lambda creation?
		Environment e=d;
		for (ISeq s= bindings.seq(); s!=null; s=s.next()) {
			Entry<?, ?> me=(Entry<?, ?>)s.first();
			e=e.assoc(me.getKey(),me.getValue());
		}
		
		KFn fn=LambdaFn.create(e,body,syms);
		return d.withResult(fn);
	}
	
	@Override
	public boolean isPure() {
		return body.isPure();
	}

	@Override
	public Expression specialise(Type type) {
		if (this.type==type) return this;
		if (type.contains(this.type)) return this;
		return update(body.specialise(type),syms,types);
	}
	
	@Override
	public Expression substitute(IPersistentMap bindings) {
		for (Symbol s:syms) {
			bindings=bindings.without(s);
		}
		Expression nbody=body.substitute(bindings);
		if (nbody==null) return null;
		
		return update(nbody,syms,types);
	}
	
	@Override
	public Lambda optimise() {
		return update(body.optimise(),syms,types);
	}
	
	@Override
	public IPersistentSet accumulateFreeSymbols(IPersistentSet s) {
		s=body.accumulateFreeSymbols(s);
		for (Symbol sym:syms) {
			s=s.disjoin(sym);
		}
		return s;
	}

	@Override
	public void validate() {
		// OK?
		
	}
}
