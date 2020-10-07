package com.sensiblemetrics.api.roadmap.router.service.service.interfaces;

import com.sensiblemetrics.api.roadmap.router.service.model.entity.CityEntity;

import java.util.Optional;
import java.util.UUID;

/**
 * {@link CityEntity} base model service
 */
public interface CityService extends BaseModelService<CityEntity, UUID> {
    /**
     * Returns {@link CityEntity} by input {@link String} name
     *
     * @param name initial input {@link String} city name to fetch by
     * @return {@link CityEntity} instance
     */
    Optional<CityEntity> findCityByName(final String name);
}
