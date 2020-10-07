package com.sensiblemetrics.api.roadmap.router.service.model.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * City base model
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class CityModelEntity extends BaseModelEntity<UUID> {
    /**
     * Default explicit serialVersionUID for interoperability
     */
    private static final long serialVersionUID = 7405742923086757007L;

    /**
     * City unique name
     */
    @NotBlank(message = "{model.entity.city.name.notBlank}")
    private String name;

    /**
     * City coordinate
     */
    @Valid
    private CoordinateEntity<@NotNull BigDecimal> coordinate;

    /**
     * Roads connected with the city
     */
    @Valid
    private List<@NotNull RoadModelEntity> roadList;

    /**
     * Adds new {@link RoadModelEntity} to city
     *
     * @param road initial input {@link RoadModelEntity} to add
     */
    public void addRoad(final RoadModelEntity road) {
        Optional.ofNullable(road)
            .ifPresent(this.roadList::add);
    }

    /**
     * Adds collection of {@link RoadModelEntity}s to city
     *
     * @param roads initial input {@link Collection} of {@link RoadModelEntity}s to add
     */
    public void addRoads(final Collection<RoadModelEntity> roads) {
        Optional.ofNullable(roads)
            .ifPresent(v -> v.forEach(this::addRoad));
    }
}
