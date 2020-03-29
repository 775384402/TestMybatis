package com.zwkj.ceng.common;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.SingletonBeanRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.util.Assert;

/**
 * Created by mark.huang on 2016-06-17.
 */
public class SpringObjectFactory {
	private ApplicationContext applicationContext;

	public <T> T createBean(Class<T> beanClass) {
		return applicationContext.getAutowireCapableBeanFactory().createBean(beanClass);
	}

	public Object createBean(Class<?> beanClass, int autowireMode, boolean dependencyCheck) {
		return applicationContext.getAutowireCapableBeanFactory().createBean(beanClass, autowireMode, dependencyCheck);
	}

	public <T> T getBean(Class<T> beanClass) {
		return applicationContext.getBean(beanClass);
	}

	public <T> T getBean(Class<T> beanClass, Object... args) {
		return applicationContext.getBean(beanClass, args);
	}

	public <T> T getBean(String name) {
		return (T) applicationContext.getBean(name);
	}

	public <T> List<T> getBeans(Class<T> beanClass) {
		return new ArrayList<>(applicationContext.getBeansOfType(beanClass).values());
	}

	@SuppressWarnings("unchecked")
	public <T> T initializeBean(T bean) {
		return initializeBean(bean, bean.getClass().getName());
	}

	@SuppressWarnings("unchecked")
	public <T> T initializeBean(T bean, String beanName) {
		AutowireCapableBeanFactory beanFactory = applicationContext.getAutowireCapableBeanFactory();
		beanFactory.autowireBean(bean);
		return (T) beanFactory.initializeBean(bean, beanName);
	}

	public void registerSingletonBean(String beanName, Class beanClass) {
		registerBean(beanName, beanClass, BeanDefinition.SCOPE_SINGLETON);
	}

	public void registerBean(String beanName, Class beanClass, String scope) {
		GenericBeanDefinition definition = new GenericBeanDefinition();
		definition.setBeanClass(beanClass);
		definition.setScope(scope);
		BeanDefinitionRegistry registry = (BeanDefinitionRegistry) applicationContext.getAutowireCapableBeanFactory();
		registry.registerBeanDefinition(beanName, definition);
	}

	public void registerSingletonBean(String beanName, Object bean) {
		SingletonBeanRegistry registry = (SingletonBeanRegistry) applicationContext.getAutowireCapableBeanFactory();
		registry.registerSingleton(beanName, bean);
	}

	@Autowired
	public void setApplicationContext(ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
		AutowireCapableBeanFactory autowireCapableBeanFactory = applicationContext.getAutowireCapableBeanFactory();
		Assert.isTrue(autowireCapableBeanFactory instanceof BeanDefinitionRegistry,
				"autowireCapableBeanFactory should be BeanDefinitionRegistry");
		Assert.isTrue(autowireCapableBeanFactory instanceof SingletonBeanRegistry,
				"autowireCapableBeanFactory should be BeanDefinitionRegistry");
	}
}
