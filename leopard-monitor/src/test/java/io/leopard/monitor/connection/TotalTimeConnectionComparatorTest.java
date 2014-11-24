package io.leopard.monitor.connection;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

public class TotalTimeConnectionComparatorTest {

	@Test
	public void compare() {
		List<ConnectionInfo> connectionInfoList = new ArrayList<ConnectionInfo>();
		{
			ConnectionInfo connectionInfo = new ConnectionInfo();
			connectionInfo.setTotalTime(1);
			connectionInfo.setContent("name1");
			connectionInfoList.add(connectionInfo);
		}
		{
			ConnectionInfo connectionInfo = new ConnectionInfo();
			connectionInfo.setTotalTime(1);
			connectionInfo.setContent("name2");
			connectionInfoList.add(connectionInfo);
		}
		{
			ConnectionInfo connectionInfo = new ConnectionInfo();
			connectionInfo.setTotalTime(3);
			connectionInfo.setContent("name3");
			connectionInfoList.add(connectionInfo);
		}
		{
			ConnectionInfo connectionInfo = new ConnectionInfo();
			connectionInfo.setTotalTime(2);
			connectionInfo.setContent("name4");
			connectionInfoList.add(connectionInfo);
		}
		Collections.sort(connectionInfoList, new TotalTimeConnectionComparator());

		Assert.assertEquals(3L, connectionInfoList.get(0).getTotalTime());
		Assert.assertEquals(2L, connectionInfoList.get(1).getTotalTime());
	}

}