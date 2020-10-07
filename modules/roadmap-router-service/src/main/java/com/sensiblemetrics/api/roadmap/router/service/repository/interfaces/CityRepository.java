package com.sensiblemetrics.api.roadmap.router.service.repository.interfaces;

import com.sensiblemetrics.api.roadmap.router.service.model.entity.CityModelEntity;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

/**
 * {@link CityModelEntity} base model repository
 */
public interface CityRepository extends BaseModelRepository<CityModelEntity, UUID> {
    /**
     * Returns {@link CompletableFuture} of {@link CityModelEntity} by input {@link String} name
     *
     * @param name initial input {@link String} city name to fetch by
     * @return {@link CityModelEntity} instance
     */
    CompletableFuture<Optional<CityModelEntity>> findCityByName(final String name);
}
