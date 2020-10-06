package com.sensiblemetrics.api.roadmap.router.service.configuration;

import com.sensiblemetrics.api.roadmap.commons.executor.QueueingThreadPoolExecutor;
import com.sensiblemetrics.api.roadmap.router.service.controller.CityController;
import com.sensiblemetrics.api.roadmap.router.service.controller.CityControllerImpl;
import com.sensiblemetrics.api.roadmap.router.service.controller.RoadController;
import com.sensiblemetrics.api.roadmap.router.service.controller.RoadControllerImpl;
import com.sensiblemetrics.api.roadmap.router.service.repository.CityRepository;
import com.sensiblemetrics.api.roadmap.router.service.repository.CityRepositoryImpl;
import com.sensiblemetrics.api.roadmap.router.service.repository.RoadRepository;
import com.sensiblemetrics.api.roadmap.router.service.repository.RoadRepositoryImpl;
import com.sensiblemetrics.api.roadmap.router.service.service.CityService;
import com.sensiblemetrics.api.roadmap.router.service.service.CityServiceImpl;
import com.sensiblemetrics.api.roadmap.router.service.service.RoadService;
import com.sensiblemetrics.api.roadmap.router.service.service.RoadServiceImpl;
import com.sensiblemetrics.api.roadmap.router.service.storage.DataStorage;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RouterConfiguration {

    public static QueueingThreadPoolExecutor threadPoolExecutor(final String name,
                                                                final int poolSize) {
        return QueueingThreadPoolExecutor.createInstance(name, poolSize);
    }

    public static CityController cityController(final CityService cityService) {
        return new CityControllerImpl(cityService);
    }

    public static RoadController roadController(final RoadService roadService) {
        return new RoadControllerImpl(roadService);
    }

    public static CityService cityService(final CityRepository cityRepository) {
        return new CityServiceImpl(cityRepository);
    }

    public static RoadService roadService(final RoadRepository roadRepository) {
        return new RoadServiceImpl(roadRepository);
    }

    public static CityRepository cityRepository(final QueueingThreadPoolExecutor executor,
                                                final DataStorage dataStorage) {
        return new CityRepositoryImpl(executor, dataStorage);
    }

    public static RoadRepository roadRepository(final QueueingThreadPoolExecutor executor,
                                                final DataStorage dataStorage) {
        return new RoadRepositoryImpl(executor, dataStorage);
    }

    public static DataStorage getStorage(final int size) {
        return DataStorage.createStorage(size);
    }
}
