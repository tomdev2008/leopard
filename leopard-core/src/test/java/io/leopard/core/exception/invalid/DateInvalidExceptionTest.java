package io.leopard.core.exception.invalid;

import org.junit.Assert;
import org.junit.Test;

public class DateInvalidExceptionTest {

	@Test
	public void DateInvalidException() {
		Assert.assertEquals("非法日期格式[20130101].", new DateInvalidException("20130101").getMessage());
	}

}