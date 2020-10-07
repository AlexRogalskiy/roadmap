package com.sensiblemetrics.api.roadmap.router.service.command;

import com.sensiblemetrics.api.roadmap.router.service.management.ApplicationContext;
import com.sensiblemetrics.api.roadmap.router.service.management.EndpointRegistry;
import com.sun.net.httpserver.HttpHandler;

public class EndpointRegistrationExecutableCommand implements ExecutableCommand {
    /**
     * Registers all available {@link HttpHandler}s
     *
     * @param context initial input {@link ApplicationContext} instance
     */
    @Override
    public void execute(final ApplicationContext context) {
        final EndpointRegistry registry = context.getRegistry();
        registry.register("/", null);
    }
}
