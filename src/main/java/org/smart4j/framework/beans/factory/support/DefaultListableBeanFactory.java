package org.smart4j.framework.beans.factory.support;

import org.aopalliance.interceptor.MethodInterceptor;
import org.smart4j.framework.aop.aspect.ProxyManager;
import org.smart4j.framework.beans.factory.BeanFactory;
import org.smart4j.framework.beans.factory.annotation.Autowire;
import org.smart4j.framework.beans.factory.config.BeanDefinition;
import org.smart4j.framework.beans.factory.config.ConfigurableListableBeanFactory;
import org.smart4j.framework.context.annotation.Bean;
import org.smart4j.framework.context.annotation.Configuration;
import org.smart4j.framework.helper.AopHelper;
import org.smart4j.framework.helper.ClassHelper;
import org.smart4j.framework.stereotype.Component;
import org.smart4j.framework.utils.ReflectionUtils;
import org.smart4j.framework.utils.StringUtil;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;
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
    private final Map<String, Object> beanDefinitionMap = new ConcurrentHashMap<>();

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
     * 需要配合包扫描才能使用注解
     */
    public void registerBean() {
        // 获取所有component下的类
        Map<Class<?>, Object> scamMap = initResolverMap();
        // 初始化解析所有Component注解类
        resolverComponentClass(scamMap);
        // 注入所有Autowire注解类
        resolverAutowire(resolvableDependencies);
        // 注入所有Bean注解
        resolverBean(scamMap);
        // 注入Aspect注解类，Aop实现
        resolverAop();
        System.out.println(resolvableDependencies);
    }

    /**
     * AOP注入
     *
     * @throws Exception
     */
    private void resolverAop() {
        try {
            Map<Class<?>, Set<Class<?>>> proxyMap = null;
            proxyMap = AopHelper.createProxyMap();
            Map<Class<?>, List<MethodInterceptor>> targetMap = AopHelper.createTargetMap(proxyMap);
            for (Map.Entry<Class<?>, List<MethodInterceptor>> map : targetMap.entrySet()) {
                Class<?> targetClass = map.getKey();
                List<MethodInterceptor> proxyList = map.getValue();
                Object proxy = ProxyManager.createProxy(targetClass, proxyList);
                resolvableDependencies.put(targetClass, proxy);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void resolverBean(Map<Class<?>, Object> scanMap) {
        for (Map.Entry<Class<?>, Object> map : scanMap.entrySet()) {
            Class<?> key = map.getKey();
            Object value = map.getValue();
            Annotation[] annotations = key.getAnnotations();
            for (Annotation annotation : annotations) {
                boolean annotationPresent = annotation.annotationType().isAnnotationPresent(Component.class);
                if (annotationPresent) {
                    Method[] methods = key.getDeclaredMethods();
                    for (Method method : methods) {
                        if (method.isAnnotationPresent(Bean.class)) {
                            Object bean = getBean(StringUtil.TransferClassName(method.getName()));
                            if (bean == null) {
                                // 得到方法的结果,进行注入
                                Object result = ReflectionUtils.invokeMethod(value, method);
                                beanDefinitionMap.put(StringUtil.TransferClassName(method.getName()), result);
                            }
                        }
                    }
                }
            }
            if (key.isAnnotationPresent(Configuration.class) || key.isAnnotationPresent(Component.class)) {
                Method[] methods = key.getDeclaredMethods();
                for (Method method : methods) {
                    if (method.isAnnotationPresent(Bean.class)) {
                        Object bean = getBean(StringUtil.TransferClassName(method.getName()));
                        if (bean == null) {
                            // 得到方法的结果,进行注入
                            Object result = ReflectionUtils.invokeMethod(value, method);
                            beanDefinitionMap.put(StringUtil.TransferClassName(method.getName()), result);
                        }
                    }
                }
            }
        }
    }

    /**
     * 解析所有Configuration配置类
     * 注入配置类中的Bean方法
     *
     * @param scanMap
     */
    @Deprecated
    private void resolverConfigurationBean(Map<Class<?>, Object> scanMap) {
        for (Map.Entry<Class<?>, Object> map : scanMap.entrySet()) {
            Class<?> key = map.getKey();
            Object value = map.getValue();
            Annotation[] annotations = key.getAnnotations();
            if (key.isAnnotationPresent(Configuration.class) || key.isAnnotationPresent(Component.class)) {
                Method[] methods = key.getDeclaredMethods();
                for (Method method : methods) {
                    if (method.isAnnotationPresent(Bean.class)) {
                        Object bean = getBean(StringUtil.TransferClassName(method.getName()));
                        if (bean == null) {
                            // 得到方法的结果,进行注入
                            Object result = ReflectionUtils.invokeMethod(value, method);
                            beanDefinitionMap.put(StringUtil.TransferClassName(method.getName()), result);
                        }
                    }
                }
            }
        }
    }

    /**
     * 获取Component包下所有的类与对象映射关系
     *
     * @return
     */
    public Map<Class<?>, Object> initResolverMap() {
        Map<Class<?>, Object> scanMap = new HashMap<>();
        Set<Class<?>> scanClassSet = ClassHelper.getScanClassSet();
        Set<Class<?>> scanClass = ClassHelper.getScanClass(scanClassSet);
        for (Class<?> cls : scanClass) {
            scanMap.put(cls, ReflectionUtils.newInstance(cls));
        }
        return scanMap;
    }

    /**
     * 从component类下注入autowire
     *
     * @param scanMap
     */
    private void resolverAutowire(Map<Class<?>, Object> scanMap) {
        // 将Autowire注解注入到beanDefinitionMap中
        // 总beanDefinitionMap中注入到resolvableDependencies中
        for (Map.Entry<Class<?>, Object> map : scanMap.entrySet()) {
            // 实例类
            Class<?> key = map.getKey();
            // 实例对象
            Object value = map.getValue();
            Field[] fields = key.getDeclaredFields();
            if (fields != null || fields.length != 0) {
                for (Field field : fields) {
                    if (field.isAnnotationPresent(Autowire.class)) {
                        // 获取字段的返回值类型
                        Class<?> type = field.getType();
                        String fieldName = StringUtil.TransferClassName(field.getName());
                        Object beanValue = getBean(fieldName);
                        // 该值已经注入到容器中
                        if (beanValue != null) {
                            // 对该类中的Autowire字段进行值注入
                            ReflectionUtils.setField(value, field, beanValue);
                        } else {
                            // 该值未注入到容器中,首先保存到beanMap中
                            // 将该值注入
                            beanDefinitionMap.put(fieldName, ReflectionUtils.newInstance(type));
                            ReflectionUtils.setField(value, field, ReflectionUtils.newInstance(type));
                        }
                    }
                }
            }
        }
    }

    /**
     * 注入所有Component注解类
     *
     * @param scanMap
     */
    private void resolverComponentClass(Map<Class<?>, Object> scanMap) {
        for (Map.Entry<Class<?>, Object> map : scanMap.entrySet()) {
            Class<?> key = map.getKey();
            Object value = map.getValue();
            resolvableDependencies.put(key, value);
        }
    }

    @Override
    public <T> T getBean(Class<T> requiredType) {
        return (T) this.resolvableDependencies.get(requiredType);
    }

    @Override
    public Object getBean(String name, Object... args) {
        return super.getBean(name, args);
    }

    @Override
    public Object getBean(String name) {
        return this.beanDefinitionMap.get(name);
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
