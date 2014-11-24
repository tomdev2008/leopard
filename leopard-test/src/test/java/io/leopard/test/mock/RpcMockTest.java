package io.leopard.test.mock;

import io.leopard.data.rpc.RpcClient;
import io.leopard.test4j.mock.LeopardMockRunner;

import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;

@RunWith(LeopardMockRunner.class)
@PrepareForTest({ RpcClient.class })
public class RpcMockTest {

	@Test
	public void RpcMock() {
		new RpcMock();
	}

	@Test
	public void doReturn() {
		RpcMock.doReturn("ok");
		Assert.assertEquals("ok", RpcClient.doGet("http://yy.com/", 10));
	}

	@Test
	public void verify() {
		RpcClient.doPost("http://yy.com/", (Map<String, Object>) null, 10);
		RpcMock.verify(1);
	}
}