package com.sensiblemetrics.api.roadmap.commons.interfaces;

import com.google.common.base.Objects;

import java.io.Serializable;
import java.util.List;

/**
 * Identifiable interface declaration
 *
 * @param <T> type of configurable entity
 */
@FunctionalInterface
public interface Identifiable<T> extends Serializable {
    /**
     * Returns {@link T} entity current identifier
     *
     * @return {@link T} entity current identifier
     */
    T getId();

    static <S, T extends Identifiable<S>> boolean exists(final List<T> list,
                                                         final T id) {
        return list.stream()
            .anyMatch(o -> Objects.equal(o.getId(), id));
    }

    static <S, T extends Identifiable<S>> T find(final List<T> list,
                                                 final T id) {
        return list.stream()
            .filter(o -> Objects.equal(o.getId(), id))
            .findFirst()
            .orElse(null);
    }

    static <S, T extends Identifiable<S>> void remove(final List<T> list,
                                                      final T id) {
        list.removeIf(o -> Objects.equal(o.getId(), id));
    }
}
