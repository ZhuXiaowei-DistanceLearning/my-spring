package org.smart4j.framework.web.servlet;

import javax.servlet.http.HttpServletRequest;

/**
 * 如果用户未提供{ View}或视图名称，则配置的{ RequestToViewNameTranslator}会将当前请求转换为视图名称。
 */
public interface RequestToViewNameTranslator {

    String getViewName(HttpServletRequest request) throws Exception;
}
