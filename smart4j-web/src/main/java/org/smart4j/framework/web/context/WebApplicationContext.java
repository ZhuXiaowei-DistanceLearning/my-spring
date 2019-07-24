package org.smart4j.framework.web.context;

import org.smart4j.framework.context.ApplicationContext;

import javax.servlet.ServletContext;

/**
 * 用于为Web应用程序提供配置的界面。
 * 这在应用程序运行时是只读的，但如果实现支持，则可以重新加载。
 * 此接口将{ getServletContext（）}方法添加到通用ApplicationContext接口，并定义一个众所周知的应用程序属性名称 根上下文必须在引导过程中绑定。
 * 与通用应用程序上下文一样，Web应用程序上下文是分层的。
 * 每个应用程序都有一个根上下文，而应用程序中的每个servlet（包括MVC框架中的调度程序servlet）都有自己的子上下文。
 * 除了标准的应用程序上下文生命周期功能外，WebApplicationContext实现还需要检测{ ServletContextAware} bean并相应地调用{ setServletContext}方法。
 */
public interface WebApplicationContext extends ApplicationContext {
    String ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE = WebApplicationContext.class.getName() + ".ROOT";

    /**
     * Scope identifier for request scope: "request".
     * Supported in addition to the standard scopes "singleton" and "prototype".
     */
    String SCOPE_REQUEST = "request";

    /**
     * Scope identifier for session scope: "session".
     * Supported in addition to the standard scopes "singleton" and "prototype".
     */
    String SCOPE_SESSION = "session";

    /**
     * Scope identifier for the global web application scope: "application".
     * Supported in addition to the standard scopes "singleton" and "prototype".
     */
    String SCOPE_APPLICATION = "application";

    /**
     * Name of the ServletContext environment bean in the factory.
     *
     * @see javax.servlet.ServletContext
     */
    String SERVLET_CONTEXT_BEAN_NAME = "servletContext";

    /**
     * Name of the ServletContext/PortletContext init-params environment bean in the factory.
     * <p>Note: Possibly merged with ServletConfig/PortletConfig parameters.
     * ServletConfig parameters override ServletContext parameters of the same name.
     *
     * @see javax.servlet.ServletContext#getInitParameterNames()
     * @see javax.servlet.ServletContext#getInitParameter(String)
     * @see javax.servlet.ServletConfig#getInitParameterNames()
     * @see javax.servlet.ServletConfig#getInitParameter(String)
     */
    String CONTEXT_PARAMETERS_BEAN_NAME = "contextParameters";

    /**
     * Name of the ServletContext/PortletContext attributes environment bean in the factory.
     *
     * @see javax.servlet.ServletContext#getAttributeNames()
     * @see javax.servlet.ServletContext#getAttribute(String)
     */
    String CONTEXT_ATTRIBUTES_BEAN_NAME = "contextAttributes";

    ServletContext getServletContext();

}
