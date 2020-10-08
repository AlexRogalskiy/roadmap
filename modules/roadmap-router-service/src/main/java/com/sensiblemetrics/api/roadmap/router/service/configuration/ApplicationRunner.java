package com.sensiblemetrics.api.roadmap.router.service.configuration;

import com.sensiblemetrics.api.roadmap.router.service.management.ExecutableCommandManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;

@Slf4j
@RequiredArgsConstructor
public class ApplicationRunner {

    private final String[] args;

    public void init() {
        log.info("Starting application runner with args: {}", Arrays.toString(this.args));
        ExecutableCommandManager.initialize();
    }
}
