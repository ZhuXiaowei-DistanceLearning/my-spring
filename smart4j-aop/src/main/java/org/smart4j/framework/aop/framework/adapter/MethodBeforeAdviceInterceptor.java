package org.smart4j.framework.aop.framework.adapter;

import org.aopalliance.interceptor.MethodInterceptor;
import org.aopalliance.interceptor.MethodInvocation;
import org.smart4j.framework.aop.BeforeAdvice;
import org.smart4j.framework.aop.MethodBeforeAdvice;

public class MethodBeforeAdviceInterceptor implements MethodInterceptor, BeforeAdvice {
    private final MethodBeforeAdvice methodBeforeAdvice;

    public MethodBeforeAdviceInterceptor(MethodBeforeAdvice methodBeforeAdvice) {
        this.methodBeforeAdvice = methodBeforeAdvice;
    }

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        this.methodBeforeAdvice.before(invocation.getMethod(), invocation.getArguments(), invocation.getThis());
        return invocation.proceed();
    }
}
