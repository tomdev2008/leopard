package io.leopard.commons.utility;

import io.leopard.commons.utility.DateUtil;

import java.util.Date;

import org.junit.Test;

public class DateUtilIntegrationTest {

	@Test
	public void getSecond() {
		Date date = DateUtil.toDate("2013-01-01 10:10:10");
		int seconds = DateUtil.getSecond(date);
		System.out.println("seconds:" + seconds);
	}

}