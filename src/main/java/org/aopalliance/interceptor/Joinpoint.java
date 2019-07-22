package org.aopalliance.interceptor;

public interface Joinpoint {
    Object proceeed() throws Throwable;

    Object getThis();
}
