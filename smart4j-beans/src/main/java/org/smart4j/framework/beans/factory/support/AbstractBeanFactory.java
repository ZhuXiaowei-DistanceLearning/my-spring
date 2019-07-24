package org.smart4j.framework.beans.factory.support;

import org.smart4j.framework.beans.factory.BeanFactory;
import org.smart4j.framework.beans.factory.config.BeanDefinition;
import org.smart4j.framework.beans.factory.config.ConfigurableBeanFactory;

/**
 * { org.springframework.beans.factory.BeanFactory}实现的抽象基类，提供{ org.springframework.beans.factory.config.ConfigurableBeanFactory} SPI的全部功能。
 * 不假设可列表bean工厂：因此也可以用作bean工厂实现的基类，它从一些后端资源获取bean定义（其中bean定义访问是一项昂贵的操作）。
 * 这个类提供单例缓存（通过其基类） { org.springframework.beans.factory.support.DefaultSingletonBeanRegistry}，单例/原型确定，{ org.springframework.beans.factory.FactoryBean}处理，别名，bean定义的bean定义合并以及bean销毁（ { org.springframework.beans.factory.DisposableBean}接口，自定义销毁方法）。
 * 此外，它可以通过实现{ org.springframework.beans.factory.HierarchicalBeanFactory来管理bean工厂层次结构（在未知bean的情况下委托给父级）接口。
 * 子类实现的主要模板方法是{ #getBeanDefinition}和{#createBean}，检索给定bean名称的bean定义并创建一个分别为给定bean定义的bean实例。
 * 可以在{DefaultListableBeanFactory}和{AbstractAutowireCapableBeanFactory}中找到这些操作的默认实现。
 */
public abstract class AbstractBeanFactory implements ConfigurableBeanFactory {

    /**
     * 父bean工厂，用于bean继承支持
     */
    private BeanFactory parentBeanFactory;

    public AbstractBeanFactory() {
    }

    /**
     * 使用给定父级创建新的AbstractBeanFactory
     * 在此处传入其他实现了BeanFactory接口的类，可覆盖此抽象类
     *
     * @param parentBeanFactory
     */
    public AbstractBeanFactory(BeanFactory parentBeanFactory) {
        this.parentBeanFactory = parentBeanFactory;
    }

    @Override
    public Object getBean(String name) {
        return doGetBean(name, null, null, false);
    }

    @Override
    public <T> T getBean(String name, Class<T> requiredType) {
        return doGetBean(name, requiredType, null, false);
    }

    @Override
    public Object getBean(String name, Object... args) {
        return doGetBean(name, null, args, false);
    }

    @Override
    public <T> T getBean(Class<T> requiredType) {
        return doGetBean(null, requiredType, null, false);
    }

    @Override
    public <T> T getBean(Class<T> requiredType, Object... args) {
        return doGetBean(null, requiredType, args, false);
    }

    /**
     * @param name
     * @param cls
     * @param args          arguments to use when creating a bean instance using explicit arguments 使用显式参数创建bean实例时使用的参数
     * @param typeCheckOnly 判断该bean是否被创建
     * @param <T>
     * @return an instance of the bean
     */
    protected <T> T doGetBean(final String name, final Class<?> cls, Object[] args, boolean typeCheckOnly) {
        Object bean = null;

        return (T) bean;
    }

    @Override
    public boolean containsBean(String name) {
        return false;
    }

    @Override
    public boolean isSingleton(String name) {
        return false;
    }

    @Override
    public boolean isPrototype(String name) {
        return false;
    }

    @Override
    public Class<?> getType(String name) {
        return null;
    }

    @Override
    public String[] getAliases(String name) {
        return new String[0];
    }

    @Override
    public BeanFactory getParentBeanFactory() {
        return this.parentBeanFactory;
    }

    @Override
    public boolean containsLocalBean(String name) {
        return false;
    }

    @Override
    public ClassLoader getBeanClassLoader() {
        return null;
    }

    @Override
    public void setTempClassLoader(ClassLoader tempClassLoader) {

    }

    @Override
    public ClassLoader getTempClassLoader() {
        return null;
    }

    public void setParentBeanFactory(BeanFactory parentBeanFactory) {
        if (this.parentBeanFactory != null && this.parentBeanFactory != parentBeanFactory) {
        }
        this.parentBeanFactory = parentBeanFactory;
    }

    protected abstract boolean containsBeanDefinition(String beanName);

    protected abstract BeanDefinition getBeanDefinition(String beanName);

    protected abstract Object createBean(String beanName, Object object);
}
