package org.smart4j.framework.aop.aspect;

import org.aopalliance.interceptor.MethodInterceptor;
import org.aopalliance.interceptor.MethodInvocation;

public class AspectJTransactionalAdvice extends AbstractAspectJAdvice implements MethodInterceptor {
    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        System.out.println("事务开启");
        return invokeAdviceMethod(invocation.getMethod(),invocation.getArguments(),invocation.getThis());
    }

    @Override
    public boolean isBeforeAdvice() {
        return false;
    }

    @Override
    public boolean isAfterAdvice() {
        return false;
    }
}
