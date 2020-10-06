package com.sensiblemetrics.api.roadmap.commons.exception;

import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Service operation {@link RuntimeException} implementation
 */
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ServiceOperationException extends RuntimeException {
    /**
     * Default explicit serialVersionUID for interoperability
     */
    private static final long serialVersionUID = 8542851383807954620L;

    /**
     * {@link ServiceOperationException} constructor with initial input message
     *
     * @param message - initial input message {@link String}
     */
    public ServiceOperationException(final String message) {
        super(message);
    }

    /**
     * {@link ServiceOperationException} constructor with initial input {@link Throwable}
     *
     * @param cause - initial input cause target {@link Throwable}
     */
    public ServiceOperationException(final Throwable cause) {
        super(cause);
    }

    /**
     * {@link ServiceOperationException} constructor with initial input message and {@link Throwable}
     *
     * @param message - initial input message {@link String}
     * @param cause   - initial input cause target {@link Throwable}
     */
    public ServiceOperationException(final String message, final Throwable cause) {
        super(message, cause);
    }

    /**
     * Returns {@link ServiceOperationException} by input parameters
     *
     * @param message - initial input description {@link String}
     * @return {@link ServiceOperationException}
     */
    public static ServiceOperationException throwError(final String message) {
        throw new ServiceOperationException(message);
    }
}
