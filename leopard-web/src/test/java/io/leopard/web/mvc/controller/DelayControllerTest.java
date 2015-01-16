package io.leopard.web.mvc.controller;

import io.leopard.data4j.env.EnvUtil;
import io.leopard.test4j.mock.LeopardMockRunner;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;

@RunWith(LeopardMockRunner.class)
@PrepareForTest({ EnvUtil.class })
public class DelayControllerTest {

	@Test
	public void checkEnv() {
		DelayController controller = new DelayController();
		PowerMockito.when(EnvUtil.isDevEnv()).thenReturn(true);
		controller.checkEnv();
		PowerMockito.when(EnvUtil.isDevEnv()).thenReturn(false);
		try {
			controller.checkEnv();
			Assert.fail("怎么没有抛异常?");
		}
		catch (RuntimeException e) {

		}
	}

	// @Test
	// public void delay() {
	// DelayController controller = Mockito.spy(new DelayController());
	// PowerMockito.when(EnvUtil.isDevEnv()).thenReturn(true);
	// PowerMockito.when(PageDelayService.isDelayOn()).thenReturn(true);
	// controller.delay();
	//
	// PowerMockito.when(PageDelayService.isDelayOn()).thenReturn(false);
	// controller.delay();
	// }

	@Test
	public void config() {
		DelayController controller = Mockito.spy(new DelayController());
		PowerMockito.when(EnvUtil.isDevEnv()).thenReturn(true);

		controller.config(true);
	}
}