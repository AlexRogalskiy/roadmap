package com.sensiblemetrics.api.roadmap.router.service;

import com.sensiblemetrics.api.roadmap.router.service.configuration.ApplicationRunner;

public class ApplicationBootstrap {

    public static void main(final String[] args) {
        new ApplicationRunner(args).start();
    }
}
