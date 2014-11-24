package io.leopard.monitor.thread;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

public class CategoryThreadComparatorTest {

	@Test
	public void compare() {
		List<ThreadInfo> threadInfoList = new ArrayList<ThreadInfo>();
		{
			ThreadInfo threadInfo = new ThreadInfo();
			threadInfo.setCategory("Dao");
			threadInfoList.add(threadInfo);
		}
		{
			ThreadInfo threadInfo = new ThreadInfo();
			threadInfo.setCategory("Service");
			threadInfoList.add(threadInfo);
		}
		{
			ThreadInfo threadInfo = new ThreadInfo();
			threadInfo.setCategory("Service");
			threadInfoList.add(threadInfo);
		}
		{
			ThreadInfo threadInfo = new ThreadInfo();
			threadInfo.setCategory("Service");
			threadInfoList.add(threadInfo);
		}
		Collections.sort(threadInfoList, new CategoryThreadComparator());

		Assert.assertEquals("Dao", threadInfoList.get(0).getCategory());
		Assert.assertEquals("Service", threadInfoList.get(1).getCategory());
	}

	@Test
	public void compare2() {
		List<ThreadInfo> threadInfoList = new ArrayList<ThreadInfo>();

		{
			ThreadInfo threadInfo = new ThreadInfo();
			threadInfo.setCategory("Service");
			threadInfo.setCurrentSize(3);
			threadInfoList.add(threadInfo);
		}
		{
			ThreadInfo threadInfo = new ThreadInfo();
			threadInfo.setCategory("Dao");
			threadInfo.setCurrentSize(1);
			threadInfoList.add(threadInfo);
		}
		{
			ThreadInfo threadInfo = new ThreadInfo();
			threadInfo.setCategory("Service");
			threadInfo.setCurrentSize(2);
			threadInfoList.add(threadInfo);
		}
		{
			ThreadInfo threadInfo = new ThreadInfo();
			threadInfo.setCategory("Service");
			threadInfo.setCurrentSize(3);
			threadInfoList.add(threadInfo);
		}
		Collections.sort(threadInfoList, new CategoryThreadComparator());

		Assert.assertEquals("Dao", threadInfoList.get(0).getCategory());
		Assert.assertEquals("Service", threadInfoList.get(1).getCategory());
		Assert.assertEquals("Service", threadInfoList.get(2).getCategory());
		Assert.assertEquals("Service", threadInfoList.get(3).getCategory());
	}
}