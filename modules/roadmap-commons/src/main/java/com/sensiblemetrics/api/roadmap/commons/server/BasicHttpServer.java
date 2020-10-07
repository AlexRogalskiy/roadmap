package com.sensiblemetrics.api.roadmap.commons.server;

import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;

import static java.lang.String.format;

/**
 * Simple HTTP server to provide HTTP endpoints for {@link HttpHandler}s
 */
@Slf4j
public final class BasicHttpServer {
    /**
     * Default roadmap http server name
     */
    private static final String ROADMAP_HTTP_SERVER_NAME = "roadmap-http-server";

    private final int port;
    private final String path;
    private final HttpHandler httpHandler;

    private BasicHttpServer(final int port,
                            final String path,
                            final HttpHandler httpHandler) {
        this.port = port;
        this.path = path;
        this.httpHandler = httpHandler;
    }

    public static void startWith(final int port,
                                 final String path,
                                 final HttpHandler httpHandler) {
        final BasicHttpServer basicHttpServer = new BasicHttpServer(port, path, httpHandler);
        log.info("Starting HTTP server on {} port with path {}.", port, path);
        basicHttpServer.start();
    }

    private void start() {
        try {
            final HttpServer server = HttpServer.create(new InetSocketAddress(this.port), 0);
            server.createContext(this.path, this.httpHandler);

            final Thread healthThread = new Thread(server::start, ROADMAP_HTTP_SERVER_NAME);
            healthThread.start();
            log.info("HTTP server started on port {} with path {}.", this.port, this.path);
        } catch (IOException ex) {
            throw new IllegalStateException(format("HTTP server for path %s failed to start.", this.path), ex);
        }
    }
}
