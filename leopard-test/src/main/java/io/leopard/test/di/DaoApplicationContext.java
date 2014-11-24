package io.leopard.test.di;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.lang.NotImplementedException;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.context.NoSuchMessageException;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;

public class DaoApplicationContext implements ApplicationContext {

	@Override
	public Environment getEnvironment() {
		throw new NotImplementedException();
	}

	@Override
	public boolean containsBeanDefinition(String beanName) {
		throw new NotImplementedException();
	}

	@Override
	public int getBeanDefinitionCount() {
		throw new NotImplementedException();
	}

	@Override
	public String[] getBeanDefinitionNames() {
		throw new NotImplementedException();
	}

	@Override
	public String[] getBeanNamesForType(Class<?> type) {
		throw new NotImplementedException();
	}

	@Override
	public String[] getBeanNamesForType(Class<?> type, boolean includeNonSingletons, boolean allowEagerInit) {
		throw new NotImplementedException();
	}

	@Override
	public <T> Map<String, T> getBeansOfType(Class<T> type) throws BeansException {
		throw new NotImplementedException();
	}

	@Override
	public <T> Map<String, T> getBeansOfType(Class<T> type, boolean includeNonSingletons, boolean allowEagerInit) throws BeansException {
		throw new NotImplementedException();
	}

	@Override
	public String[] getBeanNamesForAnnotation(Class<? extends Annotation> annotationType) {
		throw new NotImplementedException();
	}

	@Override
	public Map<String, Object> getBeansWithAnnotation(Class<? extends Annotation> annotationType) throws BeansException {
		throw new NotImplementedException();
	}

	@Override
	public <A extends Annotation> A findAnnotationOnBean(String beanName, Class<A> annotationType) throws NoSuchBeanDefinitionException {
		throw new NotImplementedException();
	}

	@Override
	public Object getBean(String name) throws BeansException {
		throw new NotImplementedException();
	}

	@Override
	public <T> T getBean(String name, Class<T> requiredType) throws BeansException {
		throw new NotImplementedException();
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
	public BeanFactory getParentBeanFactory() {
		throw new NotImplementedException();
	}

	@Override
	public boolean containsLocalBean(String name) {
		throw new NotImplementedException();
	}

	@Override
	public String getMessage(String code, Object[] args, String defaultMessage, Locale locale) {
		throw new NotImplementedException();
	}

	@Override
	public String getMessage(String code, Object[] args, Locale locale) throws NoSuchMessageException {
		throw new NotImplementedException();
	}

	@Override
	public String getMessage(MessageSourceResolvable resolvable, Locale locale) throws NoSuchMessageException {
		throw new NotImplementedException();
	}

	@Override
	public void publishEvent(ApplicationEvent event) {
		throw new NotImplementedException();
	}

	@Override
	public Resource[] getResources(String arg0) throws IOException {
		throw new NotImplementedException();
	}

	@Override
	public ClassLoader getClassLoader() {
		throw new NotImplementedException();
	}

	@Override
	public Resource getResource(String arg0) {
		throw new NotImplementedException();
	}

	@Override
	public String getId() {
		throw new NotImplementedException();
	}

	@Override
	public String getApplicationName() {
		throw new NotImplementedException();
	}

	@Override
	public String getDisplayName() {
		throw new NotImplementedException();
	}

	@Override
	public long getStartupDate() {
		throw new NotImplementedException();
	}

	@Override
	public ApplicationContext getParent() {
		throw new NotImplementedException();
	}

	private AutowireCapableBeanFactory beanFactory = new DaoAutowireCapableBeanFactory();

	@Override
	public AutowireCapableBeanFactory getAutowireCapableBeanFactory() throws IllegalStateException {
		return beanFactory;
	}

}
