package org.smart4j.framework.helper;

import org.smart4j.framework.constant.ConfigConstant;
import org.smart4j.framework.utils.PropsUtils;

import java.util.Properties;

public final class ConfigHelper {
    private static final Properties CONFIG_PROPS = PropsUtils.loadProperties(ConfigConstant.CONFIG_FILE);

    /**
     * 获取jdbc用户名
     *
     * @return
     */
    public static String getJdbcUsername() {
        return PropsUtils.getString(CONFIG_PROPS, ConfigConstant.JDBC_USERNAME);
    }

    /**
     * 获取jdbc密码
     *
     * @return
     */
    public static String getJdbcPassword() {
        return PropsUtils.getString(CONFIG_PROPS, ConfigConstant.JDBC_PASSWORD);
    }

    /**
     * 获取应用基础包名
     *
     * @return
     */
    public static String getAppBasePackage() {
        return PropsUtils.getString(CONFIG_PROPS, ConfigConstant.APP_BASE_PACKAGE);
    }

    /**
     * 获取应用jsp路径
     *
     * @return
     */
    public static String getAppJspPath() {
        return PropsUtils.getString(CONFIG_PROPS, ConfigConstant.APP_JSP_PATH, "/WEB-INF/view/");
    }

    /**
     * 获取应用静态资源路径
     *
     * @return
     */
    public static String getAppAssetPath() {
        return PropsUtils.getString(CONFIG_PROPS, ConfigConstant.APP_ASSET_PATH, "/asset/");
    }

    /**
     * 获取应用文件上传限制
     */
    public static int getAppUploadLimit() {
        return PropsUtils.getInt(CONFIG_PROPS, ConfigConstant.APP_UPLOAD_LIMIT);
    }

    /**
     * 获取包扫描路径
     */
    public static String getComponentScanPackage(){
        return PropsUtils.getString(CONFIG_PROPS,ConfigConstant.COMPONENT_SCAN);
    }
}
