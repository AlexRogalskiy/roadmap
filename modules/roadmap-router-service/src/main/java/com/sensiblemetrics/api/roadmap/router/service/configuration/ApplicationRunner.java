package com.sensiblemetrics.api.roadmap.router.service.configuration;

import com.sensiblemetrics.api.roadmap.router.service.management.ExecutableCommandManager;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ApplicationRunner {
    private final String[] args;

    public ApplicationRunner(final String[] args) {
        this.args = args;
    }

    public void init() {
        log.info("Starting application runner...");
        ExecutableCommandManager.initialize();
    }
}
