package org.aopalliance.interceptor;

// 相当于proxy接口
public interface MethodInterceptor extends Interceptor {
    Object invoke(MethodInvocation invocation) throws Throwable;
}
