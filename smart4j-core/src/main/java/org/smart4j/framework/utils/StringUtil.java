package org.smart4j.framework.utils;

public final class StringUtil {
    public static String TransferClassName(String className) {
        return className.substring(0, 1).toLowerCase() + className.substring(1);
    }
}
