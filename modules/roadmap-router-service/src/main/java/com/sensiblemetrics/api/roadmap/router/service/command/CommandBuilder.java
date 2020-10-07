package com.sensiblemetrics.api.roadmap.router.service.command;

import com.sensiblemetrics.api.roadmap.router.service.management.ApplicationContext;
import org.apache.commons.lang3.Validate;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public final class CommandBuilder {
    /**
     * {@link List} collection of {@link ExecutableCommand}
     */
    private final List<ExecutableCommand> commands = new ArrayList<>();
    private final ApplicationContext context;

    /**
     * Private command builder constructor with input {@link ApplicationContext}
     *
     * @param context initial input {@link ApplicationContext} to initialize by
     */
    private CommandBuilder(final ApplicationContext context) {
        this.context = context;
    }

    /**
     * Creates a builder for health check commands managements
     *
     * @param context initial input {@link ApplicationContext}
     * @return {@link CommandBuilder} instance
     */
    public static CommandBuilder fromContext(final ApplicationContext context) {
        Validate.notNull(context, "Health check context should not be null");

        return new CommandBuilder(context);
    }

    /**
     * Puts a command to a command list for next executions.
     *
     * @param commandSupplier initial input {@link Supplier} of {@link ExecutableCommand} instance
     * @return {@link CommandBuilder} instance
     */
    public CommandBuilder withCommand(final Supplier<ExecutableCommand> commandSupplier) {
        Validate.notNull(commandSupplier, "Health check init command supplier should not be null");

        return withCommand(commandSupplier.get());
    }

    /**
     * Puts a command to a command list for next executions.
     *
     * @param command initial input {@link ExecutableCommand} instance
     * @return {@link CommandBuilder} instance
     */
    public CommandBuilder withCommand(final ExecutableCommand command) {
        Validate.notNull(command, "Health check init command should not be null");

        this.commands.add(command);
        return this;
    }

    /**
     * Runs all registered executable commands
     */
    public void execute() {
        this.commands.forEach(command -> command.execute(this.context));
    }
}
