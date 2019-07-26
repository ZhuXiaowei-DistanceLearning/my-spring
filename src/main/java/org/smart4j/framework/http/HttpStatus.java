package org.smart4j.framework.http;

import com.sun.istack.internal.Nullable;

public enum HttpStatus {
    // 1xx Informational

    /**
     * {code 100 Continue}.
     */
    CONTINUE(100, "Continue"),
    /**
     * {code 101 Switching Protocols}.
     */
    SWITCHING_PROTOCOLS(101, "Switching Protocols"),
    /**
     * {code 102 Processing}.
     */
    PROCESSING(102, "Processing"),
    /**
     * {code 103 Checkpoint}.
     * resumable POST/PUT HTTP requests in HTTP/1.0</a>
     */
    CHECKPOINT(103, "Checkpoint"),

    // 2xx Success

    /**
     * {code 200 OK}.
     */
    OK(200, "OK"),
    /**
     * {code 201 Created}.
     */
    CREATED(201, "Created"),
    /**
     * {code 202 Accepted}.
     */
    ACCEPTED(202, "Accepted"),
    /**
     * {code 203 Non-Authoritative Information}.
     */
    NON_AUTHORITATIVE_INFORMATION(203, "Non-Authoritative Information"),
    /**
     * {code 204 No Content}.
     */
    NO_CONTENT(204, "No Content"),
    /**
     * {code 205 Reset Content}.
     */
    RESET_CONTENT(205, "Reset Content"),
    /**
     * {code 206 Partial Content}.
     */
    PARTIAL_CONTENT(206, "Partial Content"),
    /**
     * {code 207 Multi-Status}.
     */
    MULTI_STATUS(207, "Multi-Status"),
    /**
     * {code 208 Already Reported}.
     */
    ALREADY_REPORTED(208, "Already Reported"),
    /**
     * {code 226 IM Used}.
     */
    IM_USED(226, "IM Used"),

    // 3xx Redirection

    /**
     * {code 300 Multiple Choices}.
     */
    MULTIPLE_CHOICES(300, "Multiple Choices"),
    /**
     * {code 301 Moved Permanently}.
     */
    MOVED_PERMANENTLY(301, "Moved Permanently"),
    /**
     * {code 302 Found}.
     */
    FOUND(302, "Found"),
    /**
     * {code 302 Moved Temporarily}.
     * deprecated in favor of { #FOUND} which will be returned from {code HttpStatus.valueOf(302)}
     */
    @Deprecated
    MOVED_TEMPORARILY(302, "Moved Temporarily"),
    /**
     * {code 303 See Other}.
     */
    SEE_OTHER(303, "See Other"),
    /**
     * {code 304 Not Modified}.
     */
    NOT_MODIFIED(304, "Not Modified"),
    /**
     * {code 305 Use Proxy}.
     * deprecated due to security concerns regarding in-band configuration of a proxy
     */
    @Deprecated
    USE_PROXY(305, "Use Proxy"),
    /**
     * {code 307 Temporary Redirect}.
     */
    TEMPORARY_REDIRECT(307, "Temporary Redirect"),
    /**
     * {code 308 Permanent Redirect}.
     */
    PERMANENT_REDIRECT(308, "Permanent Redirect"),

    // --- 4xx Client Error ---

    /**
     * {code 400 Bad Request}.
     */
    BAD_REQUEST(400, "Bad Request"),
    /**
     * {code 401 Unauthorized}.
     */
    UNAUTHORIZED(401, "Unauthorized"),
    /**
     * {code 402 Payment Required}.
     */
    PAYMENT_REQUIRED(402, "Payment Required"),
    /**
     * {code 403 Forbidden}.
     */
    FORBIDDEN(403, "Forbidden"),
    /**
     * {code 404 Not Found}.
     */
    NOT_FOUND(404, "Not Found"),
    /**
     * {code 405 Method Not Allowed}.
     */
    METHOD_NOT_ALLOWED(405, "Method Not Allowed"),
    /**
     * {code 406 Not Acceptable}.
     */
    NOT_ACCEPTABLE(406, "Not Acceptable"),
    /**
     * {code 407 Proxy Authentication Required}.
     */
    PROXY_AUTHENTICATION_REQUIRED(407, "Proxy Authentication Required"),
    /**
     * {code 408 Request Timeout}.
     */
    REQUEST_TIMEOUT(408, "Request Timeout"),
    /**
     * {code 409 Conflict}.
     */
    CONFLICT(409, "Conflict"),
    /**
     * {code 410 Gone}.
     * HTTP/1.1: Semantics and Content, section 6.5.9</a>
     */
    GONE(410, "Gone"),
    /**
     * {code 411 Length Required}.
     * HTTP/1.1: Semantics and Content, section 6.5.10</a>
     */
    LENGTH_REQUIRED(411, "Length Required"),
    /**
     * {code 412 Precondition failed}.
     * HTTP/1.1: Conditional Requests, section 4.2</a>
     */
    PRECONDITION_FAILED(412, "Precondition Failed"),
    /**
     * {code 413 Payload Too Large}.
     * HTTP/1.1: Semantics and Content, section 6.5.11</a>
     * since 4.1
     */
    PAYLOAD_TOO_LARGE(413, "Payload Too Large"),
    /**
     * {code 413 Request Entity Too Large}.
     * deprecated in favor of { #PAYLOAD_TOO_LARGE} which will be
     * returned from {code HttpStatus.valueOf(413)}
     */
    @Deprecated
    REQUEST_ENTITY_TOO_LARGE(413, "Request Entity Too Large"),
    /**
     * {code 414 URI Too Long}.
     * <p>
     * HTTP/1.1: Semantics and Content, section 6.5.12</a>
     * since 4.1
     */
    URI_TOO_LONG(414, "URI Too Long"),
    /**
     * {code 414 Request-URI Too Long}.
     * <p>
     * deprecated in favor of { #URI_TOO_LONG} which will be returned from {code HttpStatus.valueOf(414)}
     */
    @Deprecated
    REQUEST_URI_TOO_LONG(414, "Request-URI Too Long"),
    /**
     * {code 415 Unsupported Media Type}.
     * <p>
     * HTTP/1.1: Semantics and Content, section 6.5.13</a>
     */
    UNSUPPORTED_MEDIA_TYPE(415, "Unsupported Media Type"),
    /**
     * {code 416 Requested Range Not Satisfiable}.
     */
    REQUESTED_RANGE_NOT_SATISFIABLE(416, "Requested range not satisfiable"),
    /**
     * {code 417 Expectation Failed}.
     * <p>
     * HTTP/1.1: Semantics and Content, section 6.5.14</a>
     */
    EXPECTATION_FAILED(417, "Expectation Failed"),
    /**
     * {code 418 I'm a teapot}.
     */
    I_AM_A_TEAPOT(418, "I'm a teapot"),
    /**
     * deprecated See
     * WebDAV Draft Changes</a>
     */
    @Deprecated
    INSUFFICIENT_SPACE_ON_RESOURCE(419, "Insufficient Space On Resource"),
    /**
     * deprecated See
     * WebDAV Draft Changes</a>
     */
    @Deprecated
    METHOD_FAILURE(420, "Method Failure"),
    /**
     * WebDAV Draft Changes</a>
     */
    @Deprecated
    DESTINATION_LOCKED(421, "Destination Locked"),
    /**
     * {code 422 Unprocessable Entity}.
     */
    UNPROCESSABLE_ENTITY(422, "Unprocessable Entity"),
    /**
     * {code 423 Locked}.
     */
    LOCKED(423, "Locked"),
    /**
     * {code 424 Failed Dependency}.
     */
    FAILED_DEPENDENCY(424, "Failed Dependency"),
    /**
     * {code 426 Upgrade Required}.
     */
    UPGRADE_REQUIRED(426, "Upgrade Required"),
    /**
     * {code 428 Precondition Required}.
     */
    PRECONDITION_REQUIRED(428, "Precondition Required"),
    /**
     * {code 429 Too Many Requests}.
     */
    TOO_MANY_REQUESTS(429, "Too Many Requests"),
    /**
     * {code 431 Request Header Fields Too Large}.
     */
    REQUEST_HEADER_FIELDS_TOO_LARGE(431, "Request Header Fields Too Large"),
    /**
     * {code 451 Unavailable For Legal Reasons}.
     * <p>
     * An HTTP Status Code to Report Legal Obstacles</a>
     * since 4.3
     */
    UNAVAILABLE_FOR_LEGAL_REASONS(451, "Unavailable For Legal Reasons"),

    // --- 5xx Server Error ---

    /**
     * {code 500 Internal Server Error}.
     */
    INTERNAL_SERVER_ERROR(500, "Internal Server Error"),
    /**
     * {code 501 Not Implemented}.
     */
    NOT_IMPLEMENTED(501, "Not Implemented"),
    /**
     * {code 502 Bad Gateway}.
     */
    BAD_GATEWAY(502, "Bad Gateway"),
    /**
     * {code 503 Service Unavailable}.
     */
    SERVICE_UNAVAILABLE(503, "Service Unavailable"),
    /**
     * {code 504 Gateway Timeout}.
     */
    GATEWAY_TIMEOUT(504, "Gateway Timeout"),
    /**
     * {code 505 HTTP Version Not Supported}.
     */
    HTTP_VERSION_NOT_SUPPORTED(505, "HTTP Version not supported"),
    /**
     * {code 506 Variant Also Negotiates}
     */
    VARIANT_ALSO_NEGOTIATES(506, "Variant Also Negotiates"),
    /**
     * {code 507 Insufficient Storage}
     */
    INSUFFICIENT_STORAGE(507, "Insufficient Storage"),
    /**
     * {code 508 Loop Detected}
     */
    LOOP_DETECTED(508, "Loop Detected"),
    /**
     * {code 509 Bandwidth Limit Exceeded}
     */
    BANDWIDTH_LIMIT_EXCEEDED(509, "Bandwidth Limit Exceeded"),
    /**
     * {code 510 Not Extended}
     */
    NOT_EXTENDED(510, "Not Extended"),
    /**
     * {code 511 Network Authentication Required}.
     */
    NETWORK_AUTHENTICATION_REQUIRED(511, "Network Authentication Required");


    private final int value;

    private final String reasonPhrase;


    HttpStatus(int value, String reasonPhrase) {
        this.value = value;
        this.reasonPhrase = reasonPhrase;
    }


    /**
     * Return the integer value of this status code.
     */
    public int value() {
        return this.value;
    }

    /**
     * Return the reason phrase of this status code.
     */
    public String getReasonPhrase() {
        return this.reasonPhrase;
    }

    /**
     * Whether this status code is in the HTTP series
     * { org.springframework.http.HttpStatus.Series#INFORMATIONAL}.
     * This is a shortcut for checking the value of { #series()}.
     */
    public boolean is1xxInformational() {
        return Series.INFORMATIONAL.equals(series());
    }

    /**
     * Whether this status code is in the HTTP series
     * { org.springframework.http.HttpStatus.Series#SUCCESSFUL}.
     * This is a shortcut for checking the value of { #series()}.
     */
    public boolean is2xxSuccessful() {
        return Series.SUCCESSFUL.equals(series());
    }

    /**
     * Whether this status code is in the HTTP series
     * { org.springframework.http.HttpStatus.Series#REDIRECTION}.
     * This is a shortcut for checking the value of { #series()}.
     */
    public boolean is3xxRedirection() {
        return Series.REDIRECTION.equals(series());
    }


    /**
     * Whether this status code is in the HTTP series
     * { org.springframework.http.HttpStatus.Series#CLIENT_ERROR}.
     * This is a shortcut for checking the value of { #series()}.
     */
    public boolean is4xxClientError() {
        return Series.CLIENT_ERROR.equals(series());
    }

    /**
     * Whether this status code is in the HTTP series
     * { org.springframework.http.HttpStatus.Series#SERVER_ERROR}.
     * This is a shortcut for checking the value of { #series()}.
     */
    public boolean is5xxServerError() {
        return Series.SERVER_ERROR.equals(series());
    }

    /**
     * Whether this status code is in the HTTP series
     * { org.springframework.http.HttpStatus.Series#CLIENT_ERROR} or
     * { org.springframework.http.HttpStatus.Series#SERVER_ERROR}.
     * This is a shortcut for checking the value of { #series()}.
     */
    public boolean isError() {
        return is4xxClientError() || is5xxServerError();
    }

    /**
     * Returns the HTTP status series of this status code.
     * <p>
     * see HttpStatus.Series
     */
    public Series series() {
        return Series.valueOf(this);
    }

    /**
     * Return a string representation of this status code.
     */
    @Override
    public String toString() {
        return Integer.toString(this.value);
    }


    /**
     * Return the enum constant of this type with the specified numeric value.
     * <p>
     * param statusCode the numeric value of the enum to be returned
     * return the enum constant with the specified numeric value
     * throws IllegalArgumentException if this enum has no constant for the specified numeric value
     */
    public static HttpStatus valueOf(int statusCode) {
        HttpStatus status = resolve(statusCode);
        if (status == null) {
            throw new IllegalArgumentException("No matching constant for [" + statusCode + "]");
        }
        return status;
    }


    /**
     * Resolve the given status code to an {code HttpStatus}, if possible.
     * <p>
     * param statusCode the HTTP status code (potentially non-standard)
     * return the corresponding {code HttpStatus}, or {code null} if not found
     * since 5.0
     */
    public static HttpStatus resolve(int statusCode) {
        for (HttpStatus status : values()) {
            if (status.value == statusCode) {
                return status;
            }
        }
        return null;
    }


    /**
     * Enumeration of HTTP status series.
     * <p>Retrievable via { HttpStatus#series()}.
     */
    public enum Series {

        // 信息
        INFORMATIONAL(1),
        // 成功
        SUCCESSFUL(2),
        // 重定向
        REDIRECTION(3),
        // 客户端错误
        CLIENT_ERROR(4),
        // 服务器错误
        SERVER_ERROR(5);

        private final int value;

        Series(int value) {
            this.value = value;
        }

        /**
         * Return the integer value of this status series. Ranges from 1 to 5.
         */
        public int value() {
            return this.value;
        }

        public static Series valueOf(int status) {
            int seriesCode = status / 100;
            for (Series series : values()) {
                if (series.value == seriesCode) {
                    return series;
                }
            }
            throw new IllegalArgumentException("该 [" + status + "]没有相匹配的状态");
        }

        public static Series valueOf(HttpStatus status) {
            return valueOf(status.value);
        }
    }
}
