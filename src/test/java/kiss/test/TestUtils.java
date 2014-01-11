package kiss.test;

import static org.junit.Assert.*;
import kiss.lang.impl.KissUtils;

import org.junit.Test;

public class TestUtils {
	@Test public void testTruthy() {
		assertTrue(KissUtils.truthy(1));
		assertTrue(KissUtils.truthy(true));
		assertTrue(KissUtils.truthy(new Boolean(false))); // watch out for this one!!!
		
		assertFalse(KissUtils.truthy(null));
		assertFalse(KissUtils.truthy(Boolean.FALSE));
	}
}
