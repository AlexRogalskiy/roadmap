package com.sensiblemetrics.api.roadmap.commons.exception;

import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Invalid format {@link RuntimeException} implementation
 */
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class InvalidFormatException extends RuntimeException {
    /**
     * Default explicit serialVersionUID for interoperability
     */
    private static final long serialVersionUID = 8256935783691876393L;

    /**
     * {@link InvalidFormatException} constructor with initial input message
     *
     * @param message - initial input message {@link String}
     */
    public InvalidFormatException(final String message) {
        super(message);
    }

    /**
     * {@link InvalidFormatException} constructor with initial input target {@link Throwable}
     *
     * @param cause - initial input target {@link Throwable}
     */
    public InvalidFormatException(final Throwable cause) {
        super(cause);
    }

    /**
     * {@link InvalidFormatException} constructor with initial input {@link String} message and {@link Throwable} target
     *
     * @param message - initial input message {@link String}
     * @param cause   - initial input target {@link Throwable}
     */
    public InvalidFormatException(final String message, final Throwable cause) {
        super(message, cause);
    }

    /**
     * Returns {@link InvalidFormatException} by input parameters
     *
     * @param message - initial input description {@link String}
     * @return {@link InvalidFormatException}
     */
    public static InvalidFormatException throwError(final String message) {
        throw new InvalidFormatException(message);
    }
}
