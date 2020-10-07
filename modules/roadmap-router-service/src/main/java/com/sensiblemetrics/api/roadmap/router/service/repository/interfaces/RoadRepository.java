package com.sensiblemetrics.api.roadmap.router.service.repository.interfaces;

import com.sensiblemetrics.api.roadmap.router.service.model.entity.RoadModelEntity;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

/**
 * {@link RoadModelEntity} base model repository
 */
public interface RoadRepository extends BaseModelRepository<RoadModelEntity, UUID> {
    /**
     * Returns {@link CompletableFuture} of {@link Iterable} collections of {@link RoadModelEntity}s by {@link String} name
     *
     * @param name initial input {@link String} city name to fetch by
     * @return {@link Iterable} collection of {@link RoadModelEntity} instances
     */
    CompletableFuture<Iterable<RoadModelEntity>> findRoadsByCityName(final String name);
}
