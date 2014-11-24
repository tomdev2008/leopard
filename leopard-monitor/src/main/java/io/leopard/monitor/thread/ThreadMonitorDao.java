package io.leopard.monitor.thread;

import java.util.List;

public interface ThreadMonitorDao {

	Thread[] listAllThreads();

	List<ThreadInfo> listAll();

	ThreadGroup getThreadGroup();

}
