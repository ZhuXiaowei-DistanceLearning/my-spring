package org.smart4j.framework.aop.aspect;

import net.sf.cglib.proxy.MethodProxy;
import org.aopalliance.aop.Advice.Advice;
import org.aopalliance.interceptor.MethodInterceptor;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * 代理执行类
 */
public class ProxyChain {
    // 目标实现类
    private final Class<?> targetClass;
    // 目标方法
    private final Method targetMethod;
    // 目标对象
    private final Object targetObject;
    // 目标参数
    private final Object[] methodParams;
    // 代理集合
    private final List<MethodInterceptor> proxyList = new ArrayList<>();
    // 代理方法
    private final MethodProxy methodProxy;
    // 代理索引
    private int proxyIndex = 0;

    public ProxyChain(Class<?> targetClass, Method targetMethod, Object targetObject, Object[] methodParams, MethodProxy methodProxy) {
        this.targetClass = targetClass;
        this.targetMethod = targetMethod;
        this.targetObject = targetObject;
        this.methodParams = methodParams;
        this.methodProxy = methodProxy;
    }

    public Class<?> getTargetClass() {
        return targetClass;
    }

    public Method getTargetMethod() {
        return targetMethod;
    }

    public Object[] getMethodParams() {
        return methodParams;
    }

    public Object doProxyChain() throws Throwable {
        /*Object methodResult;
        if (proxyIndex < proxyList.size()) {
            methodResult = proxyList.get(proxyIndex++).invoke(this);
        } else {
            methodResult = methodProxy.invokeSuper(targetObject, methodParams);
        }
        return methodResult;*/
        return null;
    }
}
