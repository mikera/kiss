package kiss.test;

import static org.junit.Assert.*;
import kiss.lang.Environment;
import kiss.lang.Expression;
import kiss.lang.expression.Constant;
import kiss.lang.expression.Def;

import org.junit.Test;

import clojure.lang.Symbol;

public class EnvironmentTests {

	@Test public void testDef() {
		Expression x=Def.create(Symbol.intern("foo"),Constant.create(1));
		Environment e=Environment.EMPTY;
		
		Environment e2=(Environment)x.eval(e);
		assertEquals(1,e2.get(Symbol.intern("foo")));
	}
}
