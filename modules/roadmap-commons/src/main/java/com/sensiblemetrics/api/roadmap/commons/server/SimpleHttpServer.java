package com.sensiblemetrics.api.roadmap.commons.server;

import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.Validate;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.Executor;

import static java.util.concurrent.Executors.newSingleThreadExecutor;

/**
 * Simple HTTP-server implementation
 */
@Slf4j
public final class SimpleHttpServer {
    /**
     * Default HTTP server {@link Marker} instance
     */
    private static final Marker SIMPLE_HTTP_SERVER_MARKER = MarkerFactory.getMarker("SIMPLE_HTTP_SERVER");

    /**
     * Default {@link HttpServer} instance
     */
    private final HttpServer httpServer;

    /**
     * Instantiates a new simple http server by input parameters
     *
     * @param port    initial input {@code int} server port
     * @param context initial input {@link String} server context
     * @param handler initial input {@link HttpHandler} server handler
     */
    private SimpleHttpServer(final int port,
                             final String context,
                             final HttpHandler handler) {
        this(port, Collections.singletonMap(context, handler));
    }

    /**
     * Instantiates a new simple http server by input parameters
     *
     * @param port     initial input {@code int} server port
     * @param handlers initial input {@link Map} collection of {@link HttpHandler}s
     */
    private SimpleHttpServer(final int port,
                             final Map<String, HttpHandler> handlers) {
        this(port, newSingleThreadExecutor(), handlers);
    }

    /**
     * Instantiates a new simple http server by input parameters
     *
     * @param port     initial input {@code int} server port
     * @param executor initial input {@link Executor} server executor
     * @param handlers initial input {@link Map} collection of {@link HttpHandler}s
     */
    private SimpleHttpServer(final int port,
                             final Executor executor,
                             final Map<String, HttpHandler> handlers) {
        Validate.isTrue(port >= 0, "Port should be positive or zero");
        Validate.notNull(executor, "Executor should not be null");
        Validate.notNull(handlers, "Handlers should not be null");

        try {
            //Create HttpServer which is listening on the given port
            this.httpServer = HttpServer.create(new InetSocketAddress(port), 0);
            //Create a new context for the given context and handler
            handlers.forEach(this.httpServer::createContext);
            //Create a default executor
            this.httpServer.setExecutor(executor);
        } catch (IOException ex) {
            if (log.isErrorEnabled()) {
                log.error(SIMPLE_HTTP_SERVER_MARKER, "cannot start HTTP-server, message: {}", ex.getMessage());
            }
            throw new IllegalStateException(ex);
        }
    }

    /**
     * Returns new {@link SimpleHttpServer} by input parameters
     *
     * @param port     initial input {@code int} server port
     * @param executor initial input {@link Executor} server executor
     * @param handlers initial input {@link Map} collection of {@link HttpHandler}s
     * @return new {@link SimpleHttpServer}
     */
    public static SimpleHttpServer create(final int port,
                                          final Executor executor,
                                          final Map<String, HttpHandler> handlers) {
        log.info("Starting HTTP server on port: {}", port);
        return new SimpleHttpServer(port, executor, handlers);
    }

    /**
     * Returns new {@link SimpleHttpServer} by input parameters
     *
     * @param port     initial input {@code int} server port
     * @param handlers initial input {@link Map} collection of {@link HttpHandler}s
     * @return new {@link SimpleHttpServer}
     */
    public static SimpleHttpServer create(final int port,
                                          final Map<String, HttpHandler> handlers) {
        return create(port, newSingleThreadExecutor(), handlers);
    }

    /**
     * Starting HTTP-server
     */
    public void start() {
        if (log.isInfoEnabled()) {
            log.info(SIMPLE_HTTP_SERVER_MARKER, "starting HTTP-server on address: {}", this.httpServer.getAddress());
        }
        this.httpServer.start();
    }

    /**
     * Stopping HTTP-server
     */
    public void stop() {
        if (log.isInfoEnabled()) {
            log.info(SIMPLE_HTTP_SERVER_MARKER, "stopping HTTP-server on address: {}", this.httpServer.getAddress());
        }
        this.httpServer.stop(0);
    }
}
