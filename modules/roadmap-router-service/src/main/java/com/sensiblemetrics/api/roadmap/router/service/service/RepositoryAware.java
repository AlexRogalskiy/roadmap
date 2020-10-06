package com.sensiblemetrics.api.roadmap.router.service.service;

import com.sensiblemetrics.api.roadmap.router.service.model.entity.BaseModel;
import com.sensiblemetrics.api.roadmap.router.service.repository.BaseModelRepository;

import java.io.Serializable;

/**
 * Repository aware interface declaration
 *
 * @param <E>  type of configuration model
 * @param <ID> type of configuration model {@link Serializable} identifier
 */
@FunctionalInterface
public interface RepositoryAware<E extends BaseModel<ID>, ID extends Serializable> {
    /**
     * Returns {@link BaseModelRepository}
     *
     * @return {@link BaseModelRepository}
     */
    BaseModelRepository<E, ID> getRepository();
}
