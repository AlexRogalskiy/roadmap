package com.sensiblemetrics.api.roadmap.commons.exception;

import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Configuration {@link RuntimeException} implementation
 */
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ConfigurationException extends RuntimeException {
    /**
     * Default explicit serialVersionUID for interoperability
     */
    private static final long serialVersionUID = 5120429116335637195L;

    /**
     * Initializes {@link ConfigurationException} using the given {@code String} errorMessage
     *
     * @param message The message describing the exception
     */
    public ConfigurationException(final String message) {
        super(message);
    }

    /**
     * Initializes {@link ConfigurationException} using the given {@code Throwable} type
     *
     * @param type - initial input {@link Throwable}
     */
    public ConfigurationException(final Throwable type) {
        super(type);
    }

    /**
     * Invalid mapping exception constructor with initial input {@link Throwable}
     *
     * @param message - initial input {@link String} message
     * @param type    - initial input {@link Throwable} type
     */
    public ConfigurationException(final String message, final Throwable type) {
        super(message, type);
    }

    /**
     * Returns {@link ConfigurationException} by input parameters
     *
     * @param message - initial input description {@link String}
     * @return {@link ConfigurationException}
     */
    public static ConfigurationException throwError(final String message) {
        throw new ConfigurationException(message);
    }
}
