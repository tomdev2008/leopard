package io.leopard.test.kit;

import io.leopard.util.Caller;
import io.leopard.util.Invoker;

public class MultiThreadedCaller {

	private Caller caller = new Caller();

	private int threadCount;

	public MultiThreadedCaller(int threadCount) {
		this.threadCount = threadCount;
	}

	public int add(Invoker invoker) {
		int size = 0;
		for (int i = 0; i < threadCount; i++) {
			size = caller.add(invoker);
		}
		return size;
	}

	public long execute() {
		return caller.execute();
	}
}
