package org.smart4j.framework.web.servlet;

import java.util.Locale;

/**
 * 视图解析器：定位视图资源
 */
public interface ViewResolver {
    View resolveViewName(String viewName) throws Exception;
}
