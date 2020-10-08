package com.sensiblemetrics.api.roadmap.router.service.service.interfaces;

import com.sensiblemetrics.api.roadmap.router.service.model.entity.RoadModelEntity;

import java.util.UUID;

/**
 * {@link RoadModelEntity} base model service
 */
public interface RoadModelService extends BaseModelService<RoadModelEntity, UUID> {
    /**
     * Returns {@link Iterable} collection of {@link RoadModelEntity}s by input {@link String} name
     *
     * @param name initial input {@link String} city name to fetch by
     * @return {@link Iterable} collection of {@link RoadModelEntity} instances
     */
    Iterable<RoadModelEntity> findRoadsByCityName(final String name);
}
