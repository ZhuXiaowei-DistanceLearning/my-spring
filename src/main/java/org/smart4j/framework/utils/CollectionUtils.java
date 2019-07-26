package org.smart4j.framework.utils;

import java.util.Map;

public abstract class CollectionUtils {
    public static boolean isEmpty(Map<?, ?> map) {
        return (map == null || map.isEmpty());
    }
}
