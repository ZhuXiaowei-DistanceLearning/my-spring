package org.smart4j.framework.context;

import org.smart4j.framework.beans.factory.HierarchicalBeanFactory;
import org.smart4j.framework.beans.factory.ListableBeanFactory;

public interface ApplicationContext extends HierarchicalBeanFactory, ListableBeanFactory {
    String getId();

    String getApplicationName();

    String getDisplayName();

    ApplicationContext getParent();

    long getStartUpDate();
}
