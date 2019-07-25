package org.smart4j.framework.web.context.support;

import org.smart4j.framework.web.context.WebApplicationContext;

import javax.servlet.ServletContext;

public abstract class WebApplicationContextUtils {
    public static WebApplicationContext getWebApplicationContext(ServletContext sc) {
        return getWebApplicationContext(sc, WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE);
    }

    public static WebApplicationContext getWebApplicationContext(ServletContext sc, String attrName) {
        Object attr = sc.getAttribute(attrName);
        if (attr == null) {
            return null;
        }
        if (attr instanceof RuntimeException) {
            throw (RuntimeException) attr;
        }

        if (attr instanceof Error) {
            throw (Error) attr;
        }
        return (WebApplicationContext) attr;
    }
}
