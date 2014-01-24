package kiss.test;

import static org.junit.Assert.*;
import kiss.lang.impl.KissUtils;

import org.junit.Test;

import clojure.lang.ISeq;

public class TestUtils {
	@Test public void testTruthy() {
		assertTrue(KissUtils.truthy(1));
		assertTrue(KissUtils.truthy(true));
		assertTrue(KissUtils.truthy(new Boolean(false))); // watch out for this one!!!
		
		assertFalse(KissUtils.truthy(null));
		assertFalse(KissUtils.truthy(Boolean.FALSE));
	}
	
	@Test public void testRead() {
		assertEquals(Long.valueOf(1),KissUtils.read("1"));
		assertTrue(KissUtils.read("(foo 1 3 4)") instanceof ISeq);
	}
}
