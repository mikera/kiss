package kiss.test;

import static org.junit.Assert.*;
import kiss.lang.Type;
import kiss.lang.expression.Cast;
import kiss.lang.expression.Constant;
import kiss.lang.impl.KissException;
import kiss.lang.type.Anything;
import kiss.lang.type.ExactValue;
import kiss.lang.type.FunctionType;
import kiss.lang.type.Intersection;
import kiss.lang.type.JavaType;
import kiss.lang.type.Maybe;
import kiss.lang.type.Not;
import kiss.lang.type.Nothing;
import kiss.lang.type.Null;
import kiss.lang.type.Something;
import kiss.lang.type.Union;

import org.junit.Test;

public class TypeTests {
	
	static final Type[] testTypes={
		Nothing.INSTANCE,
		Something.INSTANCE,
		Anything.INSTANCE,
		JavaType.create(Integer.class),
		JavaType.create(String.class),
		JavaType.create(Number.class),
		ExactValue.create("foo"),
		ExactValue.create(1),
		Null.INSTANCE,
		Maybe.create(JavaType.create(Integer.class)),
		Maybe.create(JavaType.create(String.class)),
		Not.create(JavaType.create(Integer.class)),
		Not.create(ExactValue.create("foo")),
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
	
	@Test public void testExactValue() {
		assertTrue(Null.INSTANCE==ExactValue.create(null));
	}
	
	@Test public void testIntersections() {
		for (Type a:testTypes) {
			for (Type b: testTypes) {
				if (!(a.isWellBehaved()&&b.isWellBehaved())) continue;
				Type c=a.intersection(b);
				if (!c.equals(b.intersection(a))) throw new KissException(a+ " x "+b);
 				assertEquals(c,b.intersection(a));
			}
		}
		
		for (Type a:testTypes) {
			assertTrue(a==a.intersection(Anything.INSTANCE));
			assertTrue(a==Anything.INSTANCE.intersection(a));
			assertTrue(Nothing.INSTANCE==a.intersection(Nothing.INSTANCE));
			assertTrue(Nothing.INSTANCE==Nothing.INSTANCE.intersection(a));
		}
	}
	
	@Test 
	public void testProperties() {
		for (Type a:testTypes) {
			if (a.canBeNull()) assertTrue(a.checkInstance(null));
			if (a.canBeFalsey()) assertTrue("Issue with: "+a, a.checkInstance(null)||a.checkInstance(Boolean.FALSE));
		}
		
	}
	
	@Test public void testOddIntersections() {
		assertTrue(Intersection.create(Null.INSTANCE).checkInstance(null));
		assertFalse(Intersection.create(Null.INSTANCE,JavaType.create(Integer.class)).checkInstance(null));
	}
	
	@Test public void testMiscUnions() {
		assertEquals(Null.INSTANCE,Union.create(Null.INSTANCE));
		assertEquals(JavaType.create(Number.class),Union.create(JavaType.create(Number.class),JavaType.create(Integer.class)));
		assertEquals(JavaType.create(String.class),Union.create(Nothing.INSTANCE,JavaType.create(String.class)));
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
