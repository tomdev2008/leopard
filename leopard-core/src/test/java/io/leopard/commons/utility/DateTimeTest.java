package io.leopard.commons.utility;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ DateTime.class })
public class DateTimeTest {

	@Test
	public void getDayCount() {
		PowerMockito.mockStatic(System.class);
		PowerMockito.when(System.currentTimeMillis()).thenReturn(1000L);
		Assert.assertEquals(0, DateTime.getDayCount());

		PowerMockito.when(System.currentTimeMillis()).thenReturn(DateTime.DAY_MILLIS);
		Assert.assertEquals(1, DateTime.getDayCount());
	}

	@Test
	public void getDayCount2() {
		Assert.assertEquals(0, DateTime.getDayCount("1970-01-01 08:00:00"));
		Assert.assertEquals(0, DateTime.getDayCount("1970-01-01 23:59:59"));
		Assert.assertEquals(1, DateTime.getDayCount("1970-01-02 00:00:00"));
		Assert.assertEquals(1, DateTime.getDayCount("1970-01-02 00:00:01"));
	}

	@Test
	public void getDayCount3() {
		Assert.assertEquals(-1, DateTime.getDayCount("1970-01-01 08:00:00", "1970-01-02 00:00:00"));
		Assert.assertEquals(0, DateTime.getDayCount("1970-01-02 08:00:00", "1970-01-02 00:00:00"));
	}

	@Test
	public void getHourCount() {
		PowerMockito.mockStatic(System.class);
		PowerMockito.when(System.currentTimeMillis()).thenReturn(1000L);
		Assert.assertEquals(0, DateTime.getHourCount());

		PowerMockito.when(System.currentTimeMillis()).thenReturn(DateTime.DAY_MILLIS);
		Assert.assertEquals(24, DateTime.getHourCount());
	}

	@Test
	public void getDate() {
		PowerMockito.mockStatic(System.class);

		PowerMockito.when(System.currentTimeMillis()).thenReturn(1000L);
		Assert.assertEquals("1970-01-01", DateTime.getDate());

		PowerMockito.when(System.currentTimeMillis()).thenReturn(DateTime.HOUR_MILLIS * 16 - 1);
		Assert.assertEquals("1970-01-01", DateTime.getDate());

		PowerMockito.when(System.currentTimeMillis()).thenReturn(DateTime.HOUR_MILLIS * 16);
		Assert.assertEquals("1970-01-02", DateTime.getDate());
	}

	@Test
	public void addDate() {

	}

	@Test
	public void parseDatetimeToArray() {

	}

	@Test
	public void getHour() {

	}

	@Test
	public void getDay() {

	}

	@Test
	public void getMonth() {

	}

	@Test
	public void getMinute() {

	}

	@Test
	public void getTime() {

	}

	@Test
	public void addTime() {

	}

	@Test
	public void getTimestamp() {

	}

	@Test
	public void getUnixTimestamp() {

	}

	@Test
	public void isDate() {

	}

	@Test
	public void isTime() {

	}

	@Test
	public void isDateTime() {

	}

	@Test
	public void getSecond() {

	}

	@Test
	public void getGMT() {

	}

	@Test
	public void getWeekName() {

	}

	@Test
	public void getDayCountOfMonth() {

	}

	@Test
	public void getFirstDayOfMonth() {

	}

	@Test
	public void getMonday() {

	}

	@Test
	public void isToday() {

	}

	@Test
	public void getIntTime() {

	}

	@Test
	public void DateTime() {
		new DateTime();
	}

}