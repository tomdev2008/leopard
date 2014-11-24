package io.leopard.biz.repeatsubmit;

import org.junit.Assert;
import org.junit.Test;

public class RepeatSubmitTest {

	@Test
	public void RepeatSubmit() {
		RepeatSubmit repeat = new RepeatSubmit();
		repeat.setMd5("md5");
		Assert.assertEquals("md5", repeat.getMd5());
	}

}