package com.sensiblemetrics.api.roadmap.router.service.command;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sensiblemetrics.api.roadmap.router.service.configuration.RouterConfiguration;
import com.sensiblemetrics.api.roadmap.router.service.controller.interfaces.CityController;
import com.sensiblemetrics.api.roadmap.router.service.controller.interfaces.RoadController;
import com.sensiblemetrics.api.roadmap.router.service.management.ApplicationContext;
import com.sensiblemetrics.api.roadmap.router.service.management.EndpointRegistry;
import com.sensiblemetrics.api.roadmap.router.service.mapper.DelegatedObjectMapper;
import com.sun.net.httpserver.HttpHandler;

import static com.sensiblemetrics.api.roadmap.router.service.handler.ResponseRouter.*;

public class EndpointRegistrationExecutableCommand implements ExecutableCommand {
    /**
     * Registers all available {@link HttpHandler}s
     *
     * @param context initial input {@link ApplicationContext} instance
     */
    @Override
    public void execute(final ApplicationContext context) {
        final EndpointRegistry registry = context.getRegistry();
        final RouterConfiguration routerConfiguration = context.getRouterConfiguration();

        final DelegatedObjectMapper mapper = new DelegatedObjectMapper(new ObjectMapper());
        final CityController cityController = routerConfiguration.getCityController();
        final RoadController roadController = routerConfiguration.getRoadController();

        registry.register("/api/cities/create", createNewCityHandler(mapper, cityController));
        registry.register("/api/cities/find/name", findCityByNameHandler(mapper, cityController));
        registry.register("/api/roads/create", createNewRoadHandler(mapper, roadController));
        registry.register("/api/roads/delete", removeRoadHandler(mapper, roadController));
        registry.register("/api/roads/find/city-name", findRoadsByCityNameHandler(mapper, roadController));
    }
}
