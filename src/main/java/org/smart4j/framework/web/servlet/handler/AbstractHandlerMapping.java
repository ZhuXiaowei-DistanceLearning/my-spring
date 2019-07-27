package org.smart4j.framework.web.servlet.handler;

import org.smart4j.framework.web.servlet.HandlerExecutionChain;
import org.smart4j.framework.web.servlet.HandlerInterceptor;
import org.smart4j.framework.web.servlet.HandlerMapping;
import org.smart4j.framework.web.util.UrlPathHelper;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class AbstractHandlerMapping implements HandlerMapping {
    private Object defaultHandler;
    private final List<Object> interceptors = new ArrayList<>();
    private final List<HandlerInterceptor> adaptedInterceptors = new ArrayList<>();
    private UrlPathHelper urlPathHelper = new UrlPathHelper();

    @Override
    /**
     * 责任链模式中，通过该抽象类实现该接口，并对接口中的方法做具体的实现
     * 两种方式：
     * 1.通过将参数设置成代理类
     * 2.将返回值设置为代理类
     * 该方法采用的是第二种
     */
    public final HandlerExecutionChain getHandler(HttpServletRequest request) throws Exception {
        Object handler = getHandlerInternal(request);
        if (handler == null) {
            handler = getDefaultHandler();
        }
        if (handler == null) {
            return null;
        }
        // bean name or resolved handler?
        if (handler instanceof String) {
            String handlerName = (String) handler;
        }
        HandlerExecutionChain chain = getHandlerExecutionChain(handler, request);
        return chain;
    }

    protected abstract Object getHandlerInternal(HttpServletRequest request) throws Exception;

    /**
     * 为给定的处理程序构建{ HandlerExecutionChain}，包括
     * 适用的拦截器。
     * <p>默认实现构建标准{ HandlerExecutionChain}
     * 使用给定的处理程序，处理程序映射的常见拦截器，以及任何
     * { MappedInterceptor}匹配当前请求的URL。拦截器
     * 按照注册顺序添加。子类可以覆盖它
     * 为了扩展/重新排列拦截器列表。
     * <p> <b>注意：</ b>传入的处理程序对象可以是原始处理程序或
     * 预建{ HandlerExecutionChain}。这种方法应该处理这些
     * 两个明确的案例，要么建立一个新的{ HandlerExecutionChain}
     * 或扩展现有链。
     * <p>只需在自定义子类中添加拦截器，请考虑调用
     * { super.getHandlerExecutionChain（handler，request）}和调用
     * { HandlerExecutionChain＃addInterceptor}在返回的链对象上。
     *
     * @return HandlerExecutionChain（从不{ null}）
     * @param处理程序已解析的处理程序实例（从不{ null}）
     * @param请求当前的HTTP请求 #getAdaptedInterceptors（）
     */
    protected HandlerExecutionChain getHandlerExecutionChain(Object handler, HttpServletRequest request) {
        HandlerExecutionChain chain = handler instanceof HandlerExecutionChain ? (HandlerExecutionChain) handler : new HandlerExecutionChain(handler);
        for (HandlerInterceptor interceptor : this.adaptedInterceptors) {
            if (interceptor instanceof MappedInterceptor) {
                MappedInterceptor mappedInterceptor = (MappedInterceptor) interceptor;
                chain.addInterceptor(mappedInterceptor.getInterceptor());
            } else {
                chain.addInterceptor(interceptor);
            }
        }
        return chain;
    }

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

    public void setInterceptor(Object... interceptors) {
        this.interceptors.addAll(Arrays.asList(interceptors));
    }

}
