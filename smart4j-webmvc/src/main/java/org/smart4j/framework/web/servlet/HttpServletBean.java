package org.smart4j.framework.web.servlet;

import com.fasterxml.jackson.databind.deser.impl.PropertyValue;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import java.util.HashSet;
import java.util.Set;

/**
 * 将Servlet中配置的参数设置到相应的属性
 */
public abstract class HttpServletBean extends HttpServlet {

    private final Set<String> requiredProperties = new HashSet<>(4);

    protected final void addRequiredProperties(String property) {
        requiredProperties.add(property);
    }

    @Override
    public final void init() throws ServletException {
        initServletBean();
    }

    protected void initServletBean() throws ServletException {

    }

    @Override
    public String getServletName() {
        return (getServletInfo() != null ? getServletConfig().getServletName() : null);
    }

}
