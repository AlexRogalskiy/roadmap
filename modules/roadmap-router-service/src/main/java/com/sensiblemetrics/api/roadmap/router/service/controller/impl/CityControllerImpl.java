package com.sensiblemetrics.api.roadmap.router.service.controller.impl;

import com.googlecode.jmapper.JMapper;
import com.sensiblemetrics.api.roadmap.router.service.controller.interfaces.CityController;
import com.sensiblemetrics.api.roadmap.router.service.model.domain.Response;
import com.sensiblemetrics.api.roadmap.router.service.model.dto.CityModelDto;
import com.sensiblemetrics.api.roadmap.router.service.model.entity.CityModelEntity;
import com.sensiblemetrics.api.roadmap.router.service.service.interfaces.CityModelService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

/**
 * {@link CityModelDto} base model controller implementation
 */
@Slf4j
@RequiredArgsConstructor
public class CityControllerImpl extends BaseModelControllerImpl<CityModelDto, UUID> implements CityController {

    private final CityModelService cityService;
    private final JMapper<CityModelEntity, CityModelDto> cityEntityToDtoMapper;
    private final JMapper<CityModelDto, CityModelEntity> cityDtoToEntityMapper;

    public CityControllerImpl(final CityModelService cityService) {
        this.cityService = cityService;
        this.cityEntityToDtoMapper = new JMapper<>(CityModelEntity.class, CityModelDto.class);
        this.cityDtoToEntityMapper = new JMapper<>(CityModelDto.class, CityModelEntity.class);
    }

    @Override
    public Response<CityModelDto> add(final CityModelDto city) {
        log.info("Storing new city model: {}", city);
        try {
            final CityModelEntity savedCity = this.cityService.save(this.cityEntityToDtoMapper.getDestination(city));
            return Response.ok(this.cityDtoToEntityMapper.getDestination(savedCity));
        } catch (Exception e) {
            return Response.failed(e.getMessage());
        }
    }

    @Override
    public Response<CityModelDto> findCityByName(final String name) {
        log.info("Finding city model by name: {}", name);
        try {
            return this.cityService.findCityByName(name)
                .map(this.cityDtoToEntityMapper::getDestination)
                .map(Response::ok)
                .orElseGet(() -> Response.notFound(name));
        } catch (Exception e) {
            return Response.failed(e.getMessage());
        }
    }
}
