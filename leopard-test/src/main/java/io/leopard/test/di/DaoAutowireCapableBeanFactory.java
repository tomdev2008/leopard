package io.leopard.test.di;

import io.leopard.burrow.lang.AssertUtil;
import io.leopard.burrow.refect.FieldUtil;
import io.leopard.commons.utility.ClassUtil;
import io.leopard.commons.utility.StringUtil;
import io.leopard.data4j.jdbc.Jdbc;
import io.leopard.data4j.redis.Redis;
import io.leopard.util.CustomBeanUtil;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.Resource;

import org.apache.commons.lang.NotImplementedException;
import org.springframework.beans.BeansException;
import org.springframework.beans.TypeConverter;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.beans.factory.config.DependencyDescriptor;
import org.springframework.util.StringUtils;

public class DaoAutowireCapableBeanFactory implements AutowireCapableBeanFactory {

	private Map<String, Object> data = new ConcurrentHashMap<String, Object>();

	@Override
	public Object getBean(String name) throws BeansException {
		AssertUtil.assertNotEmpty(name, "参数name不能为空.");
		return data.get(name);
	}

	@Override
	public <T> T getBean(String name, Class<T> requiredType) throws BeansException {
		throw new NotImplementedException();
	}

	@Override
	public <T> T createBean(Class<T> beanClass) throws BeansException {
		throw new NotImplementedException();
	}

	private static Map<String, IBeanInstance> beanInstanceMap = new HashMap<String, IBeanInstance>();
	static {
		beanInstanceMap.put(Redis.class.getName(), new RedisBeanInstance());
		beanInstanceMap.put(Jdbc.class.getName(), new JdbcBeanInstance());
	}

	@SuppressWarnings("unchecked")
	protected <T> T createBean(String beanName, Class<T> beanClass) throws BeansException {
		// System.err.println("createBean beanName:" + beanName + " beanClass:" + beanClass);
		T bean;
		if (beanClass.isInterface()) {
			IBeanInstance beanInstance = beanInstanceMap.get(beanClass.getName());
			if (beanInstance == null) {
				if (CustomBeanUtil.isCustomBean(beanClass)) {
					bean = newInstanceCustomBean(beanName, beanClass);
				}
				else {
					throw new NotImplementedException("未知class[" + beanClass.getName() + "]");
				}
			}
			else {
				bean = (T) beanInstance.instance(beanName);
			}
		}
		else {
			bean = ClassUtil.newInstance(beanClass);
		}
		this.autowireBean(bean);
		return bean;
	}

	protected <T> T newInstanceCustomBean(String beanName, Class<T> beanClass) {
		if (beanName.endsWith("Dao")) {
			T bean = this.newInstanceDao(beanName, beanClass);
			// System.out.println("beanName:" + beanName + " bean:" + bean);
			return bean;
		}

		String packageName = beanClass.getPackage().getName();
		String className;
		if (beanName.endsWith("MysqlImpl")) {
			className = packageName + ".mysql." + StringUtil.firstCharToUpperCase(beanName);
		}
		else if (beanName.endsWith("RedisImpl")) {
			className = packageName + ".redis." + StringUtil.firstCharToUpperCase(beanName);
		}
		else if (beanName.endsWith("Service")) {
			className = packageName + ".impl." + StringUtil.firstCharToUpperCase(beanName) + "Impl";
		}
		else {
			throw new RuntimeException("未知Bean类型[" + beanName + "]");
		}
		// System.out.println("beanName:" + beanName + " beanClass:" + beanClass);
		@SuppressWarnings("unchecked")
		Class<T> clazz = (Class<T>) ClassUtil.forName(className);
		return ClassUtil.newInstance(clazz);
	}

	private static List<String> daoPackageList = new ArrayList<String>();
	static {
		daoPackageList.add("cache");
		daoPackageList.add("mysql");
		daoPackageList.add("redis");
	}

	@SuppressWarnings("unchecked")
	protected <T> T newInstanceDao(String beanName, Class<T> beanClass) {
		String packageName = beanClass.getPackage().getName();

		for (String name : daoPackageList) {
			String className = packageName + "." + name + "." + StringUtil.firstCharToUpperCase(beanName) + StringUtil.firstCharToUpperCase(name) + "Impl";
			if (ClassUtil.exist(className)) {
				Class<T> implClass = (Class<T>) ClassUtil.forName(className);
				return ClassUtil.newInstance(implClass);
			}
		}
		throw new RuntimeException("找不到DAO[" + beanName + "]的实现类.");
	}

	@Override
	public void autowireBean(Object existingBean) throws BeansException {
		Field[] fields = existingBean.getClass().getDeclaredFields();
		if (fields != null) {
			for (Field field : fields) {
				String beanName = this.getBeanName(field);
				// System.out.println("field beanName:" + beanName + " " + field.toGenericString());
				if (beanName == null) {
					continue;
				}

				Object bean = this.getBean(beanName);
				if (bean == null) {
					bean = this.createBean(beanName, field.getType());
					if (bean != null) {
						this.data.put(beanName, bean);
					}
				}
				FieldUtil.setFieldValue(existingBean, field, bean);
				// System.out.println("beanName:" + beanName);
			}
		}
	}

	protected String getBeanName(Field field) {
		String beanName;
		Autowired autowired = field.getAnnotation(Autowired.class);
		if (autowired != null) {
			return field.getName();
		}
		Resource resource = field.getAnnotation(Resource.class);
		// System.out.println("resource:" + resource);
		if (resource != null) {
			beanName = resource.name();
			if (StringUtils.isEmpty(beanName)) {
				beanName = field.getName();
			}
			return beanName;
		}
		return null;
	}

	@Override
	public void autowireBeanProperties(Object existingBean, int autowireMode, boolean dependencyCheck) throws BeansException {
		this.autowireBean(existingBean);
	}

	@Override
	public Object initializeBean(Object existingBean, String beanName) throws BeansException {
		return existingBean;
	}

	@Override
	public <T> T getBean(Class<T> requiredType) throws BeansException {
		throw new NotImplementedException();
	}

	@Override
	public Object getBean(String name, Object... args) throws BeansException {
		throw new NotImplementedException();
	}

	@Override
	public boolean containsBean(String name) {
		throw new NotImplementedException();
	}

	@Override
	public boolean isSingleton(String name) throws NoSuchBeanDefinitionException {
		throw new NotImplementedException();
	}

	@Override
	public boolean isPrototype(String name) throws NoSuchBeanDefinitionException {
		throw new NotImplementedException();
	}

	@Override
	public boolean isTypeMatch(String name, Class<?> targetType) throws NoSuchBeanDefinitionException {
		throw new NotImplementedException();
	}

	@Override
	public Class<?> getType(String name) throws NoSuchBeanDefinitionException {
		throw new NotImplementedException();
	}

	@Override
	public String[] getAliases(String name) {
		throw new NotImplementedException();
	}

	@Override
	public Object configureBean(Object existingBean, String beanName) throws BeansException {
		throw new NotImplementedException();
	}

	@Override
	public Object resolveDependency(DependencyDescriptor descriptor, String beanName) throws BeansException {
		throw new NotImplementedException();
	}

	@Override
	public Object createBean(Class<?> beanClass, int autowireMode, boolean dependencyCheck) throws BeansException {
		throw new NotImplementedException();
	}

	@Override
	public Object autowire(Class<?> beanClass, int autowireMode, boolean dependencyCheck) throws BeansException {
		throw new NotImplementedException();
	}

	@Override
	public void applyBeanPropertyValues(Object existingBean, String beanName) throws BeansException {
		throw new NotImplementedException();
	}

	@Override
	public Object applyBeanPostProcessorsBeforeInitialization(Object existingBean, String beanName) throws BeansException {
		throw new NotImplementedException();
	}

	@Override
	public Object applyBeanPostProcessorsAfterInitialization(Object existingBean, String beanName) throws BeansException {
		throw new NotImplementedException();
	}

	@Override
	public void destroyBean(Object existingBean) {
		throw new NotImplementedException();
	}

	@Override
	public Object resolveDependency(DependencyDescriptor descriptor, String beanName, Set<String> autowiredBeanNames, TypeConverter typeConverter) throws BeansException {
		throw new NotImplementedException();
	}

}
