package io.leopard.data.pub;

import io.leopard.data4j.memcache.Memcache;
import io.leopard.data4j.memcache.MemcacheRedisImpl;
import io.leopard.data4j.redis.Redis;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;

public class PubSubBeanPostProcessorTest {

	PubSubBeanPostProcessor processor = new PubSubBeanPostProcessor();

	@Test
	public void postProcessAfterInitialization() {
		processor.postProcessAfterInitialization(null, "beanName");
	}

	@Test
	public void postProcessBeforeInitialization() {
		ConfigurableListableBeanFactory beanFactory = Mockito.mock(ConfigurableListableBeanFactory.class);

		PubSubBeanPostProcessor processor = new PubSubBeanPostProcessor();
		processor.setBeanFactory(beanFactory);
		processor.postProcessBeforeInitialization(null, "beanName");

		{
			Redis redis = Mockito.mock(Redis.class);
			Mockito.doReturn(redis).when(beanFactory).getBean("sessionRedis");
		}

		PubSubTestImpl pubSubTestImpl = new PubSubTestImpl();
		processor.postProcessBeforeInitialization(pubSubTestImpl, "pubSubTestImpl");

	}

	public static class PubSubTestImpl implements IPubSub {

		// @Override
		// public boolean publish(String message) {
		// return false;
		// }

		@Override
		public void subscribe(String message, boolean isMySelf) {

		}

	}

	@Test
	public void findRedisBySessionRedis() {
		ConfigurableListableBeanFactory beanFactory = Mockito.mock(ConfigurableListableBeanFactory.class);
		processor.setBeanFactory(beanFactory);
		Assert.assertNull(this.processor.findRedisBySessionRedis());

		{
			Mockito.when(beanFactory.getBean("sessionRedis")).thenThrow(new NoSuchBeanDefinitionException("memcache"));
			Assert.assertNull(processor.findRedisBySessionRedis());
		}
	}

	@Test
	public void findRedis() {
		ConfigurableListableBeanFactory beanFactory = Mockito.mock(ConfigurableListableBeanFactory.class);
		processor.setBeanFactory(beanFactory);
		this.processor.findRedis();

		{
			Redis redis = Mockito.mock(Redis.class);
			MemcacheRedisImpl memcache = Mockito.mock(MemcacheRedisImpl.class);
			Mockito.doReturn(redis).when(memcache).getRedis();
			Mockito.doReturn(memcache).when(beanFactory).getBean("memcache");
			Assert.assertNotNull(processor.findRedis());
		}

		Redis redis = Mockito.mock(Redis.class);
		Mockito.doReturn(redis).when(beanFactory).getBean("sessionRedis");
		this.processor.getRedis();
	}

	@Test
	public void PubSubBeanPostProcessor() {

	}

	@Test
	public void findRedisByMemcache() {
		ConfigurableListableBeanFactory beanFactory = Mockito.mock(ConfigurableListableBeanFactory.class);
		processor.setBeanFactory(beanFactory);
		Assert.assertNull(processor.findRedisByMemcache());

		{
			Mockito.when(beanFactory.getBean("memcache")).thenThrow(new NoSuchBeanDefinitionException("memcache"));
			Assert.assertNull(processor.findRedisByMemcache());
		}
		{
			Memcache memcache = Mockito.mock(Memcache.class);
			Mockito.doReturn(memcache).when(beanFactory).getBean("memcache");
			Assert.assertNull(processor.findRedisByMemcache());
		}
		{
			MemcacheRedisImpl memcache = Mockito.mock(MemcacheRedisImpl.class);
			Mockito.doReturn(memcache).when(beanFactory).getBean("memcache");
			Assert.assertNull(processor.findRedisByMemcache());
		}
		{
			Redis redis = Mockito.mock(Redis.class);
			MemcacheRedisImpl memcache = Mockito.mock(MemcacheRedisImpl.class);
			Mockito.doReturn(redis).when(memcache).getRedis();
			Mockito.doReturn(memcache).when(beanFactory).getBean("memcache");
			Assert.assertNotNull(processor.findRedisByMemcache());
		}
	}

}