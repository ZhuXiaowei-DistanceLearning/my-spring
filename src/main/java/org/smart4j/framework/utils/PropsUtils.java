package org.smart4j.framework.utils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public final class PropsUtils {
    /**
     * 首先得到配置文件的位置的输入流
     *
     * @param fileName
     * @return
     */
    public static Properties loadProperties(String fileName) {
        Properties properties = null;
        InputStream is = null;
        // 首先获取配置文件的位置
        try {
            is = Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName);
            if (is == null) {
                throw new FileNotFoundException(fileName + "不存在");
            }
            properties = new Properties();
            properties.load(is);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    System.out.println("配置文件流关闭异常");
                }
            }
        }
        return properties;
    }

    public static String getString(Properties properties, String key) {
        return getString(properties, key, "");
    }

    public static String getString(Properties properties, String key, String defaultValue) {
        String value = defaultValue;
        if (properties.containsKey(key)) {
            value = properties.getProperty(key);
        }
        return value;
    }

    public static Integer getInt(Properties properties, String key) {
        return getInt(properties, key);
    }

    public static Integer getInt(Properties properties, String key, int defaultValue) {
        int value = defaultValue;
        if (properties.containsKey(key)) {
            value = CastUtils.castInt(properties.get(key));
        }
        return value;
    }

    /**
     * 获取布尔类型
     */
    public static boolean getBoolean(Properties props, String key) {
        return getBoolean(props, key, false);
    }

    public static boolean getBoolean(Properties props, String key, boolean defaultValue) {
        boolean value = defaultValue;
        if (props.containsKey(key)) {
            value = CastUtils.castBoolean(props.getProperty(key));
        }
        return value;
    }
}
