package io.leopard.test.mock;

import io.leopard.test4j.mock.LeopardMockRunner;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;

@RunWith(LeopardMockRunner.class)
@PrepareForTest({ Mock.class })
public class AnyTest {

	@Test
	public void Any() {
		new Any();
	}

	@Test
	public void i() {
		Assert.assertEquals(0, Any.i());
	}

	@Test
	public void b() {
		Assert.assertEquals(false, Any.b());
	}

	@Test
	public void slist() {
		Assert.assertEquals(0, Any.slist().size());
	}

	@Test
	public void ilist() {
		Assert.assertEquals(0, Any.ilist().size());
	}

}