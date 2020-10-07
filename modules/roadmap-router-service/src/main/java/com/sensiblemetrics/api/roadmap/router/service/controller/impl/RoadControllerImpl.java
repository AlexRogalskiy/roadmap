package com.sensiblemetrics.api.roadmap.router.service.controller.impl;

import com.google.common.collect.Iterables;
import com.sensiblemetrics.api.roadmap.router.service.controller.interfaces.RoadController;
import com.sensiblemetrics.api.roadmap.router.service.model.domain.Response;
import com.sensiblemetrics.api.roadmap.router.service.model.entity.RoadEntity;
import com.sensiblemetrics.api.roadmap.router.service.service.interfaces.RoadService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;
import java.util.UUID;

import static com.sensiblemetrics.api.roadmap.commons.utils.ServiceUtils.not;

/**
 * {@link RoadEntity} base model controller implementation
 */
@Slf4j
@RequiredArgsConstructor
public class RoadControllerImpl extends BaseModelControllerImpl<RoadEntity, UUID> implements RoadController {

    private final RoadService roadService;

    @Override
    public Response<RoadEntity> add(final RoadEntity road) {
        log.info("Storing new road: {}", road);
        try {
            final RoadEntity savedRoad = this.roadService.save(road);
            return Response.ok(savedRoad);
        } catch (Exception e) {
            return Response.failed(e.getMessage());
        }
    }

    @Override
    public Response<RoadEntity> remove(final RoadEntity road) {
        log.info("Removing road: {}", road);
        try {
            final RoadEntity deletedRoad = this.roadService.delete(road);
            return Response.ok(deletedRoad);
        } catch (Exception e) {
            return Response.failed(e.getMessage());
        }
    }

    @Override
    public Response<Iterable<RoadEntity>> findRoadsByCityName(final String name) {
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
