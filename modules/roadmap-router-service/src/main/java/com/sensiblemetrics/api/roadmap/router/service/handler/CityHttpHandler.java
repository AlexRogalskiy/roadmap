package com.sensiblemetrics.api.roadmap.router.service.handler;

import com.sensiblemetrics.api.roadmap.router.service.controller.interfaces.CityController;
import com.sensiblemetrics.api.roadmap.router.service.management.DelegatedObjectMapper;
import com.sensiblemetrics.api.roadmap.router.service.model.domain.Response;
import com.sensiblemetrics.api.roadmap.router.service.model.entity.CityEntity;
import com.sun.net.httpserver.HttpExchange;

public class CityHttpHandler extends RouterHttpHandler {

    private final CityController cityController;

    public CityHttpHandler(final DelegatedObjectMapper objectMapper,
                           final CityController cityController) {
        super(objectMapper);
        this.cityController = cityController;
    }

    /**
     * Handles input {@link HttpExchange}
     *
     * @param httpExchange initial input {@link HttpExchange} to operate by
     */
    @Override
    public void handle(final HttpExchange httpExchange) {
        final String name = this.getObjectMapper().map(httpExchange.getRequestBody(), String.class);
        final Response<CityEntity> response = this.cityController.findCityByName(name);

//        Optional.of(failures)
//            .filter(Collection::isEmpty)
//            .map(o -> sendResponse(HttpStatus.SC_OK, new HealthCheckResponse(HealthCheckStatus.UP)))
//            .orElse(sendResponse(HttpStatus.SC_INTERNAL_SERVER_ERROR, new HealthCheckResponse(HealthCheckStatus.DOWN, failures)))
//            .accept(httpExchange);
    }
}
