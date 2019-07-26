package org.smart4j.framework.web.context;

import org.smart4j.framework.context.ConfigurableApplicationContext;
import org.smart4j.framework.utils.ClassUtils;
import org.smart4j.framework.utils.PropsUtils;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextListener;
import java.util.ArrayList;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

public class ContextLoader {
    public static final String CONTEXT_ID_PARAM = "contextId";
    public static final String CONFIG_LOCATION_PARAM = "contextConfigLocation";
    public static final String CONTEXT_CLASS_PARAM = "contextClass";
    public static final String CONTEXT_INITIALIZER_CLASSES_PARAM = "contextInitializerClasses";
    public static final String GLOBAL_INITIALIZER_CLASSES_PARAM = "globalInitializerClasses";
    private static final String INIT_PARAM_DELIMITERS = ",; \t\n";
    private static final String DEFAULT_STRATEGIES_PATH = "ContextLoader.properties";
    private static final Properties defaultStrategies;

    static {
        defaultStrategies = PropsUtils.loadProperties(DEFAULT_STRATEGIES_PATH);
    }

    private static final Map<ClassLoader, WebApplicationContext> currentContextPerThread =
            new ConcurrentHashMap<>(1);

    private static volatile WebApplicationContext currentContext;

    private WebApplicationContext context;

    public ContextLoader() {

    }

    public ContextLoader(WebApplicationContext context) {
        this.context = context;
    }

    public void setContextInitializers() {

    }

    public WebApplicationContext initWebApplicationContext(ServletContext servletContext) {
        if (servletContext.getAttribute((WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE)) != null) {
            long startTime = System.currentTimeMillis();
            try {
                if (this.context == null) {
                    this.context = createWebApplicationContext(servletContext);
                }
                if (this.context instanceof ConfigurableWebApplicationContext) {
                    ConfigurableApplicationContext cwac = (ConfigurableApplicationContext) this.context;
                }
                servletContext.setAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE, this.context);
                ClassLoader cc1 = Thread.currentThread().getContextClassLoader();
                if (cc1 == ContextLoader.class.getClassLoader()) {
                    currentContext = this.context;
                } else if (cc1 != null) {
                    currentContextPerThread.put(cc1, this.context);
                }
                return this.context;
            } catch (Exception e) {
                throw new RuntimeException();
            }
        }
        return null;
    }

    protected WebApplicationContext createWebApplicationContext(ServletContext sc) {
        Class<?> contextClass = determineContextClass(sc);
        if (!ConfigurableWebApplicationContext.class.isAssignableFrom(contextClass)) {
        }
        return null;
    }

    protected Class<?> determineContextClass(ServletContext servletContext) {
        String contextClassName = servletContext.getInitParameter(CONTEXT_CLASS_PARAM);
        if (contextClassName != null) {
            try {
                return ClassUtils.loadClass(contextClassName, false);
            } catch (Exception ex) {
                System.out.println("初始化WebApplicationContext失败");
            }
        } else {
            contextClassName = defaultStrategies.getProperty(WebApplicationContext.class.getName());
            return ClassUtils.loadClass(contextClassName, false);
        }
        return null;
    }
}
