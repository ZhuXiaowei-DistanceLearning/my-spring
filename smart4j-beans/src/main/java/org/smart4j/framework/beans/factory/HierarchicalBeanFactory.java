package org.smart4j.framework.beans.factory;

/**
 * 设置多个BeanFactory容器之间的父子关系
 */
public interface HierarchicalBeanFactory extends BeanFactory {

    /**
     * 获取父容器
     *
     * @return
     */
    BeanFactory getParentBeanFactory();

    /**
     * 判断是否包含指定名称的local Bean
     */
    boolean containsLocalBean(String name);
}
