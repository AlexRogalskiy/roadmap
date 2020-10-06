package com.sensiblemetrics.api.roadmap.router.service.repository.interfaces;

import com.sensiblemetrics.api.roadmap.router.service.model.entity.City;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

/**
 * {@link City} base model repository
 */
public interface CityRepository extends BaseModelRepository<City, UUID> {
    /**
     * Returns {@link CompletableFuture} of {@link City} by input {@link String} name
     *
     * @param name initial input {@link String} city name to fetch by
     * @return {@link City} instance
     */
    CompletableFuture<Optional<City>> findCityByName(final String name);
}
