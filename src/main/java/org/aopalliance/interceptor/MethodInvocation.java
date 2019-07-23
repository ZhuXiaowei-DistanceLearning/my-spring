package org.aopalliance.interceptor;

import net.sf.cglib.proxy.MethodProxy;

// 相当于proxyChain
public interface MethodInvocation extends Invocation {
    MethodProxy getMethod();
}
