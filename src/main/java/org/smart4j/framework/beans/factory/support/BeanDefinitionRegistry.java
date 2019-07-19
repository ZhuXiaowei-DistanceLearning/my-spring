package org.smart4j.framework.beans.factory.support;

import org.smart4j.framework.beans.factory.config.BeanDefinition;
import org.smart4j.framework.core.AliasRegistry;

/**
 * 包含bean定义的注册表的接口，例如RootBeanDefinition和ChildBeanDefinition实例。
 * 通常由BeanFactories实现，内部使用AbstractBeanDefinition层次结构。
 * 这是Spring的bean工厂包中唯一封装bean定义注册的接口。
 * 标准BeanFactory接口仅涵盖对完全配置的工厂实例的访问.Spring的bean定义读者希望能够使用此接口的实现。
 * Spring核心中的已知实现者是DefaultListableBeanFactory和GenericApplicationContext。
 */
public interface BeanDefinitionRegistry extends AliasRegistry {
    /**
     * 注册Bean
     *
     * @param beanName
     * @param beanDefinition
     */
    void registerBeanDefinition(String beanName, BeanDefinition beanDefinition);

    /**
     * 移除Bean
     *
     * @param beanName
     */
    void removeBeanDefinition(String beanName);

    /**
     * 获取Bean
     *
     * @param beanName
     * @return
     */
    BeanDefinition getBeanDefinition(String beanName);

    /**
     * 是否包含Bean
     *
     * @param beanName
     * @return
     */
    boolean containsBeanDefinition(String beanName);

    /**
     * 获取所有Bean的名称
     *
     * @return
     */
    String[] getBeanDefinitionNames();

    /**
     * 获取Bean的个数
     *
     * @return
     */
    int getBeanDefinitionCount();

    /**
     * 判断Bean是否被使用
     *
     * @param beanName
     * @return
     */
    boolean isBeanNameInUse(String beanName);
}
