package com.sensiblemetrics.api.roadmap.commons.exception;

import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Endpoint configuration {@link RuntimeException} implementation
 */
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class RouterConfigurationException extends RuntimeException {
    /**
     * Default explicit serialVersionUID for interoperability
     */
    private static final long serialVersionUID = 5120429116335637195L;

    /**
     * Initializes {@link RouterConfigurationException} using the given {@code String} errorMessage
     *
     * @param message The message describing the exception
     */
    public RouterConfigurationException(final String message) {
        super(message);
    }

    /**
     * Initializes {@link RouterConfigurationException} using the given {@code Throwable} type
     *
     * @param type - initial input {@link Throwable}
     */
    public RouterConfigurationException(final Throwable type) {
        super(type);
    }

    /**
     * Invalid mapping exception constructor with initial input {@link Throwable}
     *
     * @param message - initial input {@link String} message
     * @param type    - initial input {@link Throwable} type
     */
    public RouterConfigurationException(final String message, final Throwable type) {
        super(message, type);
    }

    /**
     * Returns {@link RouterConfigurationException} by input parameters
     *
     * @param message - initial input description {@link String}
     * @return {@link RouterConfigurationException}
     */
    public static RouterConfigurationException throwError(final String message) {
        throw new RouterConfigurationException(message);
    }
}
