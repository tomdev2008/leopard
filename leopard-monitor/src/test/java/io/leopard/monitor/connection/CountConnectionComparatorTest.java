package io.leopard.monitor.connection;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

public class CountConnectionComparatorTest {

	@Test
	public void compare() {
		List<ConnectionInfo> connectionInfoList = new ArrayList<ConnectionInfo>();
		{
			ConnectionInfo connectionInfo = new ConnectionInfo();
			connectionInfo.setConnectionCount(1);
			connectionInfo.setContent("name1");
			connectionInfoList.add(connectionInfo);
		}
		{
			ConnectionInfo connectionInfo = new ConnectionInfo();
			connectionInfo.setConnectionCount(1);
			connectionInfo.setContent("name2");
			connectionInfoList.add(connectionInfo);
		}
		{
			ConnectionInfo connectionInfo = new ConnectionInfo();
			connectionInfo.setConnectionCount(3);
			connectionInfo.setContent("name3");
			connectionInfoList.add(connectionInfo);
		}
		{
			ConnectionInfo connectionInfo = new ConnectionInfo();
			connectionInfo.setConnectionCount(2);
			connectionInfo.setContent("name4");
			connectionInfoList.add(connectionInfo);
		}
		Collections.sort(connectionInfoList, new CountConnectionComparator());

		Assert.assertEquals(3L, connectionInfoList.get(0).getConnectionCount());
		Assert.assertEquals(2L, connectionInfoList.get(1).getConnectionCount());
	}

}