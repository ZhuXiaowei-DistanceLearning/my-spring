package org.smart4j.framework.web.context;


import org.smart4j.framework.context.ConfigurableApplicationContext;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;

public interface ConfigurableWebApplicationContext extends WebApplicationContext, ConfigurableApplicationContext {
    String APPLICATION_CONTEXT_ID_PREFIX = WebApplicationContext.class.getName() + ":";
    String SERVLET_CONFIG_BEAN_NAME = "servletConfig";

    void setServletContext(ServletContext servletContext);

    void setServletConfig(ServletConfig servletConfig);

    ServletConfig getServletConfig();

    void setNamespace(String namespace);

    String getNamespace();

    void setConfigLocation(String configLocation);

    void setConfigLocations(String... configLocations);

    String[] getConfigLocations();
}
