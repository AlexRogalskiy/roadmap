package com.sensiblemetrics.api.roadmap.router.service.controller.interfaces;

import com.sensiblemetrics.api.roadmap.router.service.model.domain.Response;
import com.sensiblemetrics.api.roadmap.router.service.model.entity.CityModelEntity;

import java.util.UUID;

/**
 * {@link CityModelEntity} base model controller
 */
public interface CityController extends BaseModelController<CityModelEntity, UUID> {
    /**
     * Returns {@link Response} with {@link CityModelEntity} by input {@link String} name
     *
     * @param name initial input {@link String} city name to fetch by
     * @return {@link Response} with {@link CityModelEntity} body
     */
    Response<CityModelEntity> findCityByName(final String name);
}
