package kiss.lang;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import kiss.lang.expression.Application;
import kiss.lang.expression.ClojureLookup;
import kiss.lang.expression.Constant;
import kiss.lang.expression.Def;
import kiss.lang.expression.Do;
import kiss.lang.expression.If;
import kiss.lang.expression.InstanceOf;
import kiss.lang.expression.Lambda;
import kiss.lang.expression.Let;
import kiss.lang.expression.Lookup;
import kiss.lang.expression.Vector;
import kiss.lang.impl.KissException;
import kiss.lang.impl.KissUtils;
import kiss.lang.type.Anything;
import kiss.lang.type.Intersection;
import kiss.lang.type.JavaType;
import kiss.lang.type.Nothing;
import kiss.lang.type.Null;
import kiss.lang.type.Union;
import clojure.lang.IPersistentMap;
import clojure.lang.IPersistentVector;
import clojure.lang.ISeq;
import clojure.lang.RT;
import clojure.lang.Symbol;

/**
 * Kiss language analyser
 * 
 * Design intent:
 * - Converts Clojure forms to Kiss ASTs
 * 
 * @author Mike
 */
public class Analyser {

	/**
	 * Converts a Kiss form into an Expression
	 * 
	 * @param form
	 * @return
	 */
	public static Expression analyse(Object form) {
		if (form instanceof Symbol) return analyseSymbol((Symbol)form);
		if (form instanceof ISeq) return analyseSeq((ISeq)form);
		if (form instanceof IPersistentVector) return analyseVector((IPersistentVector)form);
		if (form instanceof IPersistentMap) return analyseMap((IPersistentMap)form);
		return Constant.create(form);
	}
	
	@SuppressWarnings("unchecked")
	public static Type analyseType(Object form) {
		if (form instanceof Symbol) {
			return analyseTypeSymbol((Symbol)form);
		}
		if (form instanceof Class) {
			return JavaType.create((Class<Object>) form);
		}
		if (form instanceof ISeq) {
			return analyseTypeSeq((ISeq)form);
		}
		throw new KissException("Unrecognised type form: "+form);
	}
	
	private static Type analyseTypeSymbol(Symbol sym) {
		if (sym.equals(Symbols.ANY)) return Anything.INSTANCE;
		if (sym.equals(Symbols.NOTHING)) return Nothing.INSTANCE;
		if (sym.equals(Symbols.NIL)) return Null.INSTANCE;
		if (sym.equals(Symbols.TYPE)) return JavaType.KISS_TYPE;
		if (sym.equals(Symbols.SYMBOL_TYPE)) return JavaType.SYMBOL;
		if (sym.equals(Symbols.KEYWORD_TYPE)) return JavaType.KEYWORD;
		
		if (sym.getNamespace()==null) {
			String name=sym.getName();
			if (!name.contains(".")) name="java.lang."+name;
			Class<?> c=RT.classForName(name);
			if (c!=null) return JavaType.create(c);
		}
		throw new KissException("Unrecognised type symbol: "+sym);
	}

	private static Type analyseTypeSeq(ISeq s) {
		List<Object> al=KissUtils.asList(s);
		Symbol sym=(Symbol) al.get(0);
		if (sym.equals(Symbols.U)) {
			Type[] types=analyseSequenceOfTypes(al,1,al.size()-1);
			return Union.create(types);
		} else if (sym.equals(Symbols.I)) {
			Type[] types=analyseSequenceOfTypes(al,1,al.size()-1);
			return Intersection.create(types);
		}
		throw new KissException("Unrecognised type form: "+s);		
	}

	private static Type[] analyseSequenceOfTypes(List<Object> al, int start, int length) {
		Type[] types=new Type[length];
		for (int i=0; i<length; i++) {
			types[i]=analyseType(al.get(start+i));
		}
		return types;
	}

	public static Expression analyseSymbol(Symbol sym) {
		if (sym.equals(Symbols.NIL)) return Constant.NULL;
		if (sym.equals(Symbols.TRUE)) return Constant.TRUE;
		if (sym.equals(Symbols.FALSE)) return Constant.FALSE;
		if (sym.getNamespace()!=null) return ClojureLookup.create(sym);
		return Lookup.create(sym);
	}

	private static Expression analyseVector(IPersistentVector form) {
		ArrayList<Expression> al=new ArrayList<Expression>();
		int n=form.count();
		for (int i=0; i<n; i++) {
			al.add(analyse(form.nth(i)));
		}
		return Vector.create(al);
	}
	
	@SuppressWarnings("rawtypes")
	private static Expression analyseMap(IPersistentMap form) {
		HashMap<Expression,Expression> hm=new HashMap<Expression,Expression>();
		Iterator<Map.Entry> it=form.iterator();
		while (it.hasNext()) {
			Map.Entry e=it.next();
			hm.put(analyse(e.getKey()), analyse(e.getValue()));
		}
		return kiss.lang.expression.Map.create(hm);
	}

	
	private static Expression analyseSeq(ISeq form) {
		int n=form.count();
		if (n==0) return Constant.create(form);
		Object first=form.first();
		
		if (first instanceof Symbol) {
			Symbol s=(Symbol)first;
			if (s.equals(Symbols.LET)) {
				IPersistentVector v=KissUtils.expectVector(RT.second(form));
				int vc=v.count();
				if ((vc&1)!=0) throw new KissException("let requires an even number of binding forms");
				
				// start with expression body
				Expression e = analyse(RT.nth(form, 2));
				
				for (int i=vc-2; i>=0; i-=2) {
					Symbol sym=KissUtils.expectSymbol(v.nth(i));
					Expression exp=analyse(v.nth(i+1));
					e= Let.create(sym, exp, e);
				}
				return e;
			}
			
			if (s.equals(Symbols.IF)) {
				switch (n) {
				case 4:
					return If.create(analyse(RT.nth(form, 1)), analyse(RT.nth(form, 2)), analyse(RT.nth(form, 3)));
				case 3:
					return If.create(analyse(RT.nth(form, 1)), analyse(RT.nth(form, 2)),Constant.NULL);
				default:
					throw new KissException("Wrong number of forms in if expression: "+n);
				}		
			}
			
			if (s.equals(Symbols.INSTANCE)) {
				switch (n) {
				case 3:
					return InstanceOf.create(analyseType(RT.nth(form, 1)), analyse(RT.nth(form, 2)));
				default:
					throw new KissException("Wrong number of forms in instance? expression: "+n);
				}		
			}
			
			if (s.equals(Symbols.DEF)) {
				if (n!=3) throw new KissException("Wrong number of forms in def expression: "+n);
				Symbol sym=(Symbol)RT.nth(form,1);
				return Def.create(sym,analyse(RT.nth(form, 2)));		
			}

			if (s.equals(Symbols.DO)) {
				Expression[] exps=new Expression[n-1];
				for (int i=1; i<n; i++) {
					exps[i-1]=analyse(RT.nth(form, i));
				}
				return Do.create(exps);
			}
			
			if (s.equals(Symbols.FN)) {
				IPersistentVector v=KissUtils.expectVector(RT.second(form));
				int arity=v.count();
				Symbol[] syms=new Symbol[arity];
				Type[] types=new Type[arity];
				for (int i=0; i<arity; i++) {
					Symbol paramSym=(Symbol)v.nth(i);
					syms[i]=paramSym;
					Type paramType=Type.resolveTag(s);
					types[i]=paramType;
				}
				Expression body=analyse(RT.nth(form, 2));
				
				return Lambda.create(body,syms,types);
			}
		} 
		
		Expression fn=analyse(first);
		while (KissUtils.isMacro(fn)) {
			// TODO: macro expend with expansion passing?
			Expression expansion=fn;
			if (expansion==fn) break;
		}
		
		ISeq paramSeq=RT.next(form);
		int paramCount=RT.count(paramSeq);
		Expression[] params=new Expression[paramCount];
		int i=0;
		for (ISeq s=RT.seq(paramSeq); s!=null; s=s.next()) {
			params[i++]=analyse(s.first());
		}
		
		return Application.create(fn,params);
		
		// throw new KissException("Unexpected form: "+form);
	}
}
