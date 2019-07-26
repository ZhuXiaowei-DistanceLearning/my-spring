package org.smart4j.framework.web.servlet.handler;

import org.smart4j.framework.web.servlet.HandlerInterceptor;
import org.smart4j.framework.web.servlet.HandlerMapping;
import org.smart4j.framework.web.util.UrlPathHelper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class AbstractHandlerMapping implements HandlerMapping {
    private Object defaultHandler;
    private final List<Object> interceptors = new ArrayList<>();
    private final List<HandlerInterceptor> adaptedInterceptors = new ArrayList<>();
    private UrlPathHelper urlPathHelper = new UrlPathHelper();

    public Object getDefaultHandler() {
        return defaultHandler;
    }

    public void setDefaultHandler(Object defaultHandler) {
        this.defaultHandler = defaultHandler;
    }

    public UrlPathHelper getUrlPathHelper() {
        return urlPathHelper;
    }

    public void setUrlPathHelper(UrlPathHelper urlPathHelper) {
        this.urlPathHelper = urlPathHelper;
    }

    public void setInterceptor(Object...interceptors){
        this.interceptors.addAll(Arrays.asList(interceptors));
    }

}
