package com.slvh.common.constant;

/**
 * The type Security constants.
 */
public class SecurityConstants {
    /**
     * The constant TOKEN_PREFIX.
     */
    public static final String TOKEN_PREFIX = "Bearer ";
    /**
     * The constant HEADER_STRING.
     */
    public static final String HEADER_STRING = "Authorization";

    /**
     * The constant WHITE_LIST_URL.
     */
    public static final String[] WHITE_LIST_URL = {"/auth/create", "/auth/login/{username}"};
}
