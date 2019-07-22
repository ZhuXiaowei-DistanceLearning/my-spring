package org.smart4j.framework.aop;

import org.aopalliance.aop.Advice.Advice;

import java.lang.reflect.Method;

public interface AfterReturningAdvice extends Advice {
    void afterReturning(Object returnValue, Method method, Object[] args, Object target) throws Throwable;
}
