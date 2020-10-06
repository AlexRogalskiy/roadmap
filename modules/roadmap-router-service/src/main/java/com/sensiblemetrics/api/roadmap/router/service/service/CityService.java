package com.sensiblemetrics.api.roadmap.router.service.service;

import com.sensiblemetrics.api.roadmap.router.service.model.entity.City;

import java.util.Optional;
import java.util.UUID;

/**
 * {@link City} base model service
 */
public interface CityService extends BaseModelService<City, UUID> {
    /**
     * Returns {@link City} by input {@link String} name
     *
     * @param name initial input {@link String} city name to fetch by
     * @return {@link City} instance
     */
    Optional<City> findCityByName(final String name);
}
