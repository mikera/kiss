package kiss.lang.expression;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import clojure.lang.APersistentMap;
import clojure.lang.IPersistentMap;
import clojure.lang.IPersistentSet;
import clojure.lang.PersistentHashMap;
import kiss.lang.Environment;
import kiss.lang.Expression;
import kiss.lang.Type;
import kiss.lang.impl.KissException;
import kiss.lang.type.JavaType;

/**
 * A map producing expression
 * @author Mike
 *
 */
public class Map extends Expression {
	public Type TYPE = JavaType.create(APersistentMap.class);
	
	private List<Expression> keys;
	private List<Expression> vals;
	private int length;
	
	private Map(List<Expression> ks, List<Expression> vs) {
		this.keys=ks;
		this.vals=vs;
		this.length=vs.size();
	}
	
	public static Map create (List<Expression> ks,List<Expression> vs) {
		return new Map(ks,vs);
	}
	
	public static Map create (java.util.Map<Expression,Expression> m) {
		ArrayList<Expression> alv=new ArrayList<Expression>();
		ArrayList<Expression> alk=new ArrayList<Expression>();
		for (Entry<Expression,Expression> e:m.entrySet()) {
			alk.add(e.getKey());
			alv.add(e.getValue());
		}
		return new Map(alk,alv);
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
		Expression nxv=null;
		Expression nxk=null;
		for (;i<length; i++) {
			Expression k=keys.get(i);
			nxk=k.substitute(bindings);
			Expression x=vals.get(i);
			nxv=x.substitute(bindings);
			
			if (nxk==null) return null;
			if (nxk!=k) break;
			if (nxv==null) return null;
			if (nxv!=x) break;			
		}
		if (i==length) return this; // no changes
		ArrayList<Expression> alv=new ArrayList<Expression>();
		ArrayList<Expression> alk=new ArrayList<Expression>();
		for (int j=0; j<i; j++) {
			alk.add(keys.get(j));
			alv.add(vals.get(j));
		}
		alk.add(nxk);
		alv.add(nxv);
		for (;i<length; i++) {
			Expression k=keys.get(i);
			nxk=k.substitute(bindings);
			if (nxk==null) return null;
			alk.add(nxk);

			Expression x=vals.get(i);
			nxv=x.substitute(bindings);
			if (nxv==null) return null;
			alv.add(nxv);
		}
		return create(alk,alv);
	}

	@Override
	public Environment interpret(Environment d, IPersistentMap bindings) {
		HashMap<Object,Object> hm=new HashMap<Object,Object>();
		for (int i=0; i<length; i++) {
			d=keys.get(i).interpret(d, bindings);
			if (d.isExiting()) return d;
			
			Object k=d.getResult();
			d=vals.get(i).interpret(d, bindings);
			if (d.isExiting()) return d;
			
			Object v=d.getResult();
			hm.put(k, v);
		}
		return d.withResult(PersistentHashMap.create(hm));
	}
	
	@Override
	public IPersistentSet accumulateFreeSymbols(IPersistentSet s) {
		for (Expression e:keys) {
			s=e.accumulateFreeSymbols(s);
		}
		for (Expression e:vals) {
			s=e.accumulateFreeSymbols(s);
		}
		return s;
	}
	

	@Override
	public void validate() {
		if (length!=vals.size()) throw new KissException("Mismatched vector length!");
	}


}
