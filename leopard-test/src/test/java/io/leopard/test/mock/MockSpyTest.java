package io.leopard.test.mock;

import org.junit.Test;

public class MockSpyTest {

	@Test
	public void MockSpy() {
		new MockSpy();
	}

	@Test
	public void isIgnoreType() {
		Assert.assertTrue(MockSpy.isIgnoreType(int.class));
	}

}