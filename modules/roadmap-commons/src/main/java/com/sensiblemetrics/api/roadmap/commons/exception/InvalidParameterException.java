package com.sensiblemetrics.api.roadmap.commons.exception;

import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Invalid parameter {@link RuntimeException} implementation
 */
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class InvalidParameterException extends RuntimeException {
    /**
     * Default explicit serialVersionUID for interoperability
     */
    private static final long serialVersionUID = 847426346837200760L;

    /**
     * {@link InvalidParameterException} constructor with initial input message
     *
     * @param message - initial input message {@link String}
     */
    public InvalidParameterException(final String message) {
        super(message);
    }

    /**
     * {@link InvalidParameterException} constructor with initial input target {@link Throwable}
     *
     * @param cause - initial input target {@link Throwable}
     */
    public InvalidParameterException(final Throwable cause) {
        super(cause);
    }

    /**
     * {@link InvalidParameterException} constructor with initial input {@link String} message and {@link Throwable} target
     *
     * @param message - initial input message {@link String}
     * @param cause   - initial input target {@link Throwable}
     */
    public InvalidParameterException(final String message, final Throwable cause) {
        super(message, cause);
    }

    /**
     * Returns {@link InvalidParameterException} by input parameters
     *
     * @param message - initial input description {@link String}
     * @return {@link InvalidParameterException}
     */
    public static InvalidParameterException throwError(final String message) {
        throw new InvalidParameterException(message);
    }
}
