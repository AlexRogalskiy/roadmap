package com.sensiblemetrics.api.roadmap.router.service.configuration;

import com.sensiblemetrics.api.roadmap.commons.executor.QueueingThreadPoolExecutor;
import com.sensiblemetrics.api.roadmap.router.service.controller.impl.CityControllerImpl;
import com.sensiblemetrics.api.roadmap.router.service.controller.impl.RoadControllerImpl;
import com.sensiblemetrics.api.roadmap.router.service.controller.interfaces.CityController;
import com.sensiblemetrics.api.roadmap.router.service.controller.interfaces.RoadController;
import com.sensiblemetrics.api.roadmap.router.service.repository.impl.CityRepositoryImpl;
import com.sensiblemetrics.api.roadmap.router.service.repository.impl.RoadRepositoryImpl;
import com.sensiblemetrics.api.roadmap.router.service.repository.interfaces.CityRepository;
import com.sensiblemetrics.api.roadmap.router.service.repository.interfaces.RoadRepository;
import com.sensiblemetrics.api.roadmap.router.service.service.impl.CityModelServiceImpl;
import com.sensiblemetrics.api.roadmap.router.service.service.impl.RoadModelServiceImpl;
import com.sensiblemetrics.api.roadmap.router.service.service.interfaces.CityModelService;
import com.sensiblemetrics.api.roadmap.router.service.service.interfaces.RoadModelService;
import com.sensiblemetrics.api.roadmap.router.service.storage.DataStorage;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicReference;

@Slf4j
@Getter
public class RouterConfiguration {
    /**
     * Default {@link AtomicReference} initialization flag
     */
    private static final AtomicReference<Boolean> hasInitialized = new AtomicReference<>(false);

    private static RouterConfiguration routerConfiguration;

    private final CityRepository cityRepository;
    private final RoadRepository roadRepository;

    private final CityModelService cityService;
    private final RoadModelService roadService;

    private final CityController cityController;
    private final RoadController roadController;

    private RouterConfiguration(final QueueingThreadPoolExecutor executor,
                                final DataStorage storage) {
        this.cityRepository = this.getCityRepository(executor, storage);
        this.roadRepository = this.getRoadRepository(executor, storage);

        this.cityService = this.getCityService(this.cityRepository);
        this.roadService = this.getRoadService(this.roadRepository);

        this.cityController = this.getCityController(this.cityService);
        this.roadController = this.getRoadController(this.roadService);
    }

    public CityController getCityController(final CityModelService cityService) {
        return new CityControllerImpl(cityService);
    }

    public RoadController getRoadController(final RoadModelService roadService) {
        return new RoadControllerImpl(roadService);
    }

    public CityModelService getCityService(final CityRepository cityRepository) {
        return new CityModelServiceImpl(cityRepository);
    }

    public RoadModelService getRoadService(final RoadRepository roadRepository) {
        return new RoadModelServiceImpl(roadRepository);
    }

    public CityRepository getCityRepository(final QueueingThreadPoolExecutor executor,
                                            final DataStorage dataStorage) {
        return new CityRepositoryImpl(executor, dataStorage);
    }

    public RoadRepository getRoadRepository(final QueueingThreadPoolExecutor executor,
                                            final DataStorage dataStorage) {
        return new RoadRepositoryImpl(executor, dataStorage);
    }

    /**
     * Returns lazy initialized {@link RouterConfiguration} instance
     *
     * @return {@link RouterConfiguration} instance
     */
    public static RouterConfiguration getInstance(final QueueingThreadPoolExecutor executor,
                                                  final DataStorage storage) {
        if (hasInitialized.compareAndSet(false, true)) {
            log.info("Router configuration has been initialized");
            routerConfiguration = new RouterConfiguration(executor, storage);
        }
        return routerConfiguration;
    }
}
