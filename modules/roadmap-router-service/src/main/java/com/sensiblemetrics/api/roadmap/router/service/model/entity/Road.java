package com.sensiblemetrics.api.roadmap.router.service.model.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import java.util.UUID;

/**
 * Road base model
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Road extends BaseModel<UUID> {
    /**
     * Default explicit serialVersionUID for interoperability
     */
    private static final long serialVersionUID = 2357497621243062962L;

    /**
     * Road unique name
     */
    @NotBlank
    private String name;

    /**
     * Road length
     */
    @Positive
    private long length;

    /**
     * City coordinate
     */
    private Coordinate<City> coordinate;
}
