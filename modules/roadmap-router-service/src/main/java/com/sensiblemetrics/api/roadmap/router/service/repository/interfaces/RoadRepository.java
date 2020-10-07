package com.sensiblemetrics.api.roadmap.router.service.repository.interfaces;

import com.sensiblemetrics.api.roadmap.router.service.model.entity.RoadEntity;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

/**
 * {@link RoadEntity} base model repository
 */
public interface RoadRepository extends BaseModelRepository<RoadEntity, UUID> {
    /**
     * Returns {@link CompletableFuture} of {@link Iterable} collections of {@link RoadEntity}s by {@link String} name
     *
     * @param name initial input {@link String} city name to fetch by
     * @return {@link Iterable} collection of {@link RoadEntity} instances
     */
    CompletableFuture<Iterable<RoadEntity>> findRoadsByCityName(final String name);
}
