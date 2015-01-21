package io.leopard.test.mock;

import io.leopard.burrow.util.StringUtil;

import org.junit.Test;
import org.powermock.core.classloader.annotations.PrepareForTest;

public class MockTestsTest {

	@Test
	public void MockTests() {

	}

	@Test
	public void getStaticClassName() {
		// Assert.assertNull(MockTests.getStaticClassName("test"));
	}

	@PrepareForTest(StringUtil.class)
	public static class PowerMockitoClass extends MockTests {

	}

	public static class PowerMockitoClass2 extends MockTests {

	}

	// @Test
	// public void before() {
	// {
	// PowerMockitoClass mockTests = new PowerMockitoClass();
	// mockTests.before();
	//
	// Assert.assertEquals("io.leopard.commons.utility.StringUtil", MockTests.getStaticClassName("StringUtil"));
	// }
	//
	// {
	// PowerMockitoClass2 mockTests = new PowerMockitoClass2();
	// mockTests.before();
	// }
	// }

}