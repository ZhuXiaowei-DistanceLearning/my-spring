package org.smart4j.framework.beans.factory.config;

/**
 * 将Bean的定义保存到BeanDefinition实例中
 * BeanDefinition 中保存了我们的 Bean 信息，比如这个 Bean 指向的是哪个类、是否是单例的、是否懒加载、这个 Bean 依赖了哪些 Bean 等等。
 * BeanDefinition 描述了一个bean实例，它具有属性值，构造函数参数值以及具体实现提供的更多信息
 * 这只是一个最小的接口：主要目的是允许{BeanFactoryPostProcessor}，例如{PropertyPlaceholderConfigurer} 内省和修改属性值和其他bean元数据。
 */
public interface BeanDefinition {
    // 比较不重要，直接跳过吧
    int ROLE_APPLICATION = 0;
    int ROLE_SUPPORT = 1;
    int ROLE_INFRASTRUCTURE = 2;

    // 设置父 Bean，这里涉及到 bean 继承，不是 java 继承。请参见附录的详细介绍
    // 一句话就是：继承父 Bean 的配置信息而已
    void setParentName(String parentName);

    // 获取父 Bean
    String getParentName();

    // 设置 Bean 的类名称，将来是要通过反射来生成实例的
    void setBeanClassName(String beanClassName);

    // 获取 Bean 的类名称
    String getBeanClassName();


    // 设置 bean 的 scope
    void setScope(String scope);

    String getScope();

    // 设置是否懒加载
    void setLazyInit(boolean lazyInit);

    boolean isLazyInit();

    // 设置该 Bean 依赖的所有的 Bean，注意，这里的依赖不是指属性依赖(如 @Autowire 标记的)，
    // 是 depends-on="" 属性设置的值。
    void setDependsOn(String... dependsOn);

    // 返回该 Bean 的所有依赖
    String[] getDependsOn();

    // 设置该 Bean 是否可以注入到其他 Bean 中，只对根据类型注入有效，
    // 如果根据名称注入，即使这边设置了 false，也是可以的
    void setAutowireCandidate(boolean autowireCandidate);

    // 该 Bean 是否可以注入到其他 Bean 中
    boolean isAutowireCandidate();

    // 主要的。同一接口的多个实现，如果不指定名字的话，Spring 会优先选择设置 primary 为 true 的 bean
    void setPrimary(boolean primary);

    // 是否是 primary 的
    boolean isPrimary();

    // 如果该 Bean 采用工厂方法生成，指定工厂名称。对工厂不熟悉的读者，请参加附录
    // 一句话就是：有些实例不是用反射生成的，而是用工厂模式生成的
    void setFactoryBeanName(String factoryBeanName);

    // 获取工厂名称
    String getFactoryBeanName();

    // 指定工厂类中的 工厂方法名称
    void setFactoryMethodName(String factoryMethodName);

    // 获取工厂类中的 工厂方法名称
    String getFactoryMethodName();

    // 是否 singleton
    boolean isSingleton();

    // 是否 prototype
    boolean isPrototype();

    // 如果这个 Bean 是被设置为 abstract，那么不能实例化，
    // 常用于作为 父bean 用于继承，其实也很少用......
    boolean isAbstract();

    int getRole();

    String getDescription();

    String getResourceDescription();

    BeanDefinition getOriginatingBeanDefinition();
}
