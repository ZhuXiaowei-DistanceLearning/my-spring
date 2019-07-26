package org.smart4j.framework.aop.aspect;

import org.aopalliance.interceptor.MethodInterceptor;
import org.aopalliance.interceptor.MethodInvocation;
import org.smart4j.framework.aop.utils.DataBaseHelper;

public class AspectJTransactionalAdvice extends AbstractAspectJAdvice implements MethodInterceptor {
    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        Object invokeAdviceMethod = null;
        try {
            DataBaseHelper.beginTransaction();
            invokeAdviceMethod = invokeAdviceMethod(invocation.getMethod(), invocation.getArguments(), invocation.getThis());
            DataBaseHelper.commitTransaction();
        } catch (Exception e) {
            DataBaseHelper.rollbackTransaction();
        }
        return invokeAdviceMethod;
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
