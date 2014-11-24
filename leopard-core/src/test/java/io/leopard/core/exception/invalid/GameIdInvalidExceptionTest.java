package io.leopard.core.exception.invalid;

import org.junit.Assert;
import org.junit.Test;

public class GameIdInvalidExceptionTest {

	@Test
	public void GameIdInvalidException() {
		Assert.assertEquals("非法游戏ID[DDT].", new GameIdInvalidException("DDT").getMessage());
	}

}