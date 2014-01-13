package kiss.lang.expression;

import java.util.ArrayList;
import java.util.List;

import clojure.lang.APersistentVector;
import clojure.lang.IPersistentMap;
import clojure.lang.IPersistentSet;
import clojure.lang.PersistentVector;
import kiss.lang.Environment;
import kiss.lang.Expression;
import kiss.lang.Type;
import kiss.lang.type.JavaType;

public class VectorExpr extends Expression {
	public Type TYPE = JavaType.create(APersistentVector.class);
	
	private List<Expression> vals;
	private int length;
	
	public VectorExpr(List<Expression> vs) {
		this.vals=vs;
		this.length=vs.size();
	}

	@Override
	public Type getType() {
		return TYPE;
	}

	@Override
	public Expression specialise(Type type) {
		return null;
	}

	@Override
	public Environment compute(Environment d, IPersistentMap bindings) {
		ArrayList<Object> al=new ArrayList<Object>(length);
		for (int i=0; i<length; i++) {
			d=vals.get(i).compute(d, bindings);
			al.add(d.getResult());
		}
		return d.withResult(PersistentVector.create(al));
	}
	
	@Override
	public IPersistentSet getFreeSymbols(IPersistentSet s) {
		for (Expression e:vals) {
			s=e.getFreeSymbols(s);
		}
		return s;
	}

}
