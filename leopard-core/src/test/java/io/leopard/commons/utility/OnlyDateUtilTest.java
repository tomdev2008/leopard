package io.leopard.commons.utility;

import io.leopard.burrow.lang.datatype.OnlyDate;

import org.junit.Assert;
import org.junit.Test;

public class OnlyDateUtilTest {

	@Test
	public void getFirstDayOfWeek() {
		Assert.assertEquals("2013-12-30", OnlyDateUtil.getFirstDayOfWeek(new OnlyDate("2014-01-05"), 0).toString());
		Assert.assertEquals("2014-06-23", OnlyDateUtil.getFirstDayOfWeek(new OnlyDate("2014-06-29"), 0).toString());
		Assert.assertEquals("2014-07-14", OnlyDateUtil.getFirstDayOfWeek(new OnlyDate("2014-07-19"), 0).toString());
		Assert.assertEquals("2014-07-21", OnlyDateUtil.getFirstDayOfWeek(new OnlyDate("2014-07-27"), 0).toString());
		Assert.assertEquals("2014-07-28", OnlyDateUtil.getFirstDayOfWeek(new OnlyDate("2014-07-28"), 0).toString());
		Assert.assertEquals("2014-07-28", OnlyDateUtil.getFirstDayOfWeek(new OnlyDate("2014-07-29"), 0).toString());
		Assert.assertEquals("2014-07-28", OnlyDateUtil.getFirstDayOfWeek(new OnlyDate("2014-07-30"), 0).toString());
		Assert.assertEquals("2014-07-28", OnlyDateUtil.getFirstDayOfWeek(new OnlyDate("2014-07-31"), 0).toString());
		Assert.assertEquals("2014-07-28", OnlyDateUtil.getFirstDayOfWeek(new OnlyDate("2014-08-01"), 0).toString());
		Assert.assertEquals("2014-07-28", OnlyDateUtil.getFirstDayOfWeek(new OnlyDate("2014-08-02"), 0).toString());
	}

}