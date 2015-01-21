package io.leopard.test.kit;

import io.leopard.burrow.lang.Invoker;
import io.leopard.burrow.util.Caller;

import java.util.Map;

/**
 * Map线程安全测试.
 * 
 * @author 阿海
 * 
 */
public class MapThreadSafetyAssert {

	private Map<String, String> map;

	public MapThreadSafetyAssert(Map<String, String> map) {
		this.map = map;
	}

	public void add(final int start, final int end) {
		for (int i = start; i < end; i++) {
			map.put(String.valueOf(i), String.valueOf(i));
		}
	}

	public boolean check(final int start, final int end) {
		boolean success = true;
		for (int l = start; l < end; l++) {
			// 如果key和value不同，说明在两个线程put的过程中出现异常。
			if (!String.valueOf(l).equals(map.get(String.valueOf(l)))) {
				print(l);
				success = false;
			}
		}
		return success;
	}

	private int printCount = 0;

	protected void print(int index) {
		if (printCount < 20) {
			System.err.println("有问题的:" + index);
		}
		printCount++;
	}

	public static void check(final Map<String, String> map) {
		int threadCount = 10;
		int threadSize = 10000;

		check(map, threadCount, threadSize);
	}

	public static void check(final Map<String, String> map, int threadCount, int threadSize) {

		final MapThreadSafetyAssert mapThreadSafetyAssert = new MapThreadSafetyAssert(map);
		Caller caller = new Caller();
		for (int i = 0; i < threadCount; i++) {
			final int start = threadSize * i;
			final int end = start + threadSize;
			// System.out.println("start:" + start + " end:" + end);
			caller.add(new Invoker() {
				@Override
				public void execute() {
					mapThreadSafetyAssert.add(start, end);
				}

			});
		}
		caller.execute();
		// System.out.println("count:" + map.size());

		int totalCount = threadCount * threadSize;
		int mapSize = map.size();
		if (totalCount != mapSize) {
			throw new RuntimeException("怎么只有" + mapSize + "个元素?应该是" + totalCount + "个才对.");
		}
		// System.out.println("count:" + map.size());
		boolean success = mapThreadSafetyAssert.check(0, totalCount);
		if (success) {
			System.out.println("OK");
		}

	}

}
