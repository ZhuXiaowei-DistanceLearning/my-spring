package org.smart4j.framework.beans.factory.config;

import org.smart4j.framework.beans.factory.HierarchicalBeanFactory;
import org.smart4j.framework.beans.factory.ListableBeanFactory;

/**
 * 配置接口由大多数可列出的bean工厂实现。
 * 除了{ ConfigurableBeanFactory}之外，它还提供了分析和修改bean定义以及预先实例化单例的工具。
 * { org.springframework.beans.factory.BeanFactory}的这个子接口并不适用于普通的应用程序代码：坚持{ org.springframework.beans.factory.BeanFactory}或
 * { org.springframework。 beans.factory.ListableBeanFactory}用于典型用例。
 * 这个接口只是为了允许框架内部的即插即用，即使需要访问bean工厂配置方法。
 */
public interface ConfigurableListableBeanFactory extends ListableBeanFactory, AutowireCapableBeanFactory,ConfigurableBeanFactory {
    BeanDefinition getBeanDefinition(String beanName);
}
