package io.leopard.web4j.trynb;

import org.junit.Assert;
import org.junit.Test;

public class ErrorPageUtilTest {

	@Test
	public void ErrorPageUtil() {
		new ErrorPageUtil();
	}

	@Test
	public void match() {
		Assert.assertTrue(ErrorPageUtil.match("UsernameInvalidException", "com.duowan.core.UsernameInvalidException"));
		Assert.assertTrue(ErrorPageUtil.match("com.InvalidException", "com.InvalidException"));
		Assert.assertFalse(ErrorPageUtil.match("com.InvalidException", "com.InvalidException2"));
		Assert.assertFalse(ErrorPageUtil.match("InvalidException", "com.InvalidException2"));
	}

}