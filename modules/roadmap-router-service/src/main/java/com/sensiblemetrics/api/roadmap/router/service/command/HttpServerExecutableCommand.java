package com.sensiblemetrics.api.roadmap.router.service.command;

import com.sensiblemetrics.api.roadmap.commons.server.SimpleHttpServer;
import com.sensiblemetrics.api.roadmap.router.service.management.ApplicationContext;
import com.sensiblemetrics.api.roadmap.router.service.management.ConfigurationProperties;
import com.sensiblemetrics.api.roadmap.router.service.management.EndpointRegistry;

public class HttpServerExecutableCommand implements ExecutableCommand {
    /**
     * Starts HTTP server for a health endpoint
     *
     * @param context initial input {@link ApplicationContext} instance
     */
    @Override
    public void execute(final ApplicationContext context) {
        final ConfigurationProperties configLoader = context.getConfigurationProperties();
        final EndpointRegistry endpointRegistry = context.getRegistry();
        final SimpleHttpServer simpleHttpServer = SimpleHttpServer.newInstance(
            configLoader.getServerPort(),
            endpointRegistry.getEndpointMappings()
        );
        simpleHttpServer.start();
    }
}
