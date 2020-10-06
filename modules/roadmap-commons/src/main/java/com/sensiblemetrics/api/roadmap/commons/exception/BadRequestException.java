package com.sensiblemetrics.api.roadmap.commons.exception;

import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Bad request {@link RuntimeException} implementation
 */
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class BadRequestException extends RuntimeException {
    /**
     * Default explicit serialVersionUID for interoperability
     */
    private static final long serialVersionUID = 8516167662034280227L;

    /**
     * {@link BadRequestException} constructor with initial input message
     *
     * @param message - initial input message {@link String}
     */
    public BadRequestException(final String message) {
        super(message);
    }

    /**
     * {@link BadRequestException} constructor with initial input target {@link Throwable}
     *
     * @param cause - initial input target {@link Throwable}
     */
    public BadRequestException(final Throwable cause) {
        super(cause);
    }

    /**
     * {@link BadRequestException} constructor with initial input {@link String} message and {@link Throwable} target
     *
     * @param message - initial input message {@link String}
     * @param cause   - initial input target {@link Throwable}
     */
    public BadRequestException(final String message, final Throwable cause) {
        super(message, cause);
    }

    /**
     * Returns {@link BadRequestException} by input parameters
     *
     * @param message - initial input description {@link String}
     * @return {@link BadRequestException}
     */
    public static BadRequestException throwError(final String message) {
        throw new BadRequestException(message);
    }
}
