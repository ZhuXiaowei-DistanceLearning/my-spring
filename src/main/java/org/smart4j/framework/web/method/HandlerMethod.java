package org.smart4j.framework.web.method;

import org.smart4j.framework.beans.factory.BeanFactory;
import org.smart4j.framework.http.HttpStatus;

import java.lang.reflect.Method;

/**
 *   *封装有关组成的处理程序方法的信息
 *   * { #getMethod（）方法}和{ #getBean（）bean}。
 *   *提供方便的方法访问参数，方法返回值，
 *   *方法注释等
 *  *
 *   * <p>可以使用bean实例或bean名称创建类
 *   *（例如lazy-init bean，prototype bean）。 使用{ #createWithResolvedBean（）}
 *   *获取已解析bean实例的{ HandlerMethod}实例
 *   *通过相关的{ BeanFactory}。
 */
public class HandlerMethod {
    private Object bean;
    private Class<?> controllerClass;
    private BeanFactory beanFactory;
    private Method method;
//    private final Method bridgedMethod;

    private HttpStatus responseStatus;
    private String responseStatusReason;
    private HandlerMethod resolvedFromHandlerMethod;

    public HandlerMethod(Class<?> controllerClass, Method method) {
        this.controllerClass = controllerClass;
        this.method = method;
    }

    public HandlerMethod(Object bean, Method method) {
        this.bean = bean;
        this.beanFactory = null;
        this.method = method;
    }

    public HandlerMethod(Object bean, String methodName, Class<?>... parameterTypes) {
        this.bean = bean;
        this.beanFactory = null;

    }

    public Object getBean() {
        return bean;
    }

    public BeanFactory getBeanFactory() {
        return beanFactory;
    }

    public Method getMethod() {
        return method;
    }

    public HttpStatus getResponseStatus() {
        return responseStatus;
    }

    public String getResponseStatusReason() {
        return responseStatusReason;
    }

    public HandlerMethod getResolvedFromHandlerMethod() {
        return resolvedFromHandlerMethod;
    }
}
