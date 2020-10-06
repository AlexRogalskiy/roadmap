package com.sensiblemetrics.api.roadmap.commons.exception;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/**
 * Service unavailable {@link RuntimeException} implementation
 */
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ServiceUnavailableException extends RuntimeException {
    /**
     * Default explicit serialVersionUID for interoperability
     */
    private static final long serialVersionUID = -7693119675913317655L;

    private static final String SOURCE = "UNDEFINED";

    @Getter
    private final String errorSource;

    /**
     * {@link ServiceUnavailableException} constructor with initial input description
     *
     * @param message - initial input description {@link String}
     */
    public ServiceUnavailableException(final String message) {
        this(message, SOURCE);
    }

    /**
     * {@link ServiceUnavailableException} constructor with initial input {@link String} description and error {@link String} source delegate
     *
     * @param message     - initial input exception description {@link String}
     * @param errorSource - initial input exception source {@link String}
     */
    public ServiceUnavailableException(final String message, final String errorSource) {
        super(message);
        this.errorSource = errorSource;
    }

    /**
     * Returns {@link ServiceUnavailableException} by input parameters
     *
     * @param message - initial input description {@link String}
     * @return {@link ServiceUnavailableException}
     */
    public static ServiceUnavailableException throwError(final String message) {
        throw new ServiceUnavailableException(message);
    }
}
