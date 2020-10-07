package com.sensiblemetrics.api.roadmap.router.service.controller.interfaces;

import com.sensiblemetrics.api.roadmap.router.service.model.domain.Response;
import com.sensiblemetrics.api.roadmap.router.service.model.entity.RoadEntity;

import java.util.UUID;

/**
 * {@link RoadEntity} base model controller
 */
public interface RoadController extends BaseModelController<RoadEntity, UUID> {
    /**
     * Returns {@link Response} with {@link Iterable} collection of {@link RoadEntity}s by input {@link String} name
     *
     * @param name initial input {@link String} city name to fetch by
     * @return {@link Response} with {@link RoadEntity}s body
     */
    Response<Iterable<RoadEntity>> findRoadsByCityName(final String name);
}
