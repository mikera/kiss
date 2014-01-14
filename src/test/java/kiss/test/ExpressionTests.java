package kiss.test;

import static org.junit.Assert.*;
import kiss.lang.Analyser;
import kiss.lang.Expression;
import kiss.lang.Type;
import kiss.lang.expression.Application;
import kiss.lang.expression.Constant;
import kiss.lang.expression.Do;
import kiss.lang.expression.If;
import kiss.lang.expression.Lambda;
import kiss.lang.expression.Let;
import kiss.lang.expression.Lookup;
import kiss.lang.impl.KissException;
import kiss.lang.impl.KissUtils;
import kiss.lang.type.Anything;
import kiss.lang.type.FunctionType;
import kiss.lang.type.JavaType;
import kiss.lang.type.Maybe;
import kiss.lang.type.Not;
import kiss.lang.type.Nothing;
import kiss.lang.type.Null;
import kiss.lang.type.Something;
import kiss.lang.type.Value;

import org.junit.Test;

import clojure.lang.IFn;
import clojure.lang.IPersistentSet;
import clojure.lang.ISeq;
import clojure.lang.PersistentHashMap;
import clojure.lang.PersistentHashSet;
import clojure.lang.Symbol;

public class ExpressionTests {
	
	static final Expression[] testExprs={
		Constant.create(null),
		Constant.create("friend"),
		Let.create(Symbol.intern("foo"), Constant.create(3), Lookup.create("foo")),
		Lambda.IDENTITY,
		Lookup.create("foo"),
		If.create(Constant.create(null), Constant.create(1), Constant.create(2)),
		Do.create(Constant.create(1)),
		Do.create(Constant.create(1),Lookup.create("foo")),
		Application.create(Lambda.IDENTITY, Constant.create(3))
	}; 
	
	@Test
	public void testIdentity() {
		Lambda id=Lambda.IDENTITY;
		FunctionType ft=(FunctionType) id.getType();
		assertEquals(Anything.INSTANCE,ft.getReturnType());
		IFn fn=(IFn) id.eval();
		assertEquals(1,fn.invoke(1));
		assertTrue(ft.checkInstance(fn));
	}
	
	@Test 
	public void testSubstitutions() {
		for (Expression e:testExprs) {
			try {
				assertEquals(e,e.substitute(PersistentHashMap.EMPTY));
				
				IPersistentSet free=e.getFreeSymbols(PersistentHashSet.EMPTY);
				if (free.count()==0) {
					Object result=e.eval(); // should work
					assertTrue(e.getType().checkInstance(result));
				} else {
					try {
						e.eval();
						fail();
					} catch (KissException t) {
						// OK!
					}
				}				
			} catch (Throwable t) {
				throw new KissException("Error testing expression "+e,t);
			}
		}
	}
	
	@Test
	public void testProperties() {
		for (Expression e:testExprs) {
			try {
				e.validate();		
			} catch (Throwable t) {
				throw new KissException("Error testing expression "+e,t);
			}
		}		
	}
	
	@Test
	public void testConstants() {
		assertNull(Constant.create(null).eval());
		assertEquals(1,Constant.create(1).eval());
		assertEquals("foo",Constant.create("foo").eval());

	}
	
	
	
	@Test
	public void testIf() {
		assertEquals(2,If.create(Constant.create(null), Constant.create(1), Constant.create(2)).eval());
		
		ISeq s=KissUtils.createSeq(Symbol.intern("if"),Symbol.intern("nil"),1,2);
		Expression x=Analyser.analyse(s);
		assertEquals(2,x.eval());

	}
}
