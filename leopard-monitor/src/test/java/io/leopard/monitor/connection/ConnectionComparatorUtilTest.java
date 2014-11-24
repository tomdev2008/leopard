package io.leopard.monitor.connection;

import org.junit.Assert;
import org.junit.Test;

public class ConnectionComparatorUtilTest {

	@Test
	public void getComparator() {
		Assert.assertTrue(ConnectionComparatorUtil.getComparator("connectionCount") instanceof CountConnectionComparator);
		Assert.assertTrue(ConnectionComparatorUtil.getComparator("avgTime") instanceof AvgTimeConnectionComparator);
		Assert.assertTrue(ConnectionComparatorUtil.getComparator("") instanceof TotalTimeConnectionComparator);
	}

	@Test
	public void ConnectionComparatorUtil() {
		new ConnectionComparatorUtil();
	}

}