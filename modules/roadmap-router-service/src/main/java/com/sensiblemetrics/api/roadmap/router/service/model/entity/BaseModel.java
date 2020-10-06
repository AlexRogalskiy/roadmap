package com.sensiblemetrics.api.roadmap.router.service.model.entity;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * Base abstract model
 *
 * @param <ID> type of configurable identifier
 */
@Data
public abstract class BaseModel<ID extends Serializable> implements Serializable {
    /**
     * Model unique identifier
     */
    @NotNull
    private ID id;
}
