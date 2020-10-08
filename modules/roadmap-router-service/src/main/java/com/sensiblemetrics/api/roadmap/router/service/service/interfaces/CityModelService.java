package com.sensiblemetrics.api.roadmap.router.service.service.interfaces;

import com.sensiblemetrics.api.roadmap.router.service.model.entity.CityModelEntity;

import java.util.Optional;
import java.util.UUID;

/**
 * {@link CityModelEntity} base model service
 */
public interface CityModelService extends BaseModelService<CityModelEntity, UUID> {
    /**
     * Returns {@link CityModelEntity} by input {@link String} name
     *
     * @param name initial input {@link String} city name to fetch by
     * @return {@link CityModelEntity} instance
     */
    Optional<CityModelEntity> findCityByName(final String name);
}
