package kiss.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import kiss.lang.Analyser;
import kiss.lang.Expression;
import kiss.lang.expression.Constant;
import kiss.lang.expression.If;
import kiss.lang.impl.KissUtils;

import org.junit.Test;

import clojure.lang.ISeq;
import clojure.lang.Symbol;

public class ExpressionTests {
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
