package com.sensiblemetrics.api.roadmap.router.service.management;

import com.sun.net.httpserver.HttpHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.Validate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public class EndpointRegistry {

    private final Map<String, HttpHandler> httpHandlerMap;

    /**
     * Default endpoint registry constructor with empty url mappings
     */
    public EndpointRegistry() {
        this(Collections.emptyMap());
    }

    /**
     * Default endpoint registry constructor by input parameters
     *
     * @param httpHandlerMap initial input {@link Map} collection of url mappings
     *                       with corresponding {@link HttpHandler}s
     */
    public EndpointRegistry(final Map<String, HttpHandler> httpHandlerMap) {
        Validate.notNull(httpHandlerMap, "Http handler mapping collection should not be null");

        this.httpHandlerMap = new ConcurrentHashMap<>(httpHandlerMap);
    }

    /**
     * Returns all registered url mappings
     *
     * @return List of url mappings
     */
    public List<String> getUrls() {
        return new ArrayList<>(this.httpHandlerMap.keySet());
    }

    /**
     * Returns all registered url mappings
     *
     * @return List of url mappings
     */
    public Map<String, HttpHandler> getEndpointMappings() {
        return Collections.unmodifiableMap(this.httpHandlerMap);
    }

    /**
     * Adds input {@link HttpHandler} by provided {@link String} url mapping
     *
     * @param urlMapping  initial input {@link String} url mapping
     * @param httpHandler initial input {@link HttpHandler} instance
     */
    public void register(final String urlMapping,
                         final HttpHandler httpHandler) {
        Validate.notBlank(urlMapping, "Url mapping should not be blank");
        Validate.notNull(httpHandler, "Http handler should not be null");

        log.info("Registering new http handler on context path: {}", urlMapping);
        this.httpHandlerMap.put(urlMapping, httpHandler);
    }
}
