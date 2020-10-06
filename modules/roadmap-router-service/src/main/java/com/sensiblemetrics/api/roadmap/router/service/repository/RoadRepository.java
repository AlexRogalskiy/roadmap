package com.sensiblemetrics.api.roadmap.router.service.repository;

import com.sensiblemetrics.api.roadmap.router.service.model.entity.Road;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

/**
 * {@link Road} base model repository
 */
public interface RoadRepository extends BaseModelRepository<Road, UUID> {
    /**
     * Returns {@link CompletableFuture} of {@link Iterable} collections of {@link Road}s by {@link String} name
     *
     * @param name initial input {@link String} city name to fetch by
     * @return {@link Iterable} collection of {@link Road} instances
     */
    CompletableFuture<Iterable<Road>> findRoadsByCityName(final String name);
}
