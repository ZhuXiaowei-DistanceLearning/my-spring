package org.smart4j.framework.web.servlet;

/**
 * 处理器映射器执行链
 */

import com.sun.istack.internal.Nullable;
import org.apache.commons.collections4.CollectionUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * 处理程序执行链，由处理程序对象和任何处理程序拦截器组成。
 * 由HandlerMapping的{@link HandlerMapping＃getHandler}方法返回。
 */
public class HandlerExecutionChain {
    private final Object handler;

    // 自定义处理程序执行链的工作流接口数组
    private HandlerInterceptor[] interceptors;

    // 自定义处理程序执行链的工作流接口集合
    private List<HandlerInterceptor> interceptorList;

    // 代理链索引
    private int interceptorIndex = -1;

    public HandlerExecutionChain(Object handler) {
        this(handler, (HandlerInterceptor[]) null);
    }

    public HandlerExecutionChain(Object handler, HandlerInterceptor... interceptors) {
        if (handler instanceof HandlerExecutionChain) {
            HandlerExecutionChain originalChain = (HandlerExecutionChain) handler;
            this.handler = originalChain.getHandler();
            this.interceptorList = new ArrayList<>();
        } else {
            this.handler = handler;
            this.interceptors = interceptors;
        }
    }

    public Object getHandler() {
        return handler;
    }

    public void addInterceptor(HandlerInterceptor interceptor) {
        initInterceptorList().add(interceptor);
    }

    public void addInterceptors(HandlerInterceptor... interceptors) {
        if (interceptors.length != 0) {
        }
    }

    private List<HandlerInterceptor> initInterceptorList() {
        if (this.interceptorList == null) {
            // 如果为空，就进行初始化
            this.interceptorList = new ArrayList<>();
            if (interceptors != null) {

            }
        }
        this.interceptors = null;
        return this.interceptorList;
    }

    public HandlerInterceptor[] getInterceptors() {
        if (this.interceptors == null && this.interceptorList != null) {
            this.interceptors = this.interceptorList.toArray(new HandlerInterceptor[0]);
        }
        return this.interceptors;
    }

    public void applyPostHandle(HttpServletRequest processedRequest, HttpServletResponse response, ModelAndView mv) throws Exception {
        HandlerInterceptor[] interceptors = getInterceptors();
        for (int i = interceptors.length - 1; i >= 0; i--) {
            HandlerInterceptor interceptor = interceptors[i];
            interceptor.postHandle(processedRequest, response, this.handler, mv);
        }
    }

    /**
     * 应用注册拦截器的preHandle方法。
     * @param request
     * @param response
     * @return {@code true}如果执行链应继续下一个拦截器或处理程序本身。 否则，DispatcherServlet假定这个拦截器已经处理了响应本身。}
     * @throws Exception
     */
    boolean applyPreHandle(HttpServletRequest request, HttpServletResponse response) throws Exception {
        HandlerInterceptor[] interceptors = getInterceptors();
        for (int i = 0; i < interceptors.length; i++) {
            HandlerInterceptor interceptor = interceptors[i];
            if (!interceptor.preHandle(request, response, this.handler)) {
                return false;
            }
            this.interceptorIndex = i;
        }
        return true;
    }

    void triggerAfterCompletion(HttpServletRequest request, HttpServletResponse response, @Nullable Exception ex)
            throws Exception {
    }

    void applyAfterConcurrentHandlingStarted(HttpServletRequest request, HttpServletResponse response) {
    }
}
