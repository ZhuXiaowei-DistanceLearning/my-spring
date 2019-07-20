package org.smart4j.framework.beans.factory.support;

import org.smart4j.framework.beans.factory.BeanFactory;
import org.smart4j.framework.beans.factory.config.BeanDefinition;
import org.smart4j.framework.beans.factory.config.ConfigurableListableBeanFactory;
import org.smart4j.framework.helper.ClassHelper;
import org.smart4j.framework.utils.ClassUtils;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * {org.springframework.beans.factory.ListableBeanFactory}和{BeanDefinitionRegistry}接口的默认实现：
 * 基于bean定义对象的完整bean工厂。
 * 典型用法是首先注册所有bean定义（可能从bean定义文件），在访问bean之前。
 * 因此，Bean定义查找在本地bean定义表中是一种廉价的操作，在预构建的bean定义元数据对象上运行。
 * 可以用作独立的bean工厂，也可以用作自定义bean工厂的超类。
 * 请注意，特定bean定义格式的读者通常是单独实现而不是作为bean工厂子类实现：请参阅示例{PropertiesBeanDefinitionReader}和{org.springframework.beans.factory.xml.XmlBeanDefinitionReader}。
 * 有关替代实现{org.springframework.beans.factory.ListableBeanFactory}接口，查看{StaticListableBeanFactory}，它管理现有的bean实例，而不是根据bean定义创建新的实例。
 */
public class DefaultListableBeanFactory extends AbstractAutowireCapableBeanFactory implements BeanDefinitionRegistry, ConfigurableListableBeanFactory {
    // bean定义对象的映射，由bean名称键入
    private final Map<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<>();

    // 单例和非单例bean名称的映射，由依赖类型键入
    private final Map<Class<?>, String[]> allBeanNamesByType = new ConcurrentHashMap<>();

    // 注册顺序中的bean定义名称列表
    private volatile List<String> beanDefinitionNames = new ArrayList<>();

    // 从依赖类型映射到相应的自动装配值
    private final Map<Class<?>, Object> resolvableDependencies = new ConcurrentHashMap<>(16);

    public DefaultListableBeanFactory() {
        super();
    }

    public DefaultListableBeanFactory(BeanFactory parentBeanFactory) {
        super(parentBeanFactory);
    }

    /**
     * 默认注入bean方法
     */
    public void registerBean(){
//        Set<Class<?>> classes = ClassHelper.getComponentClass();
        Set<Class<?>> configurationClass = ClassHelper.getConfigurationClass();
        Set<Class<?>> scanClass = ClassHelper.getScanClass();
    }
    @Override
    public <T> T getBean(Class<T> requiredType) {
        return super.getBean(requiredType);
    }

    @Override
    public Object getBean(String name, Object... args) {
        return super.getBean(name, args);
    }

    @Override
    public boolean containsBean(String name) {
        return super.containsBean(name);
    }


    @Override
    public String[] getBeanNamesForType(Class<?> type) {
        return new String[0];
    }

    @Override
    public String[] getBeanNamesForType(Class<?> type, boolean allowEagerInit) {
        return new String[0];
    }

    @Override
    public <T> Map<String, T> getBeansOfType(Class<T> type) {
        return null;
    }

    @Override
    public <T> Map<String, T> getBeansOfType(Class<T> type, boolean includeNonSingletons, boolean allowEagerInit) {
        return null;
    }

    @Override
    public String[] getBeanNamesForAnnotation(Class<? extends Annotation> annotationType) {
        return new String[0];
    }

    @Override
    public Map<String, Object> getBeansWithAnnotation(Class<? extends Annotation> annotationType) {
        return null;
    }

    @Override
    public <A extends Annotation> A findAnnotationOnBean(String beanName, Class<A> annotationType) {
        return null;
    }

    @Override
    /**
     * 注入Bean
     */
    public void registerBeanDefinition(String beanName, BeanDefinition beanDefinition) {

    }

    @Override
    public void removeBeanDefinition(String beanName) {

    }

    @Override
    /**
     * 继承父工厂抽象方法
     */
    public BeanDefinition getBeanDefinition(String beanName) {
        return null;
    }

    @Override
    /**
     * 继承父工厂抽象方法
     */
    protected Object createBean(String beanName, Object object) {
        return null;
    }

    @Override
    /**
     * 继承父工厂抽象方法
     */
    public boolean containsBeanDefinition(String beanName) {
        return false;
    }


    @Override
    public String[] getBeanDefinitionNames() {
        return new String[0];
    }

    @Override
    public int getBeanDefinitionCount() {
        return this.beanDefinitionMap.size();
    }

    @Override
    public boolean isBeanNameInUse(String beanName) {
        return false;
    }

    @Override
    public void registerAlias(String var1, String var2) {

    }

    @Override
    public void removeAlias(String var1) {

    }

    @Override
    public boolean isAlias(String var1) {
        return false;
    }

    @Override
    public String[] getAlias(String var1) {
        return new String[0];
    }
}
