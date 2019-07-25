package org.smart4j.framework.web.servlet;

import javax.servlet.http.HttpServletRequest;

/**
 * 由注解@RequestMapping提供URI和其他配置
 * 定位控制器响应方法
 */
public interface HandlerMapping {
    HandlerExecutionChain getHandler(HttpServletRequest request);
}
