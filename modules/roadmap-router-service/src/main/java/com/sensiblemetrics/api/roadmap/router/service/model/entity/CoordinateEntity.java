package com.sensiblemetrics.api.roadmap.router.service.model.entity;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * Coordinate model
 *
 * @param <T> type of configurable coordinate
 */
@Data
public class CoordinateEntity<T> implements Serializable {
    /**
     * Default explicit serialVersionUID for interoperability
     */
    private static final long serialVersionUID = -203806241727168980L;

    /**
     * Coordinate on X axis
     */
    @NotNull(message = "{model.entity.coordinate.position-x.notNull}")
    private T positionX;
    /**
     * Coordinate on Y axis
     */
    @NotNull(message = "{model.entity.coordinate.position-y.notNull}")
    private T positionY;
}
