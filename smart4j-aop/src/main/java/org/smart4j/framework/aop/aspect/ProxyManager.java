package org.smart4j.framework.aop.aspect;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import org.smart4j.framework.aop.annotation.Around;
import org.smart4j.framework.aop.framework.ReflectiveMethodInvocation;

import java.lang.reflect.Method;
import java.util.List;

public class ProxyManager {
    @SuppressWarnings("unchecked")
    /**
     *
     * @param targetClass 目标类
     * @param proxyList 代理对象
     * @return
     */
    public static <T> T createProxy(final Class<?> targetClass, final List<Object> proxyList) {
        // 第一个参数为目标类
        // 第二个参数为回调方法
        return (T) Enhancer.create(targetClass, new MethodInterceptor() {
            @Override
            public Object intercept(Object targetObject, Method targetMethod, Object[] methodParams,
                                    MethodProxy methodProxy) throws Throwable {
                return new ReflectiveMethodInvocation(targetClass, targetObject, targetMethod, methodProxy, methodParams, proxyList)
                        .proceed();
            }
        });
    }

    @SuppressWarnings("unchecked")
    public static <T> T getProxy(final Class<?> cls, final List<Object> proxyList) {
        return (T) Enhancer.create(cls, new MethodInterceptor() {
            @Override
            public Object intercept(Object targetObject, Method targetMethod, Object[] methodParams, MethodProxy methodProxy) throws Throwable {
                // 代理对象的方法
                Method[] methods = proxyList.get(0).getClass().getDeclaredMethods();
                Object o = proxyList.get(0).getClass().newInstance();
                // 生成切面代理对象
                for (Method method1 : methods) {
                    if (method1.isAnnotationPresent(Around.class)) {
                        ReflectiveMethodInvocation invocation = new ReflectiveMethodInvocation(cls, targetObject, targetMethod, methodProxy, methodParams, proxyList);
                        System.out.println(invocation.toString());
                        return method1.invoke(o, invocation);
                    }
                }
                System.out.println(cls.getName());
                return new ReflectiveMethodInvocation(cls, targetObject, targetMethod, methodProxy, methodParams, proxyList).proceed();
            }
        });
    }
}
