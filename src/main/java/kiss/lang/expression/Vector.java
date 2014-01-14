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

public class Vector extends Expression {
	public Type TYPE = JavaType.create(APersistentVector.class);
	
	private List<Expression> vals;
	private int length;
	
	private Vector(List<Expression> vs) {
		this.vals=vs;
		this.length=vs.size();
	}
	
	public static Vector create (List<Expression> vs) {
		return new Vector(vs);
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
	public Expression substitute(IPersistentMap bindings) {
		int i=0;
		Expression nx=null;
		for (;i<length; i++) {
			Expression x=vals.get(i);
			nx=x.substitute(bindings);
			if (nx==null) return null;
			if (nx!=x) break;
		}
		if (i==length) return this; // no changes
		ArrayList<Expression> al=new ArrayList<Expression>();
		for (int j=0; j<i; j++) {
			al.add(vals.get(j));
		}
		al.add(nx);
		for (;i<length; i++) {
			Expression x=vals.get(i);
			nx=x.substitute(bindings);
			if (nx==null) return null;
			al.add(nx);
		}
		return create(al);
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
