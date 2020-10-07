package com.sensiblemetrics.api.roadmap.router.service.model.entity;

import lombok.Data;

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
    private T coordinateX;
    /**
     * Coordinate on Y axis
     */
    private T coordinateY;
}
