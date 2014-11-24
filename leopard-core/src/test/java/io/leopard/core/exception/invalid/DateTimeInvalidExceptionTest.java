package io.leopard.core.exception.invalid;

import org.junit.Assert;
import org.junit.Test;

public class DateTimeInvalidExceptionTest {

	@Test
	public void DateTimeInvalidException() {
		Assert.assertEquals("非法时间格式[abc].", new DateTimeInvalidException("abc").getMessage());
	}

}