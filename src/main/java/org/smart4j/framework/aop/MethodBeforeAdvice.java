package org.smart4j.framework.aop;

import org.smart4j.framework.aop.aspect.ProxyChain;

import java.lang.reflect.Method;

public interface MethodBeforeAdvice extends BeforeAdvice {
    void before(Method method, Object[] args, Object target) throws Throwable;
//    void before(ProxyChain invocation) throws Throwable;
}
