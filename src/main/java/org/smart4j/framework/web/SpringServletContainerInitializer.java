package org.smart4j.framework.web;

import javax.servlet.ServletContainerInitializer;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import java.util.Set;

/**
 * ServletContainerInitializer 该类实现web.xml
 * SpringServletContainerInitializer 这个类会遍历WebApplicationInitializer接口的实现类，加载它所配置的内容
 */
public class SpringServletContainerInitializer implements ServletContainerInitializer {
    @Override
    public void onStartup(Set<Class<?>> set, ServletContext servletContext) throws ServletException {

    }
}
