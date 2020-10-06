package com.sensiblemetrics.api.roadmap.router.service.configuration;

import com.sensiblemetrics.api.roadmap.commons.executor.QueueingThreadPoolExecutor;
import com.sensiblemetrics.api.roadmap.router.service.controller.interfaces.CityController;
import com.sensiblemetrics.api.roadmap.router.service.controller.interfaces.RoadController;
import com.sensiblemetrics.api.roadmap.router.service.repository.interfaces.CityRepository;
import com.sensiblemetrics.api.roadmap.router.service.repository.interfaces.RoadRepository;
import com.sensiblemetrics.api.roadmap.router.service.service.interfaces.CityService;
import com.sensiblemetrics.api.roadmap.router.service.service.interfaces.RoadService;
import com.sensiblemetrics.api.roadmap.router.service.storage.DataStorage;
import lombok.extern.slf4j.Slf4j;

import static com.sensiblemetrics.api.roadmap.router.service.configuration.ThreadPoolSettings.getThreadPoolName;
import static com.sensiblemetrics.api.roadmap.router.service.configuration.ThreadPoolSettings.getThreadPoolSize;

@Slf4j
public class ApplicationRunner extends Thread {
    private final String[] args;

    public ApplicationRunner(final String[] args) {
        this.args = args;
        this.initializeApplicationContext();
    }

    private void initializeApplicationContext() {
        final QueueingThreadPoolExecutor executor = RouterConfiguration.threadPoolExecutor(getThreadPoolName(), getThreadPoolSize());
        final DataStorage dataStorage = RouterConfiguration.getStorage(100);

        final CityRepository cityRepository = RouterConfiguration.cityRepository(executor, dataStorage);
        final RoadRepository roadRepository = RouterConfiguration.roadRepository(executor, dataStorage);

        final CityService cityService = RouterConfiguration.cityService(cityRepository);
        final RoadService roadService = RouterConfiguration.roadService(roadRepository);

        final CityController cityController = RouterConfiguration.cityController(cityService);
        final RoadController roadController = RouterConfiguration.roadController(roadService);
    }

    @Override
    public void start() {
        log.info("Starting application runner...");

    }
}
