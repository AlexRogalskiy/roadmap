package com.sensiblemetrics.api.roadmap.router.service.undertow;

import com.sensiblemetrics.api.roadmap.commons.enumeration.HttpMethod;
import com.sensiblemetrics.api.roadmap.commons.helper.DelegatedObjectMapper;
import com.sensiblemetrics.api.roadmap.router.service.command.ExecutableCommand;
import com.sensiblemetrics.api.roadmap.router.service.configuration.RouterConfiguration;
import com.sensiblemetrics.api.roadmap.router.service.controller.interfaces.CityController;
import com.sensiblemetrics.api.roadmap.router.service.controller.interfaces.RoadController;
import com.sensiblemetrics.api.roadmap.router.service.management.ApplicationContext;
import com.sensiblemetrics.api.roadmap.router.service.management.EndpointRoutingRegistry;
import com.sun.net.httpserver.HttpHandler;

import static com.sensiblemetrics.api.roadmap.router.service.undertow.UndertowResponseRouter.*;

public class UndertowEndpointRegistrationExecutableCommand implements ExecutableCommand {
    /**
     * Registers all available {@link HttpHandler}s
     *
     * @param context initial input {@link ApplicationContext} instance
     */
    @Override
    public void execute(final ApplicationContext context) {
        final EndpointRoutingRegistry registry = context.getEndpointRoutingRegistry();
        final RouterConfiguration routerConfiguration = context.getRouterConfiguration();

        final DelegatedObjectMapper mapper = new DelegatedObjectMapper();
        final CityController cityController = routerConfiguration.getCityController();
        final RoadController roadController = routerConfiguration.getRoadController();

//        Handlers.routing()
//            .post("/api/cities", createNewCityHandler(mapper, cityController))
//            .get("/api/cities/find", findCityByNameHandler(mapper, cityController))
//            .post("/api/roads", createNewRoadHandler(mapper, roadController))
//            .delete("/api/roads/{id}", removeRoadHandler(mapper, roadController))
//            .get("/api/roads/find", findRoadsByCityNameHandler(mapper, roadController));

        registry.register(HttpMethod.POST, "/api/cities", createNewCityHandler(mapper, cityController));
        registry.register(HttpMethod.GET, "/api/cities/find", findCityByNameHandler(mapper, cityController));
        registry.register(HttpMethod.POST, "/api/roads", createNewRoadHandler(mapper, roadController));
        registry.register(HttpMethod.DELETE, "/api/roads/{id}", removeRoadHandler(mapper, roadController));
        registry.register(HttpMethod.GET, "/api/roads/find", findRoadsByCityNameHandler(mapper, roadController));
    }
}
