package com.sensiblemetrics.api.roadmap.router.service.service.impl;

import com.sensiblemetrics.api.roadmap.commons.exception.InvalidParameterException;
import com.sensiblemetrics.api.roadmap.commons.exception.ResourceNotFoundException;
import com.sensiblemetrics.api.roadmap.router.service.model.entity.BaseModelEntity;
import com.sensiblemetrics.api.roadmap.router.service.repository.interfaces.BaseModelRepository;
import com.sensiblemetrics.api.roadmap.router.service.service.interfaces.BaseModelService;
import com.sensiblemetrics.api.roadmap.router.service.service.interfaces.RepositoryAware;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.util.Optional;
import java.util.function.Predicate;

/**
 * Abstract base model service implementation
 *
 * @param <E>  type of configuration model
 * @param <ID> type of configuration model {@link Serializable} identifier
 */
@Slf4j
public abstract class BaseModelServiceImpl<E extends BaseModelEntity<ID>, ID extends Serializable> implements BaseModelService<E, ID>, RepositoryAware<E, ID> {
    /**
     * Returns {@link Iterable} collection of {@code E} entities by entities {@code ID} identifiers
     *
     * @param target - initial input {@link Iterable} collection of entities {@code ID}s
     * @return {@link Iterable} collection of {@code E} entities
     */
    @Override
    public Iterable<E> findAll(final Iterable<ID> target) {
        log.info("Fetching all target records by IDs: {}", target);
        return Optional.ofNullable(target)
            .map(this.getRepository()::findAllById)
            .orElseThrow(() -> new ResourceNotFoundException(String.format("Not found: {%s}", target)));
    }

    /**
     * Returns {@code E} entity by input entity {@code ID} identifier
     *
     * @param id - initial input {@code ID} identifier
     * @return {@code E} entity
     */
    @Override
    public Optional<E> findById(ID id) {
        log.info("Fetching target record by ID: {}", id);
        return Optional.ofNullable(id)
            .flatMap(this.getRepository()::findById);
    }

    /**
     * Saves {@code E} entity to storage
     *
     * @param target - initial input {@code E} entity to save
     * @return saved {@code E} entity
     */
    @Override
    public E save(final E target) {
        log.info("Saving target record: {}", target);
        return Optional.ofNullable(target)
            .map(this.getRepository()::save)
            .orElseThrow(() -> new InvalidParameterException(String.format("Invalid parameter: {%s}", target)));
    }

    /**
     * Updates {@code E} entity by input parameters
     *
     * @param id     - initial input entity {@code ID} identifier
     * @param target - initial input entity {@code E} to update
     * @return updated {@code E} entity
     */
    @Override
    public E update(final ID id, final E target) {
        return this.update(id, target, v -> true);
    }

    protected E update(final ID id, final E target, final Predicate<E> predicate) {
        log.info("Updating target record by ID: {}, entity: {}", id, target);
        return this.findById(id)
            .filter(predicate)
            //.map(value -> copyNonNullProperties(target, value))
            .map(this::save)
            .orElseThrow(() -> new ResourceNotFoundException(String.format("Resource not found: {%s}", target)));
    }

    /**
     * Returns {@link Iterable} collection of {@code S} saved entities
     *
     * @param target - initial input {@link Iterable} collection of {@code S} saved entities
     * @return {@link Iterable} collection of {@code S} saved entities
     */
    @Override
    public <S extends E> Iterable<S> saveAll(final Iterable<S> target) {
        log.info("Saving target records: {}", target);
        return Optional.ofNullable(target)
            .map(this.getRepository()::saveAll)
            .orElseThrow(() -> new InvalidParameterException(String.format("Invalid parameter: {%s}", target)));
    }

    /**
     * Checks existence of entity in storage by input entity {@link ID} identifier
     *
     * @param id - initial input entity {@link ID} identifier
     * @return true - if entity exists, false - otherwise
     */
    @Override
    public boolean existsById(final ID id) {
        log.info("Checking existence of target record by ID: {}", id);
        return Optional.ofNullable(id)
            .map(this.getRepository()::existsById)
            .orElseThrow(() -> new ResourceNotFoundException(String.format("Resource not found by ID: {%s}", id)));
    }

    /**
     * Removes input entity {@code E} from storage
     *
     * @param target - initial input {@code E} entity to remove
     */
    @Override
    public E delete(final E target) {
        log.info("Removing target record: {}", target);
        return Optional.ofNullable(target)
            .map(value -> {
                this.getRepository().delete(value);
                return value;
            })
            .orElseThrow(() -> new InvalidParameterException(String.format("Invalid parameter: {%s}", target)));
    }

    /**
     * Removes entity {@code E} from storage by input entity {@link ID} identifier
     *
     * @param id - initial input entity {@link ID} identifier
     * @return removed {@code E} entity
     */
    @Override
    public E deleteById(final ID id) {
        log.info("Removing target record by ID: {}", id);
        return this.findById(id)
            .map(this::delete)
            .orElseThrow(() -> new ResourceNotFoundException(String.format("Resource not found by ID: {%s}", id)));
    }

    /**
     * Removes input {@link Iterable} collection of {@code S} entities from storage
     *
     * @param target - initial input {@link Iterable} collection of {@code S} entities
     * @return {@link Iterable} collection of removed {@code E} entities
     */
    @Override
    public <S extends E> Iterable<S> deleteAll(final Iterable<S> target) {
        log.info("Removing target records: {}", target);
        return Optional.ofNullable(target)
            .map(value -> {
                this.getRepository().deleteAll(value);
                return value;
            })
            .orElseThrow(() -> new InvalidParameterException(String.format("Invalid parameter: {%s}", target)));
    }

    @Override
    public abstract BaseModelRepository<E, ID> getRepository();
}
