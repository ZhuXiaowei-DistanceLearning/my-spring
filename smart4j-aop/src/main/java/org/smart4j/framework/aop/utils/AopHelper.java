package org.smart4j.framework.aop.utils;

import org.aopalliance.interceptor.MethodInterceptor;
import org.smart4j.framework.aop.annotation.Aspect;
import org.smart4j.framework.aop.annotation.Transactional;
import org.smart4j.framework.aop.aspect.AspectJAroundAdvice;
import org.smart4j.framework.aop.aspect.AspectJTransactionalAdvice;
import org.smart4j.framework.helper.ClassHelper;
import org.smart4j.framework.stereotype.Service;
import org.smart4j.framework.utils.ClassUtils;
import org.smart4j.framework.utils.ReflectionUtils;

import java.lang.reflect.Method;
import java.util.*;

public final class AopHelper {
    /**
     * 判断该类是否有方法标注了@Transactional注解
     *
     * @param cls
     * @return
     */
    private static Class resolverTransactionMethod(Class<?> cls) {
        Method[] methods = cls.getDeclaredMethods();
        for (Method method : methods) {
            if (method.isAnnotationPresent(Transactional.class)) {
                return cls;
            }
        }
        return null;
    }

    /**
     * 获取事务代理类与目标类之间的映射关系
     *
     * @param proxyMap
     * @return
     */
    private static void addTransactionProxy(Map<Class<?>, Set<Class<?>>> proxyMap) {
        Set<Class<?>> classSetByAnnotation = ClassHelper.getClassSetByAnnotation(Service.class);
        Set<Class<?>> transactionalClassSet = new HashSet<>();
        for (Class<?> aClass : classSetByAnnotation) {
            Class cls = resolverTransactionMethod(aClass);
            if (cls != null) {
                transactionalClassSet.add(cls);
            }
        }
        proxyMap.put(AspectJTransactionalAdvice.class, transactionalClassSet);
    }

    /**
     * 普通切面类代理
     */
    private static void addAspectProxy(Map<Class<?>, Set<Class<?>>> proxyMap) throws Exception {
        Set<Class<?>> classSet = ClassHelper.getClassSet();
        for (Class<?> cls : classSet) {
            // 判断该类是否是切面代理类
            if (cls.isAnnotationPresent(Aspect.class)) {
                Aspect annotation = cls.getAnnotation(Aspect.class);
                if (annotation != null) {
                    String value = annotation.value();
                    // 获取@Aspect注解扫描配置下的所有类，建立代理关系
                    Set<Class<?>> targetClass = ClassUtils.getClassSet(value);
                    proxyMap.put(cls, targetClass);
                }
            }
        }
    }

    /**
     * 获取代理类与目标类之间的映射关系
     */
    public static Map<Class<?>, Set<Class<?>>> createProxyMap() throws Exception {
        Map<Class<?>, Set<Class<?>>> proxyMap = new HashMap<>();
        addAspectProxy(proxyMap);
        addTransactionProxy(proxyMap);
        return proxyMap;
    }

    /**
     * 创建目标类和@Aspect注解代理对象之间的映射关系
     */
    public static <T> Map<Class<?>, List<Object>> createAspectMap(Map<Class<?>, Set<Class<?>>> proxyMap) {
        Map<Class<?>, List<Object>> targetMap = new HashMap<>();
        for (Map.Entry<Class<?>, Set<Class<?>>> map : proxyMap.entrySet()) {
            // 代理类
            Class<?> key = map.getKey();
            // 目标类集合
            Set<Class<?>> targetClassSet = map.getValue();
            for (Class<?> targetClass : targetClassSet) {
                // 如果是切面代理，则使用Aspect注解类作为代理类
                Method[] methods = targetClass.getDeclaredMethods();
                // 判断该类方法上是否有Transactional注解
                for (Method method : methods) {
                    // 如果是事务代理
                    if (method.isAnnotationPresent(Transactional.class)) {
                        if (targetMap.containsKey(targetClass)) {
                            targetMap.get(targetClass).add(ReflectionUtils.newInstance(AspectJTransactionalAdvice.class));
                            break;
                        } else {
                            List<Object> list = new ArrayList<>();
                            list.add(ReflectionUtils.newInstance(AspectJTransactionalAdvice.class));
                            targetMap.put(targetClass, list);
                            break;
                        }
                    }
                }
                // 如果是非事务代理，则使用默认代理类
                if (targetMap.containsKey(targetClass)) {
                    continue;
                } else {
                    List<Object> list = new ArrayList<>();
                    list.add(ReflectionUtils.newInstance(key));
                    targetMap.put(targetClass, list);
                }
            }
        }
        return targetMap;
    }

    /**
     * 创建目标类与代理对象之间的映射关系
     *
     * @param proxyMap
     * @return
     */
    public static Map<Class<?>, List<MethodInterceptor>> createTargetMap(Map<Class<?>, Set<Class<?>>>
                                                                                 proxyMap) throws Exception {
        Map<Class<?>, List<MethodInterceptor>> targetMap = new HashMap<>();
        for (Map.Entry<Class<?>, Set<Class<?>>> map : proxyMap.entrySet()) {
            // 代理类
            Class<?> key = map.getKey();
            // 目标类集合
            Set<Class<?>> targetClassSet = map.getValue();
            for (Class<?> targetClass : targetClassSet) {
                // 使用默认的代理器
                MethodInterceptor methodInterceptor = (MethodInterceptor) AspectJAroundAdvice.class.newInstance();
                if (targetMap.containsKey(targetClass)) {
                    targetMap.get(targetClass).add(methodInterceptor);
                } else {
                    List<MethodInterceptor> list = new ArrayList<>();
                    list.add(methodInterceptor);
                    targetMap.put(targetClass, list);
                }
            }
        }
        return targetMap;
    }
}
