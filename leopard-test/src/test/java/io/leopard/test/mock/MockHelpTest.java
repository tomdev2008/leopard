package io.leopard.test.mock;

import io.leopard.test.mock.MockHelp.Model;

import org.junit.Test;

public class MockHelpTest {

	@Test
	public void MockHelp() {
		new MockHelp();
	}

	@Test
	public void doReturnListString() {
		Assert.assertNull(MockHelp.doReturnListString());
	}

	@Test
	public void doReturnListInteger() {
		Assert.assertNull(MockHelp.doReturnListInteger());
	}

	@Test
	public void doReturnListModel() {
		Assert.assertNull(MockHelp.doReturnListModel());
	}

	@Test
	public void example() {
		MockHelp.example();
	}

	@Test
	public void Model() {
		new Model();
	}

}