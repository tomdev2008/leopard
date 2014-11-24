package io.leopard.monitor.thread;

import java.util.List;

public interface ThreadMonitorService {

	List<ThreadInfo> listAll();

	int getActiveCount();
}
