package com.sensiblemetrics.api.roadmap.router.service.model.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
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
public class City extends BaseModel<UUID> {
    /**
     * Default explicit serialVersionUID for interoperability
     */
    private static final long serialVersionUID = 7405742923086757007L;

    /**
     * City unique name
     */
    @NotBlank
    private String name;

    /**
     * City coordinate
     */
    private Coordinate<BigDecimal> coordinate;

    /**
     * Roads connected with the city
     */
    private List<Road> roadList;

    /**
     * Adds new {@link Road} to city
     *
     * @param road initial input {@link Road} to add
     */
    public void addRoad(final Road road) {
        Optional.ofNullable(road)
            .ifPresent(this.roadList::add);
    }

    /**
     * Adds collection of {@link Road}s to city
     *
     * @param roads initial input {@link Collection} of {@link Road}s to add
     */
    public void addRoads(final Collection<Road> roads) {
        Optional.ofNullable(roads)
            .ifPresent(v -> v.forEach(this::addRoad));
    }
}
