package com.sensiblemetrics.api.roadmap.router.service.command;

import com.sensiblemetrics.api.roadmap.router.service.management.ApplicationContext;

/**
 * Executable command interface declaration
 */
@FunctionalInterface
public interface ExecutableCommand {
    /**
     * It should execute logic for initialization process
     * to be used in {@link CommandBuilder}.
     *
     * @param context initial input {@link ApplicationContext} to initialize by
     */
    void execute(final ApplicationContext context);
}
