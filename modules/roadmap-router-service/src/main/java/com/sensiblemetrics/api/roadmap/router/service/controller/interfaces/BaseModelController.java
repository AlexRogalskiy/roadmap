package com.sensiblemetrics.api.roadmap.router.service.controller.interfaces;

import com.sensiblemetrics.api.roadmap.router.service.model.domain.Response;
import com.sensiblemetrics.api.roadmap.router.service.model.dto.BaseModelDto;

import java.io.Serializable;

/**
 * Base model controller declaration
 *
 * @param <E>  type of configuration model
 * @param <ID> type of configuration model {@link Serializable} identifier
 */
public interface BaseModelController<E extends BaseModelDto<ID>, ID extends Serializable> {
    /**
     * Returns {@link Response} with stored {@link E} value
     *
     * @param value initial input {@link E} value to store
     * @return {@link Response} with stored {@link E} body
     */
    Response<E> add(final E value);

    /**
     * Returns {@link Response} with removed {@link E} value
     *
     * @param value initial input {@link E} value to remove
     * @return {@link Response} with removed {@link E} body
     */
    Response<E> remove(final E value);
}
