package io.leopard.test.mock;

import io.leopard.data.rpc.RpcClient;
import io.leopard.test4j.mock.LeopardMockRunner;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;

@RunWith(LeopardMockRunner.class)
@PrepareForTest({ RpcClient.class })
public class RpcAssertTest {

	@Test
	public void RpcAssert() {
		new RpcAssert();
	}

	public static class NewsClient {
		public String getContent() {
			return "ok";
		}
	}

	@Test
	public void doReturn() {
		NewsClient newsClient = new NewsClient();
		RpcAssert.doReturn("ok").when(newsClient).getContent();
	}

}