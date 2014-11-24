package io.leopard.test.mock.stubbing;

import io.leopard.test.mock.proxy.Proxy;
import io.leopard.test4j.mock.LeopardMockRunner;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;

@RunWith(LeopardMockRunner.class)
@PrepareForTest({ Proxy.class })
public class AbstractAssertStubberTest {

	@Test
	public void when() {
		new AbstractAssertStubber().when("str");
	}

	@Test
	public void invoke() {
		new AbstractAssertStubber().invoke("str", null);
	}

}