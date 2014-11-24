package io.leopard.commons.utility;

import java.util.Date;

import org.junit.Test;

public class DateTimeIntegrationTest {

	@Test
	public void getDayCount() {
		Date date = new Date();
		int dayCount1 = DateTime.getDayCount(date);
		int dayCount2 = DateTime.getDayCount(DateTime.getTime(date));
		System.out.println("dayCount1:" + dayCount1);
		System.out.println("dayCount2:" + dayCount2);
	}

	@Test
	public void getLastDayOfMonth() {
		String date = DateTime.getLastDayOfMonth("2014-07-30", -1);
		System.out.println("date:" + date);
	}
}