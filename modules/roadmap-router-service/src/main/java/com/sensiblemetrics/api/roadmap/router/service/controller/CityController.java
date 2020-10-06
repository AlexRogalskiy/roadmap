package com.sensiblemetrics.api.roadmap.router.service.controller;

import com.sensiblemetrics.api.roadmap.router.service.model.domain.Response;
import com.sensiblemetrics.api.roadmap.router.service.model.entity.City;

import java.util.UUID;

/**
 * {@link City} base model controller
 */
public interface CityController extends BaseModelController<City, UUID> {
    /**
     * Returns {@link Response} with {@link City} by input {@link String} name
     *
     * @param name initial input {@link String} city name to fetch by
     * @return {@link Response} with {@link City} body
     */
    Response<City> findCityByName(final String name);
}
