package com.sensiblemetrics.api.roadmap.router.service.configuration;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ApplicationRunner extends Thread {
    private final String[] args;

    public ApplicationRunner(final String[] args) {
        this.args = args;
        this.initializeApplicationContext();
    }

    private void initializeApplicationContext() {
//        final QueueingThreadPoolExecutor executor = RouterConfiguration.threadPoolExecutor(getThreadPoolName(), getThreadPoolSize());
//        final DataStorage dataStorage = RouterConfiguration.getStorage(100);
//
//        final CityRepository cityRepository = RouterConfiguration.cityRepository(executor, dataStorage);
//        final RoadRepository roadRepository = RouterConfiguration.roadRepository(executor, dataStorage);
//
//        final CityService cityService = RouterConfiguration.cityService(cityRepository);
//        final RoadService roadService = RouterConfiguration.roadService(roadRepository);
//
//        final CityController cityController = RouterConfiguration.cityController(cityService);
//        final RoadController roadController = RouterConfiguration.roadController(roadService);
    }

    @Override
    public void start() {
        log.info("Starting application runner...");
    }
}
