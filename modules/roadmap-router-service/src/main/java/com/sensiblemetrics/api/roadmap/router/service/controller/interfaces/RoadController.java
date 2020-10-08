package com.sensiblemetrics.api.roadmap.router.service.controller.interfaces;

import com.sensiblemetrics.api.roadmap.router.service.model.domain.Response;
import com.sensiblemetrics.api.roadmap.router.service.model.dto.RoadModelDto;

import java.util.List;
import java.util.UUID;

/**
 * {@link RoadModelDto} base model controller
 */
public interface RoadController extends BaseModelController<RoadModelDto, UUID> {
    /**
     * Returns {@link Response} with {@link Iterable} collection of {@link RoadModelDto}s by input {@link String} name
     *
     * @param name initial input {@link String} city name to fetch by
     * @return {@link Response} with {@link RoadModelDto}s body
     */
    Response<List<RoadModelDto>> findRoadsByCityName(final String name);
}
