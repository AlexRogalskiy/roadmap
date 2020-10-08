package com.sensiblemetrics.api.roadmap.commons.enumeration;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static java.util.Arrays.stream;

/**
 * Default enumeration of HTTP request methods
 */
public enum HttpMethod {
    GET, HEAD, POST, PUT, PATCH, DELETE, OPTIONS, TRACE;

    private static final Map<String, HttpMethod> mappings;

    static {
        final Map<String, HttpMethod> includes = new HashMap<>();
        stream(values()).forEach(value -> includes.put(value.name(), value));
        mappings = Collections.unmodifiableMap(includes);
    }

    /**
     * Resolve the given method value to an {@code HttpMethod}.
     *
     * @param method the method value as a String
     * @return the corresponding {@code HttpMethod}, or {@code null} if not found
     */
    public static HttpMethod resolve(final String method) {
        return Optional.ofNullable(method).map(mappings::get).orElse(null);
    }

    /**
     * Determine whether this {@code HttpMethod} matches the given
     * method value.
     *
     * @param method the method value as a String
     * @return {@code true} if it matches, {@code false} otherwise
     * @since 4.2.4
     */
    public boolean matches(final String method) {
        return (this == resolve(method));
    }
}
