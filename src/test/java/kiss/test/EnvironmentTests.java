package kiss.test;

import static org.junit.Assert.*;
import kiss.lang.Environment;
import kiss.lang.EvalResult;
import kiss.lang.Expression;
import kiss.lang.expression.Constant;
import kiss.lang.expression.Def;
import kiss.lang.expression.Lookup;

import org.junit.Test;

import clojure.lang.Symbol;

public class EnvironmentTests {

	@Test public void testDef() {
		Expression x=Def.create(Symbol.intern("foo"),Constant.create(1));
		Environment e=Environment.EMPTY;
		
		EvalResult r=x.interpret(e);
		Environment e2=r.getEnvironment();
		assertEquals(1,e2.get(Symbol.intern("foo")));
		assertEquals(null,r.getResult());
	}
	
	@Test public void testValidity() {
		EvalResult e=new EvalResult();
		e.validate();
		
		e=Def.create(Symbol.intern("foo"),Constant.create(1)).interpret(e);
		e.validate();
		
		e=Def.create(Symbol.intern("bar"),Lookup.create("foo")).interpret(e);
		e.validate();
		
		e=Def.create(Symbol.intern("bar"),Lookup.create("baz")).interpret(e);
		e.validate();
	}
}
