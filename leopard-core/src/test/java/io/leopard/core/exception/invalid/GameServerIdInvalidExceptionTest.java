package io.leopard.core.exception.invalid;

import org.junit.Assert;
import org.junit.Test;

public class GameServerIdInvalidExceptionTest {

	@Test
	public void GameServerIdInvalidException() {
		Assert.assertEquals("非法游戏服务器[DDT.S1].", new GameServerIdInvalidException("DDT", "S1").getMessage());
	}

}