package com.sensiblemetrics.api.roadmap.router.service.controller.interfaces;

import com.sensiblemetrics.api.roadmap.router.service.model.domain.Response;
import com.sensiblemetrics.api.roadmap.router.service.model.dto.CityModelDto;

import java.util.UUID;

/**
 * {@link CityModelDto} base model controller
 */
public interface CityController extends BaseModelController<CityModelDto, UUID> {
    /**
     * Returns {@link Response} with {@link CityModelDto} by input {@link String} name
     *
     * @param name initial input {@link String} city name to fetch by
     * @return {@link Response} with {@link CityModelDto} body
     */
    Response<CityModelDto> findCityByName(final String name);
}
