package io.leopard.monitor.thread;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

public class ThreadMonitorServiceImpl implements ThreadMonitorService {

	@Autowired
	private ThreadMonitorDao threadMonitorDao;

	@Override
	public List<ThreadInfo> listAll() {
		List<ThreadInfo> list = threadMonitorDao.listAll();
		for (ThreadInfo threadInfo : list) {
			String threadName = threadInfo.getThreadName();
			String category = ThreadMonitorUtil.getCategory(threadName);

			threadInfo.setCategory(category);
		}
		Collections.sort(list, new CategoryThreadComparator());
		return list;
	}

	@Override
	public int getActiveCount() {
		ThreadGroup group = threadMonitorDao.getThreadGroup();
		return group.activeCount();
	}

}
