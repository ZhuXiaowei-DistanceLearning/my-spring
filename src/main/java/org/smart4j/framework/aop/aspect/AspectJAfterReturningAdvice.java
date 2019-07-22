package org.smart4j.framework.aop.aspect;

import org.smart4j.framework.aop.AfterReturningAdvice;
import org.smart4j.framework.aop.AfterAdvice;

import java.io.Serializable;
import java.lang.reflect.Method;

public class AspectJAfterReturningAdvice implements AfterReturningAdvice, Serializable, AfterAdvice {
    @Override
    public void afterReturning(Object returnValue, Method method, Object[] args, Object target) throws Throwable {

    }
}
