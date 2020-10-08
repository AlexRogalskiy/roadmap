package com.sensiblemetrics.api.roadmap.router.service.management;

import com.sensiblemetrics.api.roadmap.commons.enumeration.HttpMethod;
import io.undertow.server.HttpHandler;
import io.undertow.server.RoutingHandler;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.Validate;

@Slf4j
@Getter
public class EndpointRoutingRegistry {

    private final RoutingHandler routingHandler;

    /**
     * Default endpoint registry constructor with empty url mappings
     */
    public EndpointRoutingRegistry() {
        this.routingHandler = new RoutingHandler();
    }

    /**
     * Adds input {@link HttpHandler} by provided {@link String} url mapping
     *
     * @param method      initial input {@link HttpMethod} method
     * @param urlMapping  initial input {@link String} url mapping
     * @param httpHandler initial input {@link HttpHandler} instance
     */
    public void register(final HttpMethod method,
                         final String urlMapping,
                         final HttpHandler httpHandler) {
        Validate.notNull(method, "Http method should not be null");
        Validate.notBlank(urlMapping, "Url mapping should not be blank");
        Validate.notNull(httpHandler, "Http handler should not be null");

        log.info("Registering new http handler with method: {} on context path: {}", method, urlMapping);
        this.routingHandler.add(method.name(), urlMapping, httpHandler);
    }
}
