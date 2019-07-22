package org.smart4j.framework.helper;

import org.aopalliance.interceptor.MethodInterceptor;
import org.smart4j.framework.aop.annotation.Aspect;
import org.smart4j.framework.aop.aspect.AspectJAroundAdvice;
import org.smart4j.framework.aop.framework.ReflectiveMethodInvocation;
import org.smart4j.framework.utils.ClassUtils;

import java.util.*;

public final class AopHelper {
    /**
     * 普通切面类代理
     */
    private static void addAspectProxy(Map<Class<?>, Set<Class<?>>> proxyMap) throws Exception {
        Set<Class<?>> classSet = ClassHelper.getClassSet();
        for (Class<?> cls : classSet) {
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
        return proxyMap;
    }

    /**
     * 创建目标类与代理对象之间的映射关系
     *
     * @param proxyMap
     * @return
     */
    public static Map<Class<?>, List<MethodInterceptor>> createTargetMap(Map<Class<?>, Set<Class<?>>> proxyMap) throws Exception {
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
