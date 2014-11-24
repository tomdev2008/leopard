package io.leopard.biz.lucky;

import io.leopard.commons.utility.Clock;

import org.junit.Test;

public class LuckyLotteryIntegrationTest {

	@Test
	public void randomWeight() {
		LuckyLottery lottery = new LuckyLottery();
		lottery.add(1);
		lottery.add(10);
		lottery.add(100);
		lottery.add(1000);
		lottery.add(10000);

		Clock clock = Clock.start();
		for (int i = 4; i >= 0; i--) {
			this.random(lottery, i);
		}
		clock.time("random");
	}

	protected void random(LuckyLottery lottery, int index) {
		int[] counts = new int[5];
		for (int i = 0; i < 1000000; i++) {
			int weight = lottery.randomWeight(index);
			counts[weight]++;
			// System.out.println("###############################");
		}
		for (int i = 0; i < counts.length; i++) {
			System.out.println("i:" + i + " count:" + counts[i]);
		}
		System.out.println("#########################################");
	}

	@Test
	public void getTotalWeight() {
		LuckyLottery lottery = new LuckyLottery();
		lottery.add(1);
		lottery.add(10);
		lottery.add(100);
		lottery.add(1000);
		lottery.add(10000);

		System.out.println("total:" + lottery.getTotalWeight(0));
	}

}