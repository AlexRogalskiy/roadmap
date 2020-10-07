package com.sensiblemetrics.api.roadmap.router.service.handler;

import com.sensiblemetrics.api.roadmap.router.service.controller.interfaces.CityController;
import com.sensiblemetrics.api.roadmap.router.service.controller.interfaces.RoadController;
import com.sensiblemetrics.api.roadmap.router.service.mapper.DelegatedObjectMapper;
import com.sensiblemetrics.api.roadmap.router.service.model.domain.Response;
import com.sensiblemetrics.api.roadmap.router.service.model.entity.CityModelEntity;
import com.sensiblemetrics.api.roadmap.router.service.model.entity.RoadModelEntity;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.utils.IOUtils;

import java.io.*;
import java.util.function.Consumer;

@Slf4j
@UtilityClass
public class ResponseRouter {

    public static HttpHandler findCityByNameHandler(final DelegatedObjectMapper mapper,
                                                    final CityController cityController) {
        return httpExchange -> {
            final String name = mapper.map(httpExchange.getRequestBody(), String.class);
            final Response<CityModelEntity> response = cityController.findCityByName(name);
            sendResponse(response.getStatus(), mapper.toString(response)).accept(httpExchange);
        };
    }

    public static HttpHandler createNewCityHandler(final DelegatedObjectMapper mapper,
                                                   final CityController cityController) {
        return httpExchange -> {
            final CityModelEntity cityModelEntity = mapper.map(httpExchange.getRequestBody(), CityModelEntity.class);
            final Response<CityModelEntity> response = cityController.add(cityModelEntity);
            sendResponse(response.getStatus(), mapper.toString(response)).accept(httpExchange);
        };
    }

    public static HttpHandler createNewRoadHandler(final DelegatedObjectMapper mapper,
                                                   final RoadController roadController) {
        return httpExchange -> {
            final RoadModelEntity roadModelEntity = mapper.map(httpExchange.getRequestBody(), RoadModelEntity.class);
            final Response<RoadModelEntity> response = roadController.add(roadModelEntity);
            sendResponse(response.getStatus(), mapper.toString(response)).accept(httpExchange);
        };
    }

    public static HttpHandler removeRoadHandler(final DelegatedObjectMapper mapper,
                                                final RoadController roadController) {
        return httpExchange -> {
            final RoadModelEntity roadModelEntity = mapper.map(httpExchange.getRequestBody(), RoadModelEntity.class);
            final Response<RoadModelEntity> response = roadController.remove(roadModelEntity);
            sendResponse(response.getStatus(), mapper.toString(response)).accept(httpExchange);
        };
    }

    public static HttpHandler findRoadsByCityNameHandler(final DelegatedObjectMapper mapper,
                                                         final RoadController roadController) {
        return httpExchange -> {
            final String name = mapper.map(httpExchange.getRequestBody(), String.class);
            final Response<Iterable<RoadModelEntity>> response = roadController.findRoadsByCityName(name);
            sendResponse(response.getStatus(), mapper.toString(response)).accept(httpExchange);
        };
    }

    private static Consumer<HttpExchange> sendResponse(final int status,
                                                       final String data) {
        return httpExchange -> {
            try {
                try (final InputStream sourceStream = new ByteArrayInputStream(data.getBytes());
                     final OutputStream outputStream = httpExchange.getResponseBody()) {
                    httpExchange.sendResponseHeaders(status, data.getBytes().length);
                    IOUtils.copy(sourceStream, outputStream);
                    outputStream.flush();
                }
            } catch (IOException ex) {
                log.error("Cannot send response: {} with status: {}, message: {}", data, status, ex.getMessage());
                throw new UncheckedIOException(ex);
            }
        };
    }
}
