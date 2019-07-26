package org.smart4j.framework.web.request;

import com.sun.istack.internal.Nullable;

import javax.servlet.ServletRequestAttributeEvent;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;

public class ServletWebRequest  {
    private static final String ETAG = "ETag";

    private static final String IF_MODIFIED_SINCE = "If-Modified-Since";

    private static final String IF_UNMODIFIED_SINCE = "If-Unmodified-Since";

    private static final String IF_NONE_MATCH = "If-None-Match";

    private static final String LAST_MODIFIED = "Last-Modified";

    private static final List<String> SAFE_METHODS = Arrays.asList("GET", "HEAD");

    private static final String[] DATE_FORMATS = new String[] {
            "EEE, dd MMM yyyy HH:mm:ss zzz",
            "EEE, dd-MMM-yy HH:mm:ss zzz",
            "EEE MMM dd HH:mm:ss yyyy"
    };

    public ServletWebRequest(HttpServletRequest request) {

    }

    public ServletWebRequest(HttpServletRequest request, @Nullable HttpServletResponse response) {

    }

}
