package org.smart4j.framework.beans.factory;

import com.sun.istack.internal.Nullable;

/**
 *用于访问Spring bean容器的根接口。
 * 这是bean容器的基本客户端视图;其他接口，例如{ ListableBeanFactory}和{ org.springframework.beans.factory.config.ConfigurableBeanFactory}可用于特定目的
 * 此接口由包含许多bean定义的对象实现，每个bean定义由String名称唯一标识。
 * 根据bean定义，工厂将返回包含对象的独立实例（Prototype设计模式）或单个共享实例（Singleton设计模式的高级替代，其中实例是范围中的单例工厂）。
 * 将返回哪种类型的实例取决于bean工厂配置：API是相同的。
 * 从Spring 2.0开始，根据具体的应用程序上下文（例如Web环境中的“请求”和“会话”范围），可以使用更多的范围。
 * 这种方法的重点是BeanFactory是应用程序组件的中央注册表，并集中应用程序组件的配置（例如，不再需要单个对象读取属性文件）。
 * 有关此方法的优点的讨论，请参见“Expert One-on-One J2EE设计和开发”的第4章和第11章。请注意，通常最好依靠依赖注入（“推送”配置）来通过setter或构造函数配置应用程序对象，而不是使用任何形式的“拉”配置，如BeanFactory查找。
 * Spring的依赖注入功能是使用此BeanFactory接口及其子接口实现。通常，BeanFactory将加载存储在配置源（例如XML文档）中的bean定义，并使用{ org.springframework.beans}包来配置bean。
 * 但是，实现可以直接在Java代码中直接返回它创建的Java对象。
 * 对如何存储定义没有任何限制：LDAP，RDBMS，XML，属性文件等。
 * 鼓励实现支持bean之间的引用（依赖注入）。与{ ListableBeanFactory}中的方法相比，所有的如果这是{ HierarchicalBeanFactory}，则此接口中的操作也将检查父工
 * 如果在此工厂实例中找不到bean，则会询问直接父工厂。
 * 此工厂实例中的Bean应该在任何父工厂中覆盖同名的Bean。 Bean工厂实现应尽可能支持标准bean生命周期接口。
 * 完整的初始化方法及其标准顺序是：
 */
public interface BeanFactory {
    String FACTORY_BEAN_PREFIX = "&";

    Object getBean(String name);

    <T> T getBean(String name, @Nullable Class<T> requiredType);

    Object getBean(String name, Object... args);

    <T> T getBean(Class<T> requiredType);

    <T> T getBean(Class<T> requiredType, Object... args);


    boolean containsBean(String name);

    boolean isSingleton(String name);

    boolean isPrototype(String name);

    @Nullable
    Class<?> getType(String name);

    String[] getAliases(String name);
}
