package com.sensiblemetrics.api.roadmap.router.service.repository.impl;

import com.sensiblemetrics.api.roadmap.router.service.interfaces.Identifiable;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

import static java.util.stream.Collectors.toList;

/**
 * {@link Map} {@link Identifiable} implementation
 *
 * @param <K> type of configurable entity key
 * @param <V> type of configurable entity value
 */
@RequiredArgsConstructor
public class MapRepositoryImpl<K, V extends Identifiable<K>> {

    private final Map<K, V> service;

    public void delete(final K key) {
        this.service.remove(key);
    }

    public boolean exists(final K key) {
        return this.service.containsKey(key);
    }

    public void add(final V model) {
        this.service.put(model.getId(), model);
    }

    public V get(final K key) {
        return this.service.get(key);
    }

    public V first() {
        return this.service.values().stream().findFirst().orElse(null);
    }

    public List<V> where(final Predicate<V> criteria) {
        return this.service.values().stream().filter(criteria).collect(toList());
    }
}
