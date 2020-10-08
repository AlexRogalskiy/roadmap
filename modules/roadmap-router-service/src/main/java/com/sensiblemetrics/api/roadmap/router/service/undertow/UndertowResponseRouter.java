package com.sensiblemetrics.api.roadmap.router.service.undertow;

import com.sensiblemetrics.api.roadmap.commons.helper.DelegatedObjectMapper;
import com.sensiblemetrics.api.roadmap.router.service.controller.interfaces.CityController;
import com.sensiblemetrics.api.roadmap.router.service.controller.interfaces.RoadController;
import com.sensiblemetrics.api.roadmap.router.service.model.domain.Response;
import com.sensiblemetrics.api.roadmap.router.service.model.dto.CityModelDto;
import com.sensiblemetrics.api.roadmap.router.service.model.dto.RoadModelDto;
import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import io.undertow.util.HttpString;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.function.Consumer;

@Slf4j
@UtilityClass
public class UndertowResponseRouter {

    public static HttpHandler findCityByNameHandler(final DelegatedObjectMapper mapper,
                                                    final CityController cityController) {
        return httpExchange -> {
            final String name = mapper.map(httpExchange.getQueryParameters().get("name").getFirst(), String.class);
            final Response<CityModelDto> response = cityController.findCityByName(name);
            sendResponse(response.getStatus(), mapper.toString(response)).accept(httpExchange);
        };
    }

    public static HttpHandler createNewCityHandler(final DelegatedObjectMapper mapper,
                                                   final CityController cityController) {
        return httpExchange -> {
            final CityModelDto cityModelDto = mapper.map(httpExchange.getInputStream(), CityModelDto.class);
            final Response<CityModelDto> response = cityController.add(cityModelDto);
            sendResponse(response.getStatus(), mapper.toString(response)).accept(httpExchange);
        };
    }

    public static HttpHandler createNewRoadHandler(final DelegatedObjectMapper mapper,
                                                   final RoadController roadController) {
        return httpExchange -> {
            final RoadModelDto roadModelDto = mapper.map(httpExchange.getInputStream(), RoadModelDto.class);
            final Response<RoadModelDto> response = roadController.add(roadModelDto);
            sendResponse(response.getStatus(), mapper.toString(response)).accept(httpExchange);
        };
    }

    public static HttpHandler removeRoadHandler(final DelegatedObjectMapper mapper,
                                                final RoadController roadController) {
        return httpExchange -> {
            final Response<RoadModelDto> response = roadController.removeById(httpExchange.getPathParameters().get("id").getFirst());
            sendResponse(response.getStatus(), mapper.toString(response)).accept(httpExchange);
        };
    }

    public static HttpHandler findRoadsByCityNameHandler(final DelegatedObjectMapper mapper,
                                                         final RoadController roadController) {
        return httpExchange -> {
            final String name = mapper.map(httpExchange.getQueryParameters().get("city-name").getFirst(), String.class);
            final Response<List<RoadModelDto>> response = roadController.findRoadsByCityName(name);
            sendResponse(response.getStatus(), mapper.toString(response)).accept(httpExchange);
        };
    }

    private static Consumer<HttpServerExchange> sendResponse(final int status,
                                                             final String data) {
        return httpExchange -> {
            httpExchange.setStatusCode(status);
            httpExchange.setResponseContentLength(data.getBytes().length);
            httpExchange.getResponseHeaders().add(new HttpString("Content-Type"), "application/json");
            httpExchange.getResponseSender().send(data);
        };
    }
}
