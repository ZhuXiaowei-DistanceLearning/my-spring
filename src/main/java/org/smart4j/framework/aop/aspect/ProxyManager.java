package org.smart4j.framework.aop.aspect;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
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
    public static <T> T createProxy(final Class<?> targetClass, final List<org.aopalliance.interceptor.MethodInterceptor> proxyList) {
        // 第一个参数为目标类
        // 第二个参数为回调方法
        return (T) Enhancer.create(targetClass, new MethodInterceptor() {
            @Override
            public Object intercept(Object targetObject, Method targetMethod, Object[] methodParams,
                                    MethodProxy methodProxy) throws Throwable {
                return new ReflectiveMethodInvocation(targetClass, targetObject, targetMethod, methodProxy, methodParams, proxyList)
                        .proceeed();
            }
        });
    }
}
