package org.aopalliance.interceptor;

import org.smart4j.framework.aop.aspect.ProxyChain;

// 相当于proxy接口
public interface MethodInterceptor extends Interceptor {
    Object invoke(MethodInvocation invocation) throws Throwable;
}
