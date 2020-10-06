package com.sensiblemetrics.api.roadmap.router.service.storage;

import com.sensiblemetrics.api.roadmap.router.service.model.entity.City;
import com.sensiblemetrics.api.roadmap.router.service.model.entity.Road;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DataStorage {
    private final Map<City, List<Road>> cityListMap;

    private DataStorage(final int initialSize) {
        this.cityListMap = new ConcurrentHashMap<>(initialSize);
    }

    public static DataStorage createStorage(final int initialSize) {
        return new DataStorage(initialSize);
    }

    public Map<City, List<Road>> getData() {
        return this.cityListMap;
    }
}
