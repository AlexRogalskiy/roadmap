package com.sensiblemetrics.api.roadmap.router.service.controller.interfaces;

import com.sensiblemetrics.api.roadmap.router.service.model.dto.Response;
import com.sensiblemetrics.api.roadmap.router.service.model.entity.Road;

import java.util.UUID;

/**
 * {@link Road} base model controller
 */
public interface RoadController extends BaseModelController<Road, UUID> {
    /**
     * Returns {@link Response} with {@link Iterable} collection of {@link Road}s by input {@link String} name
     *
     * @param name initial input {@link String} city name to fetch by
     * @return {@link Response} with {@link Road}s body
     */
    Response<Iterable<Road>> findRoadsByCityName(final String name);
}
