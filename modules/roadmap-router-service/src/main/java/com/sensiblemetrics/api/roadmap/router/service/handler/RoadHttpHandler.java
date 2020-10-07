package com.sensiblemetrics.api.roadmap.router.service.handler;

import com.sensiblemetrics.api.roadmap.router.service.controller.interfaces.RoadController;
import com.sensiblemetrics.api.roadmap.router.service.management.DelegatedObjectMapper;
import com.sun.net.httpserver.HttpExchange;

public class RoadHttpHandler extends RouterHttpHandler {

    private final RoadController roadController;

    public RoadHttpHandler(final DelegatedObjectMapper objectMapper,
                           final RoadController roadController) {
        super(objectMapper);
        this.roadController = roadController;
    }

    /**
     * Handles input {@link HttpExchange}
     *
     * @param httpExchange initial input {@link HttpExchange} to operate by
     */
    @Override
    public void handle(final HttpExchange httpExchange) {
//        Optional.of(failures)
//            .filter(Collection::isEmpty)
//            .map(o -> sendResponse(HttpStatus.SC_OK, new HealthCheckResponse(HealthCheckStatus.UP)))
//            .orElse(sendResponse(HttpStatus.SC_INTERNAL_SERVER_ERROR, new HealthCheckResponse(HealthCheckStatus.DOWN, failures)))
//            .accept(httpExchange);
    }
}
