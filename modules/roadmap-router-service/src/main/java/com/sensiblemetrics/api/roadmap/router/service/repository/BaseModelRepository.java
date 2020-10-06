package com.sensiblemetrics.api.roadmap.router.service.repository;

import com.sensiblemetrics.api.roadmap.router.service.model.entity.BaseModel;

import java.io.Serializable;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

/**
 * Base model repository declaration
 *
 * @param <E>  type of {@link Serializable} entity
 * @param <ID> type of {@link Serializable} entity identifier {@link Serializable}
 */
public interface BaseModelRepository<E extends BaseModel<ID>, ID extends Serializable> {
    /**
     * Returns collection of {@link Serializable} models as {@link Stream} by query
     *
     * @param <S> type of {@link Serializable} entity
     * @return {@link Stream} of {@link Serializable} models
     */
    <S extends E> CompletableFuture<Stream<S>> streamAll();

    Optional<E> findById(ID id);

    Iterable<E> findAllById(final Iterable<ID> ids);

    boolean existsById(final ID id);

    <S extends E> Iterable<S> saveAll(final Iterable<S> entities);

    <S extends E> S save(final S entity);

    <S extends E> void delete(final S entity);

    <S extends E> void deleteAll(final Iterable<S> entities);
}
