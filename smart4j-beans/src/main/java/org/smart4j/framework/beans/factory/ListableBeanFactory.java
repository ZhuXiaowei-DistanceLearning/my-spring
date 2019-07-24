package org.smart4j.framework.beans.factory;


import java.lang.annotation.Annotation;
import java.util.Map;

/**
 * 通过此接口获取多个Bean，顶层BeanFactory获取单个Bean
 * { BeanFactory}接口的扩展由bean工厂实现，可以枚举所有bean实例，而不是按客户端的请求逐个尝试按名称查找bean。
 * 预加载所有bean定义（例如基于XML的工厂）的BeanFactory实现可以实现此接口。
 * 如果这是{ HierarchicalBeanFactory}，则返回值不会考虑任何BeanFactory层次结构，而只会涉及bean在当前工厂中定义。
 * 使用{ BeanFactoryUtils}帮助程序类来考虑祖先工厂中的bean。此接口中的方法将仅考虑此工厂的bean定义。
 * 他们将忽略已经通过{ org.springframework.beans.factory.config.ConfigurableBeanFactory}的{ registerSingleton}方法等其他方式注册的任何单例bean，但{ getBeanNamesOfType}和{@除外代码getBeansOfType}也将检查这样的手动注册单例。
 * 当然，BeanFactory的{ getBean}确实允许透明访问这些特殊的bean。
 * 但是，在典型的场景中，所有bean都将由外部bean定义定义，因此大多数应用程序不需要担心这种区别。
 * 注意：除了{ getBeanDefinitionCount}和{ containsBeanDefinition}之外，此接口中的方法不是为频繁调用而设计的。实施可能很慢。
 */

/**
 * 此接口可以被实现
 * 如果需要预载所有Bean定义的工厂就实现此接口
 * 工厂也分很多
 * 如果该工厂需要预加载所有Bean定义则实现此接口
 */
public interface ListableBeanFactory extends BeanFactory {
    /**
     *
     */
    boolean containsBeanDefinition(String beanName);

    int getBeanDefinitionCount();

    String[] getBeanDefinitionNames();

    /**
     * 查找符合该类型的所有bean的名称
     * @param type
     * @return
     */
    String[] getBeanNamesForType(Class<?> type);

    String[] getBeanNamesForType(Class<?> type, boolean allowEagerInit);

    /**
     * 获取Bean实例
     * @param type
     * @param <T>
     * @return
     */
    <T> Map<String, T> getBeansOfType(Class<T> type);

    <T> Map<String, T> getBeansOfType(Class<T> type, boolean includeNonSingletons, boolean allowEagerInit);

    /**
     * 查找所有包含此注解的Bean的名称
     * @param annotationType
     * @return
     */
    String[] getBeanNamesForAnnotation(Class<? extends Annotation> annotationType);

    /**
     * 查找所有符合该注解类型的Bean，并返回Map 实例
     * @param annotationType
     * @return
     */
    Map<String, Object> getBeansWithAnnotation(Class<? extends Annotation> annotationType);

    /**
     * E - Element (在集合中使用，因为集合中存放的是元素)
     *  T - Type（Java 类）
     *  K - Key（键）
     *  V - Value（值）
     *  N - Number（数值类型）
     * ？ -  表示不确定的java类型
     *  S、U、V  - 2nd、3rd、4th types
     *  如果在给定的类本身上找不到注解，则在指定的bean查找，遍历其接口和超类
     * @param beanName
     * @param annotationType
     * @param <A>:annotation的子类
     * @return
     */
    <A extends Annotation> A findAnnotationOnBean(String beanName, Class<A> annotationType);
}
