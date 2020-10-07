package com.sensiblemetrics.api.roadmap.router.service.model.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.UUID;

/**
 * Road base model
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class RoadEntity extends BaseModelEntity<UUID> {
    /**
     * Default explicit serialVersionUID for interoperability
     */
    private static final long serialVersionUID = 2357497621243062962L;

    /**
     * Road unique name
     */
    @NotBlank(message = "{model.entity.road.name.notBlank}")
    private String name;

    /**
     * Road length
     */
    @Positive(message = "{model.entity.road.length.positive}")
    private long length;

    /**
     * City coordinate
     */
    @Valid
    @NotNull(message = "{model.entity.road.cities.notNull}")
    private CoordinateEntity<@NotNull CityEntity> coordinate;
}
