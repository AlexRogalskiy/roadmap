package com.sensiblemetrics.api.roadmap.router.service.repository.interfaces;

import com.sensiblemetrics.api.roadmap.router.service.model.entity.CityEntity;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

/**
 * {@link CityEntity} base model repository
 */
public interface CityRepository extends BaseModelRepository<CityEntity, UUID> {
    /**
     * Returns {@link CompletableFuture} of {@link CityEntity} by input {@link String} name
     *
     * @param name initial input {@link String} city name to fetch by
     * @return {@link CityEntity} instance
     */
    CompletableFuture<Optional<CityEntity>> findCityByName(final String name);
}
