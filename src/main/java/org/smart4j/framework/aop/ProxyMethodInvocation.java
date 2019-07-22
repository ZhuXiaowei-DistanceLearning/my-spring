package org.smart4j.framework.aop;

import org.aopalliance.interceptor.MethodInvocation;

public interface ProxyMethodInvocation extends MethodInvocation {
    /**
     * Return the proxy that this method invocation was made through.
     *
     * @return the original proxy object
     */
    Object getProxy();

    /**
     * Create a clone of this object. If cloning is done before {@code proceed()}
     * is invoked on this object, {@code proceed()} can be invoked once per clone
     * to invoke the joinpoint (and the rest of the advice chain) more than once.
     *
     * @return an invocable clone of this invocation.
     * {@code proceed()} can be called once per clone.
     */
    MethodInvocation invocableClone();

    /**
     * Create a clone of this object. If cloning is done before {@code proceed()}
     * is invoked on this object, {@code proceed()} can be invoked once per clone
     * to invoke the joinpoint (and the rest of the advice chain) more than once.
     *
     * @param arguments the arguments that the cloned invocation is supposed to use,
     *                  overriding the original arguments
     * @return an invocable clone of this invocation.
     * {@code proceed()} can be called once per clone.
     */
    MethodInvocation invocableClone(Object... arguments);

    /**
     * Set the arguments to be used on subsequent invocations in the any advice
     * in this chain.
     *
     * @param arguments the argument array
     */
    void setArguments(Object... arguments);

    /**
     * Add the specified user attribute with the given value to this invocation.
     * <p>Such attributes are not used within the AOP framework itself. They are
     * just kept as part of the invocation object, for use in special interceptors.
     *
     * @param key   the name of the attribute
     * @param value the value of the attribute, or {@code null} to reset it
     */
    void setUserAttribute(String key, Object value);

    /**
     * Return the value of the specified user attribute.
     *
     * @param key the name of the attribute
     * @return the value of the attribute, or {@code null} if not set
     * @see #setUserAttribute
     */
    Object getUserAttribute(String key);
}
