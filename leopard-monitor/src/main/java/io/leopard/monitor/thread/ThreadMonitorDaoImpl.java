package io.leopard.monitor.thread;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class ThreadMonitorDaoImpl implements ThreadMonitorDao {

	@Override
	public List<ThreadInfo> listAll() {
		Thread[] threads = this.listAllThreads();
		Map<String, Integer> map = new HashMap<String, Integer>();
		for (Thread thread : threads) {
			StackTraceElement[] stacks = thread.getStackTrace();
			for (StackTraceElement stack : stacks) {
				String threadName = stack.toString();
				if (ThreadMonitorUtil.isIgnoreThreadName(threadName)) {
					continue;
				}
				Integer count = map.get(threadName);
				if (count == null) {
					count = 0;
				}
				count++;
				map.put(threadName, count);
			}
		}

		Iterator<Entry<String, Integer>> iterator = map.entrySet().iterator();
		List<ThreadInfo> list = new ArrayList<ThreadInfo>();
		while (iterator.hasNext()) {
			Entry<String, Integer> entry = iterator.next();
			String threadName = entry.getKey();
			int currentSize = entry.getValue();
			ThreadInfo threadInfo = new ThreadInfo();
			threadInfo.setThreadName(threadName);
			threadInfo.setCurrentSize(currentSize);
			list.add(threadInfo);
		}
		return list;
	}

	@Override
	public ThreadGroup getThreadGroup() {
		ThreadGroup group = Thread.currentThread().getThreadGroup();
		ThreadGroup parent = null;
		while ((parent = group.getParent()) != null) {
			group = parent;
		}
		return group;
	}

	@Override
	public Thread[] listAllThreads() {
		ThreadGroup group = this.getThreadGroup();
		Thread[] threads = new Thread[group.activeCount()];
		group.enumerate(threads);
		return threads;
	}

	// protected void printThread(Thread thread) {
	// StackTraceElement[] stacks = thread.getStackTrace();
	// for (StackTraceElement stack : stacks) {
	// System.out.println(stack.toString());
	// }
	//
	// System.out.println("#######################################################");
	// }
	//
	// Redis redis;
	//
	// protected Redis getRedis() {
	// if (redis == null) {
	// redis = RedisFactory.create("113.108.228.100:6311");
	// }
	// return this.redis;
	// }
	//
	// //
	// public void test() {
	// for (int i = 0; i < 100; i++) {
	// final int index = i;
	// new Thread() {
	// @Override
	// public void run() {
	// try {
	// Jedis jedis = getRedis().getJedisPool().getResource();
	// String value = jedis.get("key");
	// System.err.println("index:" + index + " value:" + value);
	// }
	// catch (Exception e) {
	// e.printStackTrace();
	// }
	// }
	// }.start();
	// }
	// }
}
