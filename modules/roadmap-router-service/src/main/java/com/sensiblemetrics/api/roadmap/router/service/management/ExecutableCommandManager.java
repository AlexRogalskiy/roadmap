package com.sensiblemetrics.api.roadmap.router.service.management;

import com.sensiblemetrics.api.roadmap.router.service.command.CommandBuilder;
import com.sensiblemetrics.api.roadmap.router.service.command.EndpointRegistrationExecutableCommand;
import com.sensiblemetrics.api.roadmap.router.service.command.HttpServerExecutableCommand;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicBoolean;

@Slf4j
public final class ExecutableCommandManager {
    /**
     * Default initializer flag
     */
    private static final AtomicBoolean INIT = new AtomicBoolean(false);

    /**
     * Initializes executable command manager
     */
    public static void initialize() {
        if (!INIT.compareAndSet(false, true)) {
            throw new IllegalStateException("Executable command manager has already started");
        }
        log.info("Executable command manager started initialization.");
        CommandBuilder.fromContext(ApplicationContext.create())
            .withCommand(EndpointRegistrationExecutableCommand::new)
            .withCommand(HttpServerExecutableCommand::new)
            .execute();
        log.info("Executable command manager initialization finished successfully.");
    }
}
