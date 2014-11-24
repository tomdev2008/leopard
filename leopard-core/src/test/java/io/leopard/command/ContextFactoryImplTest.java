package io.leopard.command;

import io.leopard.test4j.mock.LeopardMockRunner;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.context.support.AbstractApplicationContext;

@RunWith(LeopardMockRunner.class)
public class ContextFactoryImplTest {

	protected AbstractApplicationContext applicationContext = Mockito.mock(AbstractApplicationContext.class, Mockito.CALLS_REAL_METHODS);

	protected ContextFactoryImpl contextFactoryImpl = newInstance();

	protected ContextFactoryImpl newInstance() {
		ContextFactoryImpl contextFactoryImpl = Mockito.spy(new ContextFactoryImpl());
		Mockito.doReturn(this.applicationContext).when(contextFactoryImpl).getApplicationContext();
		return contextFactoryImpl;
	}

	@Test
	public void getBean() {
		Mockito.doReturn(null).when(this.applicationContext).getBean("beanName");
		Assert.assertNull(contextFactoryImpl.getBean("beanName"));

		Mockito.doReturn(null).when(this.applicationContext).getBean("string");
		Assert.assertNull(contextFactoryImpl.getBean(String.class));
	}

	@Test
	public void shutDown() {
		Mockito.doNothing().when(this.applicationContext).registerShutdownHook();
		this.contextFactoryImpl.shutDown();
	}

	@Test
	public void ContextFactoryImpl() {
		new ContextFactoryImpl();
	}

}