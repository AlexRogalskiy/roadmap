package com.sensiblemetrics.api.roadmap.router.service.controller.impl;

import com.google.common.collect.Iterables;
import com.sensiblemetrics.api.roadmap.router.service.controller.interfaces.RoadController;
import com.sensiblemetrics.api.roadmap.router.service.model.domain.Response;
import com.sensiblemetrics.api.roadmap.router.service.model.entity.RoadModelEntity;
import com.sensiblemetrics.api.roadmap.router.service.service.interfaces.RoadService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;
import java.util.UUID;

import static com.sensiblemetrics.api.roadmap.commons.utils.ServiceUtils.not;

/**
 * {@link RoadModelEntity} base model controller implementation
 */
@Slf4j
@RequiredArgsConstructor
public class RoadControllerImpl extends BaseModelControllerImpl<RoadModelEntity, UUID> implements RoadController {

    private final RoadService roadService;

    @Override
    public Response<RoadModelEntity> add(final RoadModelEntity road) {
        log.info("Storing new road: {}", road);
        try {
            final RoadModelEntity savedRoad = this.roadService.save(road);
            return Response.ok(savedRoad);
        } catch (Exception e) {
            return Response.failed(e.getMessage());
        }
    }

    @Override
    public Response<RoadModelEntity> remove(final RoadModelEntity road) {
        log.info("Removing road: {}", road);
        try {
            final RoadModelEntity deletedRoad = this.roadService.delete(road);
            return Response.ok(deletedRoad);
        } catch (Exception e) {
            return Response.failed(e.getMessage());
        }
    }

    @Override
    public Response<Iterable<RoadModelEntity>> findRoadsByCityName(final String name) {
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
