package org.smart4j.framework.aop.aspect;

import net.sf.cglib.proxy.MethodProxy;
import org.smart4j.framework.aop.MethodBeforeAdvice;

import java.io.Serializable;
import java.lang.reflect.Method;

/**
 * 前置通知
 */
public class AspectJMethodBeforeAdvice extends AbstractAspectJAdvice implements MethodBeforeAdvice, Serializable {

    @Override
    public boolean isBeforeAdvice() {
        return true;
    }

    @Override
    public boolean isAfterAdvice() {
        return false;
    }

    @Override
    public void before(MethodProxy method, Object[] args, Object target) throws Throwable {
        invokeAdviceMethod(method,args,target);
    }
}
