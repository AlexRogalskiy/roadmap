package com.sensiblemetrics.api.roadmap.router.service.controller;

import com.google.common.collect.Iterables;
import com.sensiblemetrics.api.roadmap.router.service.model.domain.Response;
import com.sensiblemetrics.api.roadmap.router.service.model.entity.Road;
import com.sensiblemetrics.api.roadmap.router.service.service.RoadService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;
import java.util.UUID;

import static java.util.function.Predicate.not;

/**
 * {@link Road} base model controller implementation
 */
@Slf4j
@RequiredArgsConstructor
public class RoadControllerImpl extends BaseModelControllerImpl<Road, UUID> implements RoadController {

    private final RoadService roadService;

    @Override
    public Response<Road> add(final Road road) {
        log.info("Storing new road: {}", road);
        try {
            final Road savedRoad = this.roadService.save(road);
            return Response.ok(savedRoad);
        } catch (Exception e) {
            return Response.failed(e.getMessage());
        }
    }

    @Override
    public Response<Road> remove(final Road road) {
        log.info("Removing road: {}", road);
        try {
            final Road deletedRoad = this.roadService.delete(road);
            return Response.ok(deletedRoad);
        } catch (Exception e) {
            return Response.failed(e.getMessage());
        }
    }

    @Override
    public Response<Iterable<Road>> findRoadsByCityName(final String name) {
        log.info("Finding roads by name: {}", name);
        try {
            return Optional.of(this.roadService.findRoadsByCityName(name))
                .filter(not(Iterables::isEmpty))
                .map(Response::ok)
                .orElseGet(() -> Response.notFound(name));
        } catch (Exception e) {
            return Response.failed(e.getMessage());
        }
    }
}
