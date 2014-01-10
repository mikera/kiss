package kiss.test;

import static org.junit.Assert.*;
import kiss.lang.Type;
import kiss.lang.expression.Cast;
import kiss.lang.expression.Constant;
import kiss.lang.impl.KissException;
import kiss.lang.type.Anything;
import kiss.lang.type.FunctionType;
import kiss.lang.type.JavaType;
import kiss.lang.type.Maybe;
import kiss.lang.type.Nothing;
import kiss.lang.type.Null;
import kiss.lang.type.Something;

import org.junit.Test;

public class TypeTests {
	
	static final Type[] testTypes={
		Nothing.INSTANCE,
		Something.INSTANCE,
		Anything.INSTANCE,
		JavaType.create(Integer.class),
		JavaType.create(String.class),
		JavaType.create(Number.class),
		Null.INSTANCE,
		Maybe.create(JavaType.create(Integer.class)),
		Maybe.create(JavaType.create(String.class)),
		FunctionType.create(Something.INSTANCE, Something.INSTANCE),
		FunctionType.create(Something.INSTANCE),
		FunctionType.create(Something.INSTANCE, JavaType.create(Number.class))
	}; 

	@Test public void testFnType() {
		FunctionType t=FunctionType.create(Null.INSTANCE,JavaType.create(Integer.class));
		assertEquals(1,t.getArity());
		
		assertTrue(t.contains(FunctionType.create(Null.INSTANCE,JavaType.create(Number.class))));
		assertFalse(t.contains(FunctionType.create(Null.INSTANCE,JavaType.create(String.class))));
	}
	
	@Test public void testIntersections() {
		for (Type a:testTypes) {
			for (Type b: testTypes) {
				Type c=a.intersection(b);
				assertEquals(c,b.intersection(a));
			}
		}
	}
	
	@SuppressWarnings("unused")
	@Test public void testCast() {
		try {
			Cast cast=Cast.create(String.class, Constant.create(10));
			fail("Cast should throw exception if cast is not possible");
		} catch (KissException ke) {
			// OK!
		}
		
	}
}
