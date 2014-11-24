package io.leopard.test.mock;

import io.leopard.data.rpc.RpcClient;

import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;

public class RpcMock {
	@SuppressWarnings("unchecked")
	public static void doReturn(Object toBeReturned) {
		Mockito.when(RpcClient.doGet(Mockito.anyString(), Mockito.anyInt())).thenReturn(toBeReturned);
		Mockito.when(RpcClient.doPost(Mockito.anyString(), Mockito.anyInt())).thenReturn(toBeReturned);
		Mockito.when(RpcClient.doPost(Mockito.anyString(), Mockito.anyMap(), Mockito.anyInt())).thenReturn(toBeReturned);
	}

	@SuppressWarnings("unchecked")
	public static void verify(int times) {
		PowerMockito.verifyStatic(Mockito.times(times));
		RpcClient.doPost(Mockito.anyString(), Mockito.anyMap(), Mockito.anyInt());
	}
}
