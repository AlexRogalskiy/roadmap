package com.sensiblemetrics.api.roadmap.router.service.repository.impl;

import com.sensiblemetrics.api.roadmap.commons.executor.QueueingThreadPoolExecutor;
import com.sensiblemetrics.api.roadmap.router.service.model.entity.RoadModelEntity;
import com.sensiblemetrics.api.roadmap.router.service.repository.interfaces.RoadRepository;
import com.sensiblemetrics.api.roadmap.router.service.storage.DataStorage;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

/**
 * {@link RoadModelEntity} repository implementation
 */
public class RoadRepositoryImpl extends BaseModelRepositoryImpl<RoadModelEntity, UUID> implements RoadRepository {

    public RoadRepositoryImpl(final QueueingThreadPoolExecutor queueingThreadPoolExecutor,
                              final DataStorage storage) {
        super(queueingThreadPoolExecutor, storage);
    }

    @Override
    public CompletableFuture<Iterable<RoadModelEntity>> findRoadsByCityName(final String name) {
        return CompletableFuture.supplyAsync(() -> this.getRoadsByCity(name), this.queueingThreadPoolExecutor);
    }

    private List<RoadModelEntity> getRoadsByCity(final String name) {
        return this.storage.getData().entrySet()
            .stream()
            .filter(e -> e.getKey().getName().equals(name))
            .map(Map.Entry::getValue)
            .findFirst()
            .orElseGet(Collections::emptyList);
    }
}
