package io.leopard.core.exception.notfound;

import org.junit.Assert;
import org.junit.Test;

public class YyuidNotFoundExceptionTest {

	@Test
	public void YyuidNotFoundException() {
		Assert.assertEquals("YY用户不存在[1].", new YyuidNotFoundException(1).getMessage());
	}

}