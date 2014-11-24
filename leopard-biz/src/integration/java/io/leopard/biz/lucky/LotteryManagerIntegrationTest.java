package io.leopard.biz.lucky;

import org.junit.Test;

public class LotteryManagerIntegrationTest {

	@Test
	public void randomWeight() {
		LuckyLottery lottery = new LuckyLottery();
		lottery.add(1);
		lottery.add(10);
		lottery.add(100);
		lottery.add(1000);
		lottery.add(10000);

		LotteryManager manager = new LotteryManager(lottery);

		for (int i = 0; i < 10; i++) {
			int weight = manager.randomWeight();
			System.out.println("i:" + i + " weight:" + weight);
		}
	}

}