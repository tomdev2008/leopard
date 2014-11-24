package io.leopard.commons.utility;

import org.junit.Test;

public class SpeedTest {

	@Test
	public void Speed() {
		new Speed(10) {
			@Override
			public void run() {
				try {
					Thread.sleep(2);
				}
				catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		};
	}

}