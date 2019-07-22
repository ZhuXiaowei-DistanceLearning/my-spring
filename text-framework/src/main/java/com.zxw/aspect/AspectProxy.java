package com.zxw.aspect;

import org.smart4j.framework.aop.annotation.After;
import org.smart4j.framework.aop.annotation.Aspect;
import org.smart4j.framework.aop.annotation.Before;
import org.smart4j.framework.aop.framework.ReflectiveMethodInvocation;
import org.smart4j.framework.stereotype.Component;

@Aspect("com.zxw.web")
@Component
public class AspectProxy {

    public Object around(ReflectiveMethodInvocation rm) {
        before();
        Object proceeed = null;
        try {
            proceeed = rm.proceeed();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        after();
        return proceeed;
    }

    @Before
    public void before() {
        System.out.println("前置增强执行了");
    }

    @After
    public void after() {
        System.out.println("后置增强执行了");
    }
}
