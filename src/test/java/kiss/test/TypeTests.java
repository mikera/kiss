package kiss.test;

import static org.junit.Assert.*;
import kiss.lang.Type;
import kiss.lang.type.FunctionType;
import kiss.lang.type.JavaType;
import kiss.lang.type.NullType;

import org.junit.Test;

public class TypeTests {

	@Test public void testFnType() {
		FunctionType t=FunctionType.create(NullType.INSTANCE,JavaType.create(Integer.class));
		assertEquals(1,t.getArity());
		
		assertTrue(t.contains(FunctionType.create(NullType.INSTANCE,JavaType.create(Number.class))));
		assertFalse(t.contains(FunctionType.create(NullType.INSTANCE,JavaType.create(String.class))));
	}
}
