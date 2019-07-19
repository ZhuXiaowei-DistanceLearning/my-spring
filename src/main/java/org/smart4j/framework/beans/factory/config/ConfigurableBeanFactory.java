package org.smart4j.framework.beans.factory.config;

import com.sun.istack.internal.Nullable;
import org.smart4j.framework.beans.factory.BeanFactory;
import org.smart4j.framework.beans.factory.HierarchicalBeanFactory;

/**
 *  配置接口大多数由bean工厂实现，除了BeanFactory中的bean工厂客户端方法之外，还提供配置bean工厂的工具
 *  此bean工厂接口不适用于普通应用程序代码
 *  这个扩展接口只是为了允许框架内部的即插即用和对bean工厂配置方法的特殊访问。
 */
public interface ConfigurableBeanFactory extends HierarchicalBeanFactory {
    ClassLoader getBeanClassLoader();

    void setTempClassLoader(ClassLoader tempClassLoader);

    ClassLoader getTempClassLoader();
}
