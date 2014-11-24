package io.leopard.monitor.connection;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

public class AvgTimeConnectionComparatorTest {

	@Test
	public void compare() {
		List<ConnectionInfo> connectionInfoList = new ArrayList<ConnectionInfo>();
		{
			ConnectionInfo connectionInfo = new ConnectionInfo();
			connectionInfo.setAvgTime(1);
			connectionInfo.setContent("name1");
			connectionInfoList.add(connectionInfo);
		}
		{
			ConnectionInfo connectionInfo = new ConnectionInfo();
			connectionInfo.setAvgTime(1);
			connectionInfo.setContent("name2");
			connectionInfoList.add(connectionInfo);
		}
		{
			ConnectionInfo connectionInfo = new ConnectionInfo();
			connectionInfo.setAvgTime(3);
			connectionInfo.setContent("name3");
			connectionInfoList.add(connectionInfo);
		}
		{
			ConnectionInfo connectionInfo = new ConnectionInfo();
			connectionInfo.setAvgTime(2);
			connectionInfo.setContent("name4");
			connectionInfoList.add(connectionInfo);
		}
		Collections.sort(connectionInfoList, new AvgTimeConnectionComparator());

		Assert.assertEquals(3D, connectionInfoList.get(0).getAvgTime(), 0);
		Assert.assertEquals(2D, connectionInfoList.get(1).getAvgTime(), 0);
	}

}