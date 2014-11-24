package io.leopard.test.benchmark;

import io.leopard.command.ContextFactory;
import io.leopard.data.env.AppProperties;
import io.leopard.test.hosts.DnsConfig;
import io.leopard.test.mock.Mock;
import io.leopard.test4j.mock.LeopardMockRunner;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.core.classloader.annotations.PrepareForTest;

@RunWith(LeopardMockRunner.class)
@PrepareForTest({ ContextFactory.class, DnsConfig.class, AppProperties.class })
public class AbstractBenchmarkTest {

	@Test
	public void start() {
		AbstractBenchmark abstractBenchmark = Mockito.mock(AbstractBenchmark.class, Mockito.CALLS_REAL_METHODS);
		abstractBenchmark.threadSize = 1;
		abstractBenchmark.start();
		Mock.verify(abstractBenchmark, 1).startSingleThread();
		abstractBenchmark.threadSize = 2;
		abstractBenchmark.start();
		Mock.verify(abstractBenchmark, 1).startMultiThread();
	}

	@Test
	public void startSingleThread() throws Exception {
		AbstractBenchmark abstractBenchmark = Mockito.mock(AbstractBenchmark.class, Mockito.CALLS_REAL_METHODS);
		abstractBenchmark.count = 2;
		Mockito.doNothing().when(abstractBenchmark).run();
		abstractBenchmark.startSingleThread();
		Mock.verify(abstractBenchmark, 2).run();
		Mockito.doThrow(new IllegalArgumentException("msg")).when(abstractBenchmark).run();

		try {
			abstractBenchmark.startSingleThread();
		}
		catch (RuntimeException e) {

		}
	}

	@Test
	public void AbstractBenchmark() {
		AbstractBenchmark abstractBenchmark = new AbstractBenchmark(1) {
			@Override
			public void run() throws Exception {

			}
		};

		Assert.assertEquals(1, abstractBenchmark.count);

	}

}