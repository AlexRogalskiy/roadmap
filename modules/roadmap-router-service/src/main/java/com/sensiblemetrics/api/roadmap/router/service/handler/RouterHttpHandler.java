package com.sensiblemetrics.api.roadmap.router.service.handler;

import com.sensiblemetrics.api.roadmap.router.service.management.DelegatedObjectMapper;
import com.sensiblemetrics.api.roadmap.router.service.model.domain.Response;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.utils.IOUtils;

import java.io.*;
import java.util.function.Consumer;

/**
 * This handler have to verify mandatory health checks
 * and it will be used for PDV. All health checks called in parallel
 * and the response of the handler will be sent in synchronous manner.
 */
@Slf4j
@Getter
@RequiredArgsConstructor
public abstract class RouterHttpHandler implements HttpHandler {

    private final DelegatedObjectMapper objectMapper;

    /**
     * Returns consumer that operates on {@link HttpExchange} output stream and sends response with input parameters
     *
     * @param status   initial input {@code status} to send
     * @param response initial input {@link Response}
     * @return consumer that sends HTTP response
     */
    protected <T> Consumer<HttpExchange> sendResponse(final int status,
                                                      final Response<T> response) {
        return httpExchange -> {
            try {
                final String data = this.objectMapper.toString(response);
                try (final InputStream sourceStream = new ByteArrayInputStream(data.getBytes());
                     final OutputStream outputStream = httpExchange.getResponseBody()) {
                    httpExchange.sendResponseHeaders(status, data.getBytes().length);
                    IOUtils.copy(sourceStream, outputStream);
                    outputStream.flush();
                }
            } catch (IOException ex) {
                log.error("Cannot send response: {}, message: {}", response, ex.getMessage());
                throw new UncheckedIOException(ex);
            }
        };
    }
}
