package org.smart4j.framework.beans.factory.support;

import org.smart4j.framework.beans.factory.BeanFactory;
import org.smart4j.framework.beans.factory.config.AutowireCapableBeanFactory;

import java.util.HashSet;
import java.util.Set;

/**
 * 抽象bean工厂超类，它实现了默认的bean创建，具有{ RootBeanDefinition}类指定的全部功能。
 * 除了AbstractBeanFactory的{ #createBean}方法之外，还实现了{ org.springframework.beans.factory.config.AutowireCapableBeanFactory}接口。
 * 提供bean创建（具有构造函数解析），属性填充，连线（包括自动装配）和初始化。处理运行时bean引用，解析托管集合，调用初始化方法等。
 * 支持自动装配构造函数，按名称的属性和按类型的属性。子类实现的主要模板方法是{ #resolveDependency（DependencyDescriptor，String，Set，TypeConverter）}，用于按类型自动装配。
 * 在工厂能够搜索其bean定义的情况下，匹配bean通常将通过这种搜索来实现。
 * 对于其他工厂样式，可以实现简化的匹配算法。请注意，此类不承担或实现bean定义注册表功能。
 * 有关{ org.springframework.beans.factory.ListableBeanFactory}和{ BeanDefinitionRegistry}接口的实现，请参阅{ DefaultListableBeanFactory}，它们代表API和SPI分别对这样一个工厂的看法。
 */
public abstract class AbstractAutowireCapableBeanFactory extends AbstractBeanFactory implements AutowireCapableBeanFactory {
    public AbstractAutowireCapableBeanFactory() {
        super();
    }

    public AbstractAutowireCapableBeanFactory(BeanFactory parentBeanFactory) {
        this();
        setParentBeanFactory(parentBeanFactory);
    }

    /**
     * 忽略依赖类型
     * 要在依赖性检查和自动装配时忽略的依赖关系类型，作为Class对象的集合：例如，String。 默认为none。
     */
    private final Set<Class<?>> ignoredDependencyTypes = new HashSet<>();

    /**
     * 忽略依赖接口
     * 依赖关系接口忽略依赖性检查和自动装配，作为一组Class对象。 默认情况下，仅忽略BeanFactory接口。
     */
    private final Set<Class<?>> ignoredDependencyInterfaces = new HashSet<>();

    @Override
    public <T> T createBean(Class<T> beanClass) {
        return null;
    }

    @Override
    public void autowireBean(Object existingBean) {

    }

    @Override
    public Object configureBean(Object existingBean, String beanName) {
        return null;
    }

    @Override
    public Object initializeBean(Object existingBean, String beanName) {
        return null;
    }

    @Override
    public void destroyBean(Object existingBeab) {

    }

    public void ignoreDependencyType(Class<?> type) {
        ignoredDependencyTypes.add(type);
    }

    public void ignoreDependencyInterfaces(Class<?> type) {
        ignoredDependencyInterfaces.add(type);
    }

}
