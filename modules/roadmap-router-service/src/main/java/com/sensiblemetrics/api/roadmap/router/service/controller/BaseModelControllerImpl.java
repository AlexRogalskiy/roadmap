package com.sensiblemetrics.api.roadmap.router.service.controller;

import com.sensiblemetrics.api.roadmap.router.service.model.domain.Response;
import com.sensiblemetrics.api.roadmap.router.service.model.entity.BaseModel;

import java.io.Serializable;

/**
 * Abstract base mode controller implementation
 *
 * @param <E>  type of configuration model
 * @param <ID> type of configuration model {@link Serializable} identifier
 */
public abstract class BaseModelControllerImpl<E extends BaseModel<ID>, ID extends Serializable> implements BaseModelController<E, ID> {

    @Override
    public Response<E> add(final E value) {
        return null;
    }

    @Override
    public Response<E> remove(final E road) {
        return null;
    }
}
