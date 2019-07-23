package org.smart4j.framework.aop;

import net.sf.cglib.proxy.MethodProxy;
import org.smart4j.framework.aop.aspect.ProxyChain;

import java.lang.reflect.Method;

public interface MethodBeforeAdvice extends BeforeAdvice {
    void before(MethodProxy method, Object[] args, Object target) throws Throwable;
//    void before(ProxyChain invocation) throws Throwable;
}
