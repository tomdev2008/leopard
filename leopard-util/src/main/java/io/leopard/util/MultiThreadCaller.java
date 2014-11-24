package io.leopard.util;

import io.leopard.commons.utility.NumberUtil;
import io.leopard.core.Invoker;

public class MultiThreadCaller extends Caller {

	private int threadCount;
	private int threadSize;//

	public MultiThreadCaller(int threadCount) {
		this(threadCount, 1);
	}

	public MultiThreadCaller(int threadCount, int threadSize) {
		this.threadCount = threadCount;
		this.threadSize = threadSize;
	}

	public Invoker getBatchInvoker(final Invoker invoker) {
		if (threadSize <= 1) {
			return invoker;
		}
		return new Invoker() {
			@Override
			public void execute() {
				for (int i = 0; i < threadSize; i++) {
					invoker.execute();
				}
			}
		};
	}

	public long execute(Invoker invoker) {
		for (int i = 0; i < threadCount; i++) {
			super.add(getBatchInvoker(invoker));
		}
		long time = super.execute();
		long totalCount = this.threadCount * this.threadSize;
		long avg = NumberUtil.perSecondAvg(totalCount, time);
		System.out.println("totalCount:" + totalCount + " time:" + time + " avg:" + avg);
		return time;
	}

}
