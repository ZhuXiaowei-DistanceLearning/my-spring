package org.aopalliance.interceptor;

public interface Invocation extends Joinpoint {
    Object[] getArguments();
}
