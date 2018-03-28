package com.ice.annotation;

/**
 * <pre>
 *   请求类型的方法
 * </pre>
 *
 * @version 1.0
 */
public enum HttpAction {

    GET, POST;

    public static HttpAction fromValue(String value) {
        if (GET.name().equalsIgnoreCase(value)) {
            return GET;
        }
        return POST;
    }
}

