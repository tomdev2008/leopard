package io.leopard.web;

import io.leopard.commons.utility.SystemUtil;
import io.leopard.core.StartService;
import io.leopard.test4j.mock.LeopardMockRunner;
import io.leopard.web4j.parameter.PageParameter;
import io.leopard.web4j.validator.ParameterValidator;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;

@RunWith(LeopardMockRunner.class)
@PrepareForTest({ SystemUtil.class })
public class LeopardBeanPostProcessorTest {
	private LeopardBeanPostProcessor processor = new LeopardBeanPostProcessor();

	public static class UserService implements StartService {

		@Override
		public void load() {
			// TODO Auto-generated method stub

		}

	}

	@Test
	public void postProcessBeforeInitialization() {
		processor.postProcessBeforeInitialization(null, null);
	}

	@Test
	public void postProcessAfterInitialization() {
		{
			PowerMockito.when(SystemUtil.isNotWindows()).thenReturn(true);
			processor.postProcessAfterInitialization(new UserService(), "userService");
			PowerMockito.when(SystemUtil.isNotWindows()).thenReturn(false);
			processor.postProcessAfterInitialization(new UserService(), "userService");
		}

		this.processor.postProcessAfterInitialization(Mockito.mock(PageParameter.class), "xxxPageParameter");
		this.processor.postProcessAfterInitialization(Mockito.mock(ParameterValidator.class), "xxxParameterValidator");
	}
}