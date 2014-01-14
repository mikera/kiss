package kiss.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import kiss.lang.Analyser;
import kiss.lang.Expression;
import kiss.lang.Type;
import kiss.lang.expression.Constant;
import kiss.lang.expression.If;
import kiss.lang.expression.Let;
import kiss.lang.expression.Lookup;
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

import clojure.lang.ISeq;
import clojure.lang.PersistentHashMap;
import clojure.lang.Symbol;

public class ExpressionTests {
	
	static final Expression[] testExprs={
		Constant.create(null),
		Constant.create("friend"),
		Let.create(Symbol.intern("foo"), Constant.create(3), Lookup.create("foo"))	
	}; 
	
	@Test 
	public void testSubstitutions() {
		for (Expression e:testExprs) {
			assertEquals(e,e.substitute(PersistentHashMap.EMPTY));
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
