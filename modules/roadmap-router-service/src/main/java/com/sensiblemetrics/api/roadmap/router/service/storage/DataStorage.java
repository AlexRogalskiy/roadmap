package com.sensiblemetrics.api.roadmap.router.service.storage;

import com.sensiblemetrics.api.roadmap.router.service.model.entity.CityEntity;
import com.sensiblemetrics.api.roadmap.router.service.model.entity.RoadEntity;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicReference;

@Slf4j
public class DataStorage {
    /**
     * Default {@link AtomicReference} initialization flag
     */
    private static final AtomicReference<Boolean> hasLoaded = new AtomicReference<>(false);

    private static Map<CityEntity, List<RoadEntity>> cityListMap;

    private DataStorage(final int initialSize) {
        if (!hasLoaded.compareAndSet(false, true)) {
            log.info("DataStorage has already been initialized");
            return;
        }
        cityListMap = new ConcurrentHashMap<>(initialSize);
    }

    public static DataStorage createStorage(final int initialSize) {
        return new DataStorage(initialSize);
    }

    public Map<CityEntity, List<RoadEntity>> getData() {
        return cityListMap;
    }
}
