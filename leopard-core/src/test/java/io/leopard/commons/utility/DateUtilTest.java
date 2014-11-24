package io.leopard.commons.utility;

import java.util.Date;

import org.junit.Assert;
import org.junit.Test;

public class DateUtilTest {

	@Test
	public void DateUtil() {
		new DateUtil();
	}

	@Test
	public void date2String() {
		Assert.assertNull(DateUtil.date2String(null));
		Assert.assertEquals("2013-01-01 00:00:00", DateUtil.date2String(DateUtil.toDate("2013-01-01 00:00:00")));

		Assert.assertNull(DateUtil.date2String(null, "yyyy"));
		Assert.assertEquals("2013-01-01", DateUtil.date2String(DateUtil.toDate("2013-01-01 00:00:00"), "yyyy-MM-DD"));
	}

	@Test
	public void defaultDate() {
		Assert.assertNotNull(DateUtil.defaultDate(null));
		Assert.assertNotNull(DateUtil.defaultDate(new Date()));

		Assert.assertEquals(1, DateUtil.defaultDate(null, 1).getTime());
		Assert.assertEquals(2, DateUtil.defaultDate(new Date(2), 1).getTime());

		Assert.assertEquals(1, DateUtil.defaultDate(null, new Date(1)).getTime());
		Assert.assertEquals(2, DateUtil.defaultDate(new Date(2), new Date(1)).getTime());
	}

	@Test
	public void toDate() {
		Assert.assertNull(DateUtil.toDate((Long) null));
		Assert.assertNull(DateUtil.toDate(new Long(0)));
		Assert.assertEquals(1000L, DateUtil.toDate(new Long(1000)).getTime());

		Assert.assertNull(DateUtil.toDate((String) null));
		Assert.assertEquals(1000L, DateUtil.toDate("1970-01-01 08:00:01").getTime());
	}

	@Test
	public void getSecond() {
		Assert.assertEquals(1, DateUtil.getSecond(DateUtil.toDate("1970-01-01 08:00:01")));
	}

	@Test
	public void isToday() {
		Assert.assertFalse(DateUtil.isToday(null));
		Assert.assertFalse(DateUtil.isToday(DateUtil.toDate("1970-01-01 08:00:01")));
	}

	@Test
	public void getBeforeSecond() {
		Assert.assertEquals(1, DateUtil.getBeforeSecond(DateUtil.toDate("1970-01-01 08:00:02"), -1).getTime() / 1000L);
		Assert.assertEquals(3, DateUtil.getBeforeSecond(DateUtil.toDate("1970-01-01 08:00:02"), 1).getTime() / 1000L);
	}

	@Test
	public void before() {
		Assert.assertFalse(DateUtil.before(null, null));
		Assert.assertFalse(DateUtil.before(null, new Date()));
		Assert.assertTrue(DateUtil.before(new Date(), null));
		Assert.assertTrue(DateUtil.before(new Date(1), new Date(2)));
		Assert.assertFalse(DateUtil.before(new Date(3), new Date(2)));
	}

	@Test
	public void isEqualsDay() {
		Assert.assertTrue(DateUtil.isEqualsDay(1, 2));
	}

	@Test
	public void getHour() {
		Assert.assertEquals(8, DateUtil.getHour(1));
	}

	@Test
	public void getDays() {
		Assert.assertEquals(-1, DateUtil.getDays("2013-01-01", "2013-01-02"));
	}

	@Test
	public void getTimestamp() {
		Assert.assertEquals(1, DateUtil.getTimestamp(new Date(1)));
	}

	@Test
	public void getTime() {
		Assert.assertNotNull(DateUtil.getTime());
		Assert.assertNull(DateUtil.getTime(null));
		Assert.assertEquals("2013-01-01", DateUtil.getTime(DateUtil.toDate("2013-01-01 08:00:02"), "yyyy-MM-DD"));
	}

	@Test
	public void str2Date() {
		Assert.assertEquals(2000L, DateUtil.str2Date("1970-01-01 08:00:02").getTime());
		Assert.assertEquals(2000L, DateUtil.str2Date("1970-01-01 08:00:02", "yyyy-MM-dd HH:mm:ss").getTime());
		Assert.assertEquals(1, DateUtil.str2Date("1970-01-01", "yyyy-MM-dd HH:mm:ss", new Date(1)).getTime());
		Assert.assertEquals(2000L, DateUtil.str2Date("1970-01-01 08:00:02", "yyyy-MM-dd HH:mm:ss", new Date(1)).getTime());
		Assert.assertEquals(1, DateUtil.str2Date("1970-01-01", new Date(1)).getTime());

		Assert.assertEquals(2000L, DateUtil.str2Date("1970-01-01 08:00:02", new Date(1)).getTime());

		try {
			DateUtil.str2Date("1970-01-01");
			Assert.fail("怎么没有抛异常?");
		}
		catch (RuntimeException e) {

		}

		try {
			DateUtil.str2Date("1970-01-01", "yyyy-MM-dd HH:mm:ss");
			Assert.fail("怎么没有抛异常?");
		}
		catch (RuntimeException e) {

		}
	}

	@Test
	public void addTime() {
		DateUtil.addTime(1);
		DateUtil.addTime(new Date(1), 1);

		Assert.assertEquals("1970-01-01 08:01:02", DateUtil.getTime(DateUtil.addTime(DateUtil.toDate("1970-01-01 08:00:02"), 1)));

	}

	@Test
	public void getOnlyDate() {
		Assert.assertEquals("1970-01-01 00:00:00", DateUtil.getTime(DateUtil.getOnlyDate(DateUtil.toDate("1970-01-01 08:00:02"))));
	}

	@Test
	public void addDate() {
		Assert.assertEquals("1970-01-02 08:00:02", DateUtil.getTime(DateUtil.addDate(DateUtil.toDate("1970-01-01 08:00:02"), 1)));
	}

	@Test
	public void getDate() {
		Assert.assertEquals("1970-01-01", DateUtil.getDate(DateUtil.toDate("1970-01-01 08:00:02")));
		Assert.assertNull(DateUtil.getDate(null));
	}

	@Test
	public void toLongDate() {
		Assert.assertNull(DateUtil.toLongDate(0));
		Assert.assertEquals("2010-01-01 00:00:01", DateUtil.getTime(DateUtil.toLongDate(1)));

		Assert.assertNull(DateUtil.toLongDate((Double) null));
		Assert.assertEquals("2010-01-01 00:00:01", DateUtil.getTime(DateUtil.toLongDate(new Double(1))));
	}

	@Test
	public void getShortSeconds() {
		// 2010-01-01 00:00:00
		Assert.assertEquals(1, DateUtil.getShortSeconds(DateUtil.toDate("2010-01-01 00:00:01")));
		Assert.assertTrue(DateUtil.getShortSeconds() > 0);
	}

	@Test
	public void getSeconds() {
		Assert.assertTrue(DateUtil.getSeconds() > 0);
	}
}