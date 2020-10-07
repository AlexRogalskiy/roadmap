package com.sensiblemetrics.api.roadmap.router.service.controller.impl;

import com.sensiblemetrics.api.roadmap.router.service.controller.interfaces.CityController;
import com.sensiblemetrics.api.roadmap.router.service.model.domain.Response;
import com.sensiblemetrics.api.roadmap.router.service.model.entity.CityModelEntity;
import com.sensiblemetrics.api.roadmap.router.service.service.interfaces.CityService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

/**
 * {@link CityModelEntity} base model controller implementation
 */
@Slf4j
@RequiredArgsConstructor
public class CityControllerImpl extends BaseModelControllerImpl<CityModelEntity, UUID> implements CityController {

    private final CityService cityService;

    @Override
    public Response<CityModelEntity> add(final CityModelEntity city) {
        log.info("Storing new city: {}", city);
        try {
            final CityModelEntity savedCity = this.cityService.save(city);
            return Response.ok(savedCity);
        } catch (Exception e) {
            return Response.failed(e.getMessage());
        }
    }

    @Override
    public Response<CityModelEntity> findCityByName(final String name) {
        log.info("Finding city by name: {}", name);
        try {
            return this.cityService.findCityByName(name)
                .map(Response::ok)
                .orElseGet(() -> Response.notFound(name));
        } catch (Exception e) {
            return Response.failed(e.getMessage());
        }
    }
}
