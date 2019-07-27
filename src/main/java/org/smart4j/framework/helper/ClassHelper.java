package org.smart4j.framework.helper;

import com.sun.org.apache.bcel.internal.generic.RETURN;
import org.smart4j.framework.context.annotation.Bean;
import org.smart4j.framework.context.annotation.Configuration;
import org.smart4j.framework.stereotype.Component;
import org.smart4j.framework.stereotype.Controller;
import org.smart4j.framework.stereotype.Service;
import org.smart4j.framework.utils.ClassUtils;
import org.smart4j.framework.utils.Logger;
import org.smart4j.framework.utils.ReflectionUtils;
import org.smart4j.framework.utils.StringUtil;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedType;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * 类操作助手类
 */
public final class ClassHelper {
    private static final Logger logger = new Logger(ClassHelper.class);
    private static final Set<Class<?>> CLASS_SET;
    private static final Set<Class<?>> SCAN_CLASS_SET;

    static {
        /**
         * 获取该应用包下的所有类
         */
        String basePackage = ConfigHelper.getAppBasePackage();
        CLASS_SET = ClassUtils.getClassSet(basePackage);
        /**
         * 获取ComponentScan扫描下的所有类
         */
        String componentScanPackage = ConfigHelper.getComponentScanPackage();
        SCAN_CLASS_SET = ClassUtils.getClassSet(componentScanPackage);
    }

    /**
     * 获取应用包名下所有类
     */
    public static Set<Class<?>> getClassSet() {
        return CLASS_SET;
    }

    /**
     * 获取ComponentScan下的所有包
     * 然后在进行注解判断，依赖注入
     *
     * @return
     */
    public static Set<Class<?>> getScanClassSet() {
        return SCAN_CLASS_SET;
    }

    /**
     * 获取ComponentScan扫描的类
     */
    public static Set<Class<?>> getScanClass(Set<Class<?>> scanClass) {
        Set<Class<?>> componentClass = new HashSet<>();
        for (Class<?> cls : scanClass) {
            Annotation[] annotations = cls.getAnnotations();
            // 首先获取标注了Component注解的类
            if (cls.isAnnotationPresent(Component.class)) {
                componentClass.add(cls);
            } else {
                // 如果没有标注Component注解，则通过标注的注解往上寻找
                for (Annotation annotation : annotations) {
                    boolean annotationPresent = annotation.annotationType().isAnnotationPresent(Component.class);
                    if (annotationPresent) {
                        componentClass.add(cls);
                    }
                }
            }
        }
        return componentClass;
    }

//    /**
//     * 获取所有Component注解类
//     */
//    public static Set<Class<?>> getComponentClass() {
//        Set<Class<?>> componentClass = new HashSet<>();
//        for (Class<?> cls : CLASS_SET) {
//            Annotation[] annotations = cls.getAnnotations();
//            for (Annotation annotation : annotations) {
//                boolean annotationPresent = annotation.annotationType().isAnnotationPresent(Component.class);
//                if(annotationPresent){
//                    componentClass.add(cls);
//                }
//            }
//            /*if (cls.isAnnotationPresent(Component.class)) {
//                String className = StringUtil.TransferClassName(cls.getName());
//                componentClass.add(cls);
//            }*/
//        }
//        return componentClass;
//    }

    /**
     * 获取所有Configuration注解配置类
     *
     * @param resolvableDependencies
     */
    public static void getConfigurationClass(Map<Class<?>, Object> resolvableDependencies) {
        for (Class<?> cls : CLASS_SET) {
            if (cls.isAnnotationPresent(Configuration.class)) {
                try {
                    Object instance = cls.newInstance();
                    Method[] methods = cls.getDeclaredMethods();
                    for (Method method : methods) {
                        if (method.isAnnotationPresent(Bean.class)) {
                            Object obj = method.invoke(instance, null);
//                            beanDefinitionMap.put(StringUtil.TransferClassName(method.getName()), obj);
                            resolvableDependencies.put(cls, ReflectionUtils.newInstance(cls));
                        }
                    }
                } catch (InstantiationException e) {
                    logger.catchException("类实例化失败");
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    logger.catchException("配置类方法映射执行失败");
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 获取应用包名下带有某注解的类
     *
     * @param cls
     * @return
     */
    public static Set<Class<?>> getClassSetByAnnotation(Class<? extends Annotation> cls) {
        Set<Class<?>> classSet = new HashSet<>();
        for (Class<?> aClass : SCAN_CLASS_SET) {
            if (aClass.isAnnotationPresent(Service.class)) {
                classSet.add(aClass);
            }
        }
        return classSet;
    }

    public static Set<Class<?>> getClassSetByController(){
        Set<Class<?>> classSet = new HashSet<>();
        for (Class<?> cls : SCAN_CLASS_SET) {
            if(cls.isAnnotationPresent(Controller.class)){
                classSet.add(cls);
            }
        }
        return classSet;
    }
}
