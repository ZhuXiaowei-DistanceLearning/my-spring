package org.smart4j.framework.web.util;

import javax.servlet.http.HttpServletRequest;

public class UrlPathHelper {
    private static final String WEBSPHERE_URI_ATTRIBUTE = "";
    static volatile Boolean websphereComplianceFlag;
    private boolean alwaysUseFullPath = false;
    private boolean urlDecode = true;
    private boolean removeSemicolonContext = true;

    public boolean isAlwaysUseFullPath() {
        return alwaysUseFullPath;
    }

    public boolean isUrlDecode() {
        return urlDecode;
    }

    public boolean isRemoveSemicolonContext() {
        return removeSemicolonContext;
    }

    public String getPathWithinServletMapping(HttpServletRequest request) {
        return request.getPathInfo();
    }

}
