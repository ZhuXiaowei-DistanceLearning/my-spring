package org.smart4j.framework.context;

import org.smart4j.framework.beans.factory.config.ConfigurableListableBeanFactory;
import org.smart4j.framework.context.ApplicationContext;

public interface ConfigurableApplicationContext extends ApplicationContext {
    void setId(String id);

    void setParent(ApplicationContext parent);

    ConfigurableListableBeanFactory getBeanFactory() throws IllegalStateException;
}
