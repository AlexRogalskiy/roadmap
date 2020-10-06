package com.sensiblemetrics.api.roadmap.router.service.repository;

import com.sensiblemetrics.api.roadmap.commons.exception.ResourceAlreadyExistException;
import com.sensiblemetrics.api.roadmap.commons.executor.QueueingThreadPoolExecutor;
import com.sensiblemetrics.api.roadmap.router.service.model.entity.City;
import com.sensiblemetrics.api.roadmap.router.service.storage.DataStorage;
import org.apache.commons.compress.utils.Lists;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import static java.lang.String.format;

/**
 * {@link City} repository implementation
 */
public class CityRepositoryImpl extends BaseModelRepositoryImpl<City, UUID> implements CityRepository {

    public CityRepositoryImpl(final QueueingThreadPoolExecutor queueingThreadPoolExecutor,
                              final DataStorage storage) {
        super(queueingThreadPoolExecutor, storage);
    }

    @Override
    public CompletableFuture<Optional<City>> findCityByName(final String name) {
        return CompletableFuture.supplyAsync(() -> this.getCity(name), this.queueingThreadPoolExecutor);
    }

    @Override
    public <S extends City> S save(final S value) {
        if (this.storage.getData().containsKey(value)) {
            throw ResourceAlreadyExistException.throwError(format("Resource already exists: {%s}", value));
        }
        this.storage.getData().put(value, Lists.newArrayList());
        return value;
    }

    private Optional<City> getCity(final String name) {
        return this.storage.getData()
            .keySet()
            .stream()
            .filter(e -> e.getName().equals(name))
            .findFirst();
    }
}
