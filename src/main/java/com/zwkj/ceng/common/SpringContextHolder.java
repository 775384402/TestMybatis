package com.zwkj.ceng.common;

import org.apache.logging.log4j.core.util.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.util.ArrayList;
import java.util.List;

/**
 * 以静态变量保存Spring ApplicationContext, 可在任何代码任何地方任何时候取出ApplicaitonContext.
 * <p>
 * <p>
 * zhuyuef 2016-8-10
 */
public class SpringContextHolder implements ApplicationContextAware, DisposableBean {

    private static ApplicationContext applicationContext = null;

    private static final Logger LOGGER = LoggerFactory.getLogger(SpringContextHolder.class);

    public static ApplicationContext getApplicationContext() {
        assertContextInjected();
        return applicationContext;
    }

    @SuppressWarnings("unchecked")
    public static <T> T getBean(String name) {
        assertContextInjected();
        return (T) applicationContext.getBean(name);
    }

    public static <T> T getBean(Class<T> requiredType) {
        assertContextInjected();
        return applicationContext.getBean(requiredType);
    }

    public static <T> List<T> getBeans(Class<T> beanClass) {
        return new ArrayList(applicationContext.getBeansOfType(beanClass).values());
    }

    public static void clearHolder() {
        applicationContext = null;
    }

    @Override
    public void setApplicationContext(ApplicationContext appContext) {
        if (applicationContext != null) {
            LOGGER.warn("ApplicationContext overrided, original ApplicationContext:" + SpringContextHolder.applicationContext);
        }

        //avoid findbugs ST_WRITE_TO_STATIC_FROM_INSTANCE_METHOD
        setApp(appContext);
    }

    private static void setApp(ApplicationContext appContext) {
        applicationContext = appContext;
    }

    @Override
    public void destroy() throws Exception {
        SpringContextHolder.clearHolder();
    }

    private static void assertContextInjected() {
        Assert.requireNonEmpty(applicationContext, "applicaitonContext属性未注入, 请在applicationContext.xml中定义SpringContextHolder.");
    }
}