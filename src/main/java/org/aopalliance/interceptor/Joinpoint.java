package org.aopalliance.interceptor;

public interface Joinpoint {
    Object proceed() throws Throwable;

    Object getThis();
}
