package kiss.lang;

import java.util.ArrayList;

import kiss.lang.expression.Application;
import kiss.lang.expression.ClojureLookup;
import kiss.lang.expression.Constant;
import kiss.lang.expression.Def;
import kiss.lang.expression.Do;
import kiss.lang.expression.If;
import kiss.lang.expression.Lambda;
import kiss.lang.expression.Let;
import kiss.lang.expression.Lookup;
import kiss.lang.expression.Vector;
import kiss.lang.impl.KissException;
import kiss.lang.impl.KissUtils;
import kiss.lang.type.JavaType;
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
		return Constant.create(form);
	}
	
	@SuppressWarnings("unchecked")
	public static Type analyseType(Object form) {
		if (form instanceof Class) {
			return JavaType.create((Class<Object>) form);
		}
		if (form instanceof ISeq) {
			return analyseTypeSeq((ISeq)form);
		}
		throw new KissException("Unrecognised type form: "+form);
	}
	
	private static Type analyseTypeSeq(ISeq s) {
		throw new KissException("Unrecognised type seq: "+s);
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

	
	private static Expression analyseSeq(ISeq form) {
		int n=form.count();
		if (n==0) return Constant.create(form);
		Object first=form.first();
		
		if (first instanceof Symbol) {
			Symbol s=(Symbol)first;
			if (s.equals(Symbols.LET)) {
				IPersistentVector v=KissUtils.expectVector(RT.second(form));
				Symbol sym=KissUtils.expectSymbol(v.nth(0));
				
				return Let.create(sym, analyse(v.nth(1)), analyse(RT.nth(form, 2)));
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
		
		ISeq paramSeq=RT.next(form);
		int paramCount=RT.count(paramSeq);
		Expression[] params=new Expression[paramCount];
		int i=0;
		for (ISeq s=RT.seq(paramSeq); s!=null; s=s.next()) {
			params[i++]=analyse(s.first());
		}
		
		return Application.create(analyse(first),params);
		
		// throw new KissException("Unexpected form: "+form);
	}
}
