package org.smart4j.framework.aop.framework;

import net.sf.cglib.proxy.MethodProxy;
import org.aopalliance.interceptor.MethodInterceptor;
import org.aopalliance.interceptor.MethodInvocation;
import org.smart4j.framework.aop.ProxyMethodInvocation;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

// proxyChain的实现类
public class ReflectiveMethodInvocation implements ProxyMethodInvocation {
    // 目标实现类
    private final Class<?> targetClass;
    // 目标方法
    private final Method targetMethod;
    // 目标对象
    private final Object targetObject;
    // 目标参数
    private final Object[] methodParams;
    // 代理集合
    private List<MethodInterceptor> proxyList = new ArrayList<>();
    // 代理方法
    private final MethodProxy methodProxy;
    // 代理索引
    private int proxyIndex = 0;

    public ReflectiveMethodInvocation(Class<?> targetClass, Object targetObject, Method targetMethod, MethodProxy methodProxy, Object[] methodParams, List<MethodInterceptor> proxyList) {
        this.targetClass = targetClass;
        this.targetMethod = targetMethod;
        this.targetObject = targetObject;
        this.methodParams = methodParams;
        this.methodProxy = methodProxy;
        this.proxyList = proxyList;
    }

    @Override
    // TODO 现在的问题是，AspectJAroundAdvice没有执行
    public Object proceed() throws Throwable {
        Object methodResult = null;
        // TODO 代理对象有问题
        if (proxyIndex < proxyList.size()) {
            methodResult = proxyList.get(proxyIndex++).invoke(this);
        }
        return methodResult;
    }

    @Override
    public Object getProxy() {
        return null;
    }

    @Override
    public MethodInvocation invocableClone() {
        return null;
    }

    @Override
    public MethodInvocation invocableClone(Object... arguments) {
        return null;
    }

    @Override
    public void setArguments(Object... arguments) {

    }

    @Override
    public void setUserAttribute(String key, Object value) {

    }

    @Override
    public Object getUserAttribute(String key) {
        return null;
    }

    @Override
    public MethodProxy getMethod() {
        return this.methodProxy;
    }

    @Override
    public Object[] getArguments() {
        return this.methodParams;
    }

    @Override
    public Object getThis() {
        return this.targetObject;
    }
}
