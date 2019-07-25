package org.smart4j.framework.context;

import com.sun.istack.internal.Nullable;
import org.smart4j.framework.beans.factory.config.ConfigurableListableBeanFactory;

public interface ConfigurableApplicationContext extends ApplicationContext {
    void setId(String id);

    void setParent(ApplicationContext parent);

    ConfigurableListableBeanFactory getBeanFactory() throws IllegalStateException;
}
