package com.sensiblemetrics.api.roadmap.commons.exception;

import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Resource not found {@link RuntimeException} implementation
 */
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ResourceAlreadyExistException extends RuntimeException {
    /**
     * Default explicit serialVersionUID for interoperability
     */
    private static final long serialVersionUID = 5509697613979523813L;

    /**
     * {@link ResourceAlreadyExistException} constructor with initial input message
     *
     * @param message - initial input message {@link String}
     */
    public ResourceAlreadyExistException(final String message) {
        super(message);
    }

    /**
     * {@link ResourceAlreadyExistException} constructor with initial input target {@link Throwable}
     *
     * @param cause - initial input target {@link Throwable}
     */
    public ResourceAlreadyExistException(final Throwable cause) {
        super(cause);
    }

    /**
     * {@link ResourceAlreadyExistException} constructor with initial input {@link String} message and {@link Throwable} target
     *
     * @param message - initial input message {@link String}
     * @param cause   - initial input target {@link Throwable}
     */
    public ResourceAlreadyExistException(final String message, final Throwable cause) {
        super(message, cause);
    }

    /**
     * Returns {@link ResourceAlreadyExistException} by input parameters
     *
     * @param message - initial input description {@link String}
     * @return {@link ResourceAlreadyExistException}
     */
    public static ResourceAlreadyExistException throwError(final String message) {
        throw new ResourceAlreadyExistException(message);
    }
}
