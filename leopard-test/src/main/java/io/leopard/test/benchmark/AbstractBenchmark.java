package io.leopard.test.benchmark;

import io.leopard.commons.utility.Clock;
import io.leopard.commons.utility.NumberUtil;
import io.leopard.core.Invoker;
import io.leopard.util.Caller;

public abstract class AbstractBenchmark {

	/**
	 * 线程数量.
	 */
	protected int threadSize;

	/**
	 * 执行总数.
	 */
	protected int totalCount;
	protected int count;

	public AbstractBenchmark(int count) {
		this(1, count);
	}

	public AbstractBenchmark(int threadSize, int count) {
		this.threadSize = threadSize;
		this.count = count;
		this.totalCount = count * threadSize;
		this.start();
	}

	public abstract void run() throws Exception;

	protected void startSingleThread() {
		for (int i = 0; i < this.count; i++) {
			try {
				this.run();
			}
			catch (Exception e) {
				throw new RuntimeException(e.getMessage(), e);
			}
		}
	}

	protected void startMultiThread() {
		Caller caller = new Caller();
		for (int i = 0; i < this.threadSize; i++) {
			caller.add(new Invoker() {
				@Override
				public void execute() {
					startSingleThread();
				}

			});
		}
		caller.execute();
	}

	protected long start() {
		Clock clock = Clock.start();
		if (this.threadSize <= 1) {
			this.startSingleThread();
		}
		else {

			this.startMultiThread();
		}
		long time = clock.time();
		// long time = clock.avg(this.totalCount, "start");
		long avg = NumberUtil.perSecondAvg(totalCount, time);
		System.out.println("time:" + time + "ms avg:" + avg + " threadSize:" + threadSize + " totalCount:" + totalCount);
		// PerformanceUtil.printCategoryTime(threadSize);
		return time;
	}

}
