package org.aopalliance.interceptor;

import java.lang.reflect.Method;

// 相当于proxyChain
public interface MethodInvocation extends Invocation {
    Method getMethod();
}
