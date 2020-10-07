package com.sensiblemetrics.api.roadmap.router.service.repository.impl;

import com.sensiblemetrics.api.roadmap.commons.exception.ResourceAlreadyExistException;
import com.sensiblemetrics.api.roadmap.commons.executor.QueueingThreadPoolExecutor;
import com.sensiblemetrics.api.roadmap.router.service.model.entity.CityModelEntity;
import com.sensiblemetrics.api.roadmap.router.service.repository.interfaces.CityRepository;
import com.sensiblemetrics.api.roadmap.router.service.storage.DataStorage;
import org.apache.commons.compress.utils.Lists;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import static java.lang.String.format;

/**
 * {@link CityModelEntity} repository implementation
 */
public class CityRepositoryImpl extends BaseModelRepositoryImpl<CityModelEntity, UUID> implements CityRepository {

    public CityRepositoryImpl(final QueueingThreadPoolExecutor queueingThreadPoolExecutor,
                              final DataStorage storage) {
        super(queueingThreadPoolExecutor, storage);
    }

    @Override
    public CompletableFuture<Optional<CityModelEntity>> findCityByName(final String name) {
        return CompletableFuture.supplyAsync(() -> this.getCity(name), this.queueingThreadPoolExecutor);
    }

    @Override
    public <S extends CityModelEntity> S save(final S value) {
        if (this.storage.getData().containsKey(value)) {
            throw ResourceAlreadyExistException.throwError(format("Resource already exists: {%s}", value));
        }
        this.storage.getData().put(value, Lists.newArrayList());
        return value;
    }

    private Optional<CityModelEntity> getCity(final String name) {
        return this.storage.getData()
            .keySet()
            .stream()
            .filter(e -> e.getName().equals(name))
            .findFirst();
    }
}
