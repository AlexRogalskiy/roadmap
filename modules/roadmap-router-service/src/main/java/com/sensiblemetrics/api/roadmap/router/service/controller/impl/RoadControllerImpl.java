package com.sensiblemetrics.api.roadmap.router.service.controller.impl;

import com.google.common.collect.Iterables;
import com.googlecode.jmapper.JMapper;
import com.sensiblemetrics.api.roadmap.router.service.controller.interfaces.RoadController;
import com.sensiblemetrics.api.roadmap.router.service.model.domain.Response;
import com.sensiblemetrics.api.roadmap.router.service.model.dto.RoadModelDto;
import com.sensiblemetrics.api.roadmap.router.service.model.entity.RoadModelEntity;
import com.sensiblemetrics.api.roadmap.router.service.service.interfaces.RoadModelService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.sensiblemetrics.api.roadmap.commons.utils.ServiceUtils.not;
import static com.sensiblemetrics.api.roadmap.commons.utils.ServiceUtils.streamOf;

/**
 * {@link RoadModelDto} base model controller implementation
 */
@Slf4j
@RequiredArgsConstructor
public class RoadControllerImpl extends BaseModelControllerImpl<RoadModelDto, UUID> implements RoadController {

    private final RoadModelService roadService;
    private final JMapper<RoadModelEntity, RoadModelDto> roadEntityToDtoMapper;
    private final JMapper<RoadModelDto, RoadModelEntity> roadDtoToEntityMapper;

    public RoadControllerImpl(final RoadModelService roadService) {
        this.roadService = roadService;
        this.roadEntityToDtoMapper = new JMapper<>(RoadModelEntity.class, RoadModelDto.class);
        this.roadDtoToEntityMapper = new JMapper<>(RoadModelDto.class, RoadModelEntity.class);
    }

    @Override
    public Response<RoadModelDto> add(final RoadModelDto road) {
        log.info("Storing new road model: {}", road);
        try {
            final RoadModelEntity savedRoad = this.roadService.save(this.roadEntityToDtoMapper.getDestination(road));
            return Response.ok(this.roadDtoToEntityMapper.getDestination(savedRoad));
        } catch (Exception e) {
            return Response.failed(e.getMessage());
        }
    }

    @Override
    public Response<RoadModelDto> remove(final RoadModelDto road) {
        log.info("Removing road model: {}", road);
        try {
            final RoadModelEntity deletedRoad = this.roadService.delete(this.roadEntityToDtoMapper.getDestination(road));
            return Response.ok(this.roadDtoToEntityMapper.getDestination(deletedRoad));
        } catch (Exception e) {
            return Response.failed(e.getMessage());
        }
    }

    @Override
    public Response<List<RoadModelDto>> findRoadsByCityName(final String name) {
        log.info("Finding road models by name: {}", name);
        try {
            return Optional.of(this.roadService.findRoadsByCityName(name))
                .filter(not(Iterables::isEmpty))
                .map(v -> streamOf(v.iterator()).map(this.roadDtoToEntityMapper::getDestination).collect(Collectors.toList()))
                .map(Response::ok)
                .orElseGet(() -> Response.notFound(name));
        } catch (Exception e) {
            return Response.failed(e.getMessage());
        }
    }
}
