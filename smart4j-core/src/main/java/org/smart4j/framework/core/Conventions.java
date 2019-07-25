package org.smart4j.framework.core;

import org.smart4j.framework.utils.ClassUtils;
import org.smart4j.framework.utils.Logger;

import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.util.Collection;
import java.util.Iterator;

/**
 * 提供支持整个框架中使用的各种命名和其他约定的方法。
 * 主要供框架内部使用。
 */
public abstract class Conventions {
    private static Logger logger = new Logger(Conventions.class);
    private static final String PLURAL_SUFFIX = "List";

    public static String getVariableName(Object value) {
        Class<?> valueClass;
        boolean pluralize = true;

        // 如果该对象是数组
        if (value.getClass().isArray()) {
            // getComponentType返回类数组的组件类型的Class。
            // 返回类数组的组件类型的Class。 如果此类不表示数组类，则此方法返回null。
            valueClass = value.getClass().getComponentType();
            pluralize = true;
        } else if (value instanceof Collection) {
            // 如果该对象是Collection类的实例
            Collection<?> collection = (Collection<?>) value;
            if (collection.isEmpty()) {
                logger.catchException("collection为空");
                throw new RuntimeException();
            }
            Object valueToCheck = peekAhead(collection);
//            getClass

        }
        return null;
    }

    private static String pluralize(String name) {
        return name + PLURAL_SUFFIX;
    }

    private static <E> E peekAhead(Collection<E> collection) {
        Iterator<E> it = collection.iterator();
        if (!it.hasNext()) {
            throw new IllegalStateException(
                    "Unable to peek ahead in non-empty collection - no element found");
        }
        E value = it.next();
        if (value == null) {
            throw new IllegalStateException(
                    "Unable to peek ahead in non-empty collection - only null element found");
        }
        return value;
    }
}
