package kiss.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import kiss.lang.Type;
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
		Something.MAYBE,
		JavaType.create(Integer.class),
		JavaType.create(String.class),
		JavaType.create(Number.class),
		Null.INSTANCE,
		Maybe.create(JavaType.create(Integer.class)),
		Maybe.create(JavaType.create(String.class))
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
}
