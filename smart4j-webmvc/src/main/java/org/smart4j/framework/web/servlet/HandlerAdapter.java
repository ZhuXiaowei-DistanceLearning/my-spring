package org.smart4j.framework.web.servlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 处理器适配器：运行处理器
 */
public interface HandlerAdapter {
    /**
     * 给定一个处理程序实例，返回此{ HandlerAdapter}是否可以支持它。
     * 典型的HandlerAdapters将根据处理程序类型做出决定。 HandlerAdapters通常每个只支持一种处理程序类型。
     * 典型的实现：{ return（handler instanceof MyHandler）;
     *
     * @param handler 处理程序处理程序对象，用于检查@return此对象是否可以使用给定的处理程序
     * @return
     */
    boolean support(Object handler);

    /**
     * 使用给定的处理程序来处理此请求。
     * 所需的工作流程可能差异很大。
     *
     * @param request
     * @param response
     * @param handler  处理程序使用的处理程序。
     *                 此对象必须先前已传递到此接口的{ supports}方法，该方法必须具有此方法
     *                 返回{@code true}。
     * @return
     * @throws Exception
     */
    ModelAndView handle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception;

    /**
     * 与HttpServlet的{ getLastModified}方法相同的合同。
     * 如果处理程序类中没有支持，则可以简单地返回-1。
     *
     * @param request
     * @param handler
     * @return
     */
    long getLastModified(HttpServletRequest request, Object handler);
}
