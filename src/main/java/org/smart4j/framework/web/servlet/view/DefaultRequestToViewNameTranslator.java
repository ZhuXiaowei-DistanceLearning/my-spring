package org.smart4j.framework.web.servlet.view;

import org.smart4j.framework.web.servlet.RequestToViewNameTranslator;

import javax.servlet.http.HttpServletRequest;

/**
 * 如果用户未提供{ View}或视图名称，则配置的{ RequestToViewNameTranslator}会将当前请求转换为视图名称。
 * 相应的bean名称是“viewNameTranslator”;默认值为{ org.springframework.web.servlet.view.DefaultRequestToViewNameTranslator}。
 */
public class DefaultRequestToViewNameTranslator implements RequestToViewNameTranslator {
    @Override
    public String getViewName(HttpServletRequest request) throws Exception {
        return null;
    }
}
