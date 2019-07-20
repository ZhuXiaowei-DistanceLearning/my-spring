package org.smart4j.framework.helper;

import org.smart4j.framework.context.annotation.Bean;
import org.smart4j.framework.context.annotation.Configuration;
import org.smart4j.framework.stereotype.Component;
import org.smart4j.framework.stereotype.Service;
import org.smart4j.framework.utils.ClassUtils;
import org.smart4j.framework.utils.StringUtil;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedType;
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
    private static final Set<Class<?>> CLASS_SET;
    private final Map<String, Class<?>> SCAN_CLASS = new HashMap<>();

    static {
        String basePackage = ConfigHelper.getAppBasePackage();
        CLASS_SET = ClassUtils.getClassSet(basePackage);
    }

    /**
     * 获取ComponentScan扫描的类
     */
    public static Set<Class<?>> getScanClass() {
        Set<Class<?>> scanClass = new HashSet<>();
        String scanPackage = ConfigHelper.getComponentScanPackage();
        Set<Class<?>> ScanClass = ClassUtils.getClassSet(scanPackage);
        for (Class<?> cls : ScanClass) {
            Annotation[] annotations = cls.getAnnotations();
            for (Annotation annotation : annotations) {
                boolean annotationPresent = annotation.annotationType().isAnnotationPresent(Component.class);
                if(annotationPresent){
                    scanClass.add(cls);
                }
            }
        }
        return scanClass;
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
     */
    public static Set<Class<?>> getConfigurationClass() {
        Set<Class<?>> configurationClass = new HashSet<>();
        for (Class<?> cls : CLASS_SET) {
            if (cls.isAnnotationPresent(Configuration.class)) {
                configurationClass.add(cls);
            }
        }
        return configurationClass;
    }
}
