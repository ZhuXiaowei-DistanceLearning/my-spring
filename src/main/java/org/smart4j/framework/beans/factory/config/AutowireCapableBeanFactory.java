package org.smart4j.framework.beans.factory.config;

import org.smart4j.framework.beans.factory.BeanFactory;

/**
 * 自动装配Bean
 * { org.springframework.beans.factory.BeanFactory}接口的扩展将由能够自动装配的bean工厂实现，前提是它们希望为现有bean实例公开此功能。
 * BeanFactory的这个子接口并不适用于普通的应用程序代码：对于典型的用例，请坚持使用{ org.springframework.beans.factory.BeanFactory}或{org.springframework.beans.factory.ListableBeanFactory}。
 * 其他框架的集成代码可以利用此接口来连接和填充Spring无法控制其生命周期的现有Bean实例。
 * 例如，这对WebWork Actions和Tapestry Page对象特别有用。请注意，{org.springframework.context.ApplicationContext}外观并未实现此接口，因为应用程序代码几乎不使用它。
 * 也就是说，它也可以从应用程序上下文中获得，可以通过ApplicationContext的{ org.springframework.context.ApplicationContext＃getAutowireCapableBeanFactory（）}方法访问。
 * 您还可以实现{ org.springframework.beans.factor.BeanFactoryAware}接口，该接口即使在ApplicationContext中运行时也会公开内部BeanFactory以访问AutowireCapableBeanFactory：只需将传入的BeanFactory强制转换为AutowireCapableBeanFactory。
 */

/**
 * 由能够自动装配的bean工厂实现
 * 如果
 */
public interface AutowireCapableBeanFactory extends BeanFactory {

    int AUTOWIRE_NO = 0;
    int AUTOWIRE_BY_NAME = 1;
    int AUTOWIRE_BY_TYPE = 2;
    int AUTOWIRE_CONSTRUCTOR = 3;

    <T> T createBean(Class<T> beanClass);

    void autowireBean(Object existingBean);

    Object configureBean(Object existingBean, String beanName);

    Object initializeBean(Object existingBean, String beanName);

    void destroyBean(Object existingBeab);


}
