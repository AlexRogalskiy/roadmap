package com.sensiblemetrics.api.roadmap.router.service.controller.interfaces;

import com.sensiblemetrics.api.roadmap.router.service.model.domain.Response;
import com.sensiblemetrics.api.roadmap.router.service.model.entity.CityEntity;

import java.util.UUID;

/**
 * {@link CityEntity} base model controller
 */
public interface CityController extends BaseModelController<CityEntity, UUID> {
    /**
     * Returns {@link Response} with {@link CityEntity} by input {@link String} name
     *
     * @param name initial input {@link String} city name to fetch by
     * @return {@link Response} with {@link CityEntity} body
     */
    Response<CityEntity> findCityByName(final String name);
}
