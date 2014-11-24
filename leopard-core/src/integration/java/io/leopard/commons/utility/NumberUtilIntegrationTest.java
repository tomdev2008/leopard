package io.leopard.commons.utility;

import io.leopard.commons.utility.NumberUtil;

import org.junit.Test;

public class NumberUtilIntegrationTest {

	@Test
	public void perSecondAvg() {
		// 10273000
		// 43536935
		long avg = NumberUtil.perSecondAvg(10273, 43536935);
		System.out.println("avg:" + avg);
	}

	@Test
	public void format() {
		String str = NumberUtil.format(0.1, 1);
		System.out.println("str:" + str);
	}

}