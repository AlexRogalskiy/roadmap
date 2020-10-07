package com.sensiblemetrics.api.roadmap.router.service.repository.impl;

import com.sensiblemetrics.api.roadmap.commons.executor.QueueingThreadPoolExecutor;
import com.sensiblemetrics.api.roadmap.router.service.model.entity.BaseModelEntity;
import com.sensiblemetrics.api.roadmap.router.service.repository.interfaces.BaseModelRepository;
import com.sensiblemetrics.api.roadmap.router.service.storage.DataStorage;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

/**
 * Abstract base model repository
 *
 * @param <E>  type of {@link Serializable} entity
 * @param <ID> type of {@link Serializable} entity identifier {@link Serializable}
 */
@RequiredArgsConstructor
public abstract class BaseModelRepositoryImpl<E extends BaseModelEntity<ID>, ID extends Serializable> implements BaseModelRepository<E, ID> {
    protected final QueueingThreadPoolExecutor queueingThreadPoolExecutor;
    protected final DataStorage storage;

    /**
     * Returns collection of {@link Serializable} models as {@link Stream} by query
     *
     * @return {@link Stream} of {@link Serializable} models
     */
    @Override
    public <S extends E> CompletableFuture<Stream<S>> streamAll() {
        return null;
    }

    @Override
    public Optional<E> findById(final ID id) {
        return Optional.empty();
    }

    @Override
    public Iterable<E> findAllById(final Iterable<ID> ids) {
        return null;
    }

    @Override
    public boolean existsById(final ID id) {
        return false;
    }

    @Override
    public <S extends E> Iterable<S> saveAll(final Iterable<S> entities) {
        return null;
    }

    @Override
    public <S extends E> S save(final S entity) {
        return null;
    }

    @Override
    public <S extends E> void delete(final S entity) {
    }

    @Override
    public <S extends E> void deleteAll(final Iterable<S> entities) {
    }
}
