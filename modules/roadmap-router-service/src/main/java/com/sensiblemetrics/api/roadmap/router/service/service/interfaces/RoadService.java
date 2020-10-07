package com.sensiblemetrics.api.roadmap.router.service.service.interfaces;

import com.sensiblemetrics.api.roadmap.router.service.model.entity.RoadEntity;

import java.util.UUID;

/**
 * {@link RoadEntity} base model service
 */
public interface RoadService extends BaseModelService<RoadEntity, UUID> {
    /**
     * Returns {@link Iterable} collection of {@link RoadEntity}s by input {@link String} name
     *
     * @param name initial input {@link String} city name to fetch by
     * @return {@link Iterable} collection of {@link RoadEntity} instances
     */
    Iterable<RoadEntity> findRoadsByCityName(final String name);
}
