package org.smart4j.framework.web.servlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 允许自定义处理程序执行链的工作流接口。
 *  *应用程序可以注册任意数量的现有或自定义拦截器
 *  *对于某些处理程序组，添加常见的预处理行为
 *  *无需修改每个处理程序实现。
 *  *
 *  * <p>在适当的HandlerAdapter之前调用HandlerInterceptor
 *  *触发处理程序本身的执行。可以使用这种机制
 *  *对于大范围的预处理方面，例如用于授权检查，
 *  *或常见的处理程序行为，如区域设置或主题更改。它的主要目的
 *  *是允许分解重复的处理程序代码。
 *  *
 *  * <p>在异步处理场景中，处理程序可以在a中执行
 *  *主线程退出时单独的线程，无需渲染或调用
 *  * {@code postHandle}和{@code afterCompletion}回调。何时并发
 *  *处理程序执行完成，请求被调度回来
 *  *继续渲染模型，并调用此合同的所有方法
 *  *再次。有关更多选项和详细信息，请参
 *  * {@code org.springframework.web.servlet.AsyncHandlerInterceptor}
 *  *
 *  * <p>通常根据HandlerMapping bean定义一个拦截器链，
 *  *分享其粒度。能够应用某种拦截链
 *  *对于一组处理程序，需要通过一个处理程序映射所需的处理程序
 *  * HandlerMapping bean。拦截器本身被定义为bean
 *  *在应用程序上下文中，由映射bean定义引用
 *  *通过其“拦截器”属性（在XML中：＆lt; list＆gt; of＆lt; ref＆gt;）。
 *  *
 *  * <p> HandlerInterceptor基本上类似于Servlet过滤器，但在
 *  *与后者相比，它只允许使用选项进行自定义预处理
 *  *禁止执行处理程序本身，以及自定义后处理。
 *  *过滤器功能更强大，例如它们允许交换请求
 *  *和传递链的响应对象。请注意一个过滤器
 *  *在web.xml中配置，在应用程序上下文中是HandlerInterceptor。
 *  *
 *  * <p>作为基本准则，与细粒度处理程序相关的预处理任务是
 *  * HandlerInterceptor实现的候选者，尤其是因子分解
 *  *常见的处理程序代码和授权检查。另一方面，过滤器
 *  *非常适合请求内容和查看内容处理，如multipart
 *  *表格和GZIP压缩。这通常显示何时需要映射
 *  *过滤到某些内容类型（例如图像）或所有请求。
 */
public interface HandlerInterceptor {
    default boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        return true;
    }

    default void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    default void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
