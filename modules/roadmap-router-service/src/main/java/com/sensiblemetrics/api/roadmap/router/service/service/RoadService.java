package com.sensiblemetrics.api.roadmap.router.service.service;

import com.sensiblemetrics.api.roadmap.router.service.model.entity.Road;

import java.util.UUID;

/**
 * {@link Road} base model service
 */
public interface RoadService extends BaseModelService<Road, UUID> {
    /**
     * Returns {@link Iterable} collection of {@link Road}s by input {@link String} name
     *
     * @param name initial input {@link String} city name to fetch by
     * @return {@link Iterable} collection of {@link Road} instances
     */
    Iterable<Road> findRoadsByCityName(final String name);
}
