package io.leopard.data4j.redis.hash;

import io.leopard.data4j.redis.StringHashType;

import org.junit.Assert;
import org.junit.Test;

public class StringHashTypeTest {

	@Test
	public void getHashCode() {
		Assert.assertEquals(48690, new StringHashType().getHashCode("prefix:123"));
	}

}