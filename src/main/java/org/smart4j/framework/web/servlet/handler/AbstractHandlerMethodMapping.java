package org.smart4j.framework.web.servlet.handler;

import org.smart4j.framework.helper.ClassHelper;
import org.smart4j.framework.http.HttpMethod;
import org.smart4j.framework.web.bind.annotation.RequestMapping;
import org.smart4j.framework.web.bind.annotation.RequestMethod;
import org.smart4j.framework.web.method.HandlerMethod;
import org.smart4j.framework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;
import javax.xml.ws.spi.http.HttpHandler;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Set;

public abstract class AbstractHandlerMethodMapping<T> extends AbstractHandlerMapping {
    @Override
    protected HandlerMethod getHandlerInternal(HttpServletRequest request) throws Exception {
        String pathInfo = request.getPathInfo();
        HandlerMethod handlerMethod = lookupHandlerMethod(pathInfo, request);
        return handlerMethod;
    }

    protected HandlerMethod lookupHandlerMethod(String lookupPath, HttpServletRequest request) {
        // 得到路径和请求
        String requestMethod = request.getMethod();
        Set<Class<?>> controllereClass = ClassHelper.getClassSetByController();
        for (Class<?> cls : controllereClass) {
            Method[] methods = cls.getMethods();
            for (Method method : methods) {
                Annotation[] annotations = method.getAnnotations();
                for (Annotation annotation : annotations) {
                    if (annotation.annotationType().isAnnotationPresent(RequestMapping.class)) {
                        Class<? extends Annotation> type = annotation.annotationType();
                        RequestMethod[] requestMethods = type.getAnnotation(RequestMapping.class).method();
                    }
                }
            }
        }
        return null;
    }
}