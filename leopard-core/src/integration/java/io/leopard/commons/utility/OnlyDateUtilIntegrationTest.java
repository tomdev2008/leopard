package io.leopard.commons.utility;

import io.leopard.burrow.lang.datatype.OnlyDate;

import org.junit.Test;

public class OnlyDateUtilIntegrationTest {

	@Test
	public void getFirstDayOfMonth() {
		OnlyDate date = OnlyDateUtil.getFirstDayOfMonth(new OnlyDate(), 0);
		System.out.println("firstDay:" + date.toString());
	}

	@Test
	public void getLastDayOfMonth() {
		OnlyDate date = OnlyDateUtil.getLastDayOfMonth(new OnlyDate(), 0);
		System.out.println("lastDay:" + date.toString());
	}

	@Test
	public void getFirstDayOfWeek() {

	}

}