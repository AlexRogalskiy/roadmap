package com.sensiblemetrics.api.roadmap.router.service.controller.interfaces;

import com.sensiblemetrics.api.roadmap.router.service.model.domain.Response;
import com.sensiblemetrics.api.roadmap.router.service.model.entity.RoadModelEntity;

import java.util.UUID;

/**
 * {@link RoadModelEntity} base model controller
 */
public interface RoadController extends BaseModelController<RoadModelEntity, UUID> {
    /**
     * Returns {@link Response} with {@link Iterable} collection of {@link RoadModelEntity}s by input {@link String} name
     *
     * @param name initial input {@link String} city name to fetch by
     * @return {@link Response} with {@link RoadModelEntity}s body
     */
    Response<Iterable<RoadModelEntity>> findRoadsByCityName(final String name);
}
