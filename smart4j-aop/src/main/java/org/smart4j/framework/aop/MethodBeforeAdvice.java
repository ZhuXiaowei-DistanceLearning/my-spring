package org.smart4j.framework.aop;

import net.sf.cglib.proxy.MethodProxy;

public interface MethodBeforeAdvice extends BeforeAdvice {
    void before(MethodProxy method, Object[] args, Object target) throws Throwable;
//    void before(ProxyChain invocation) throws Throwable;
}
