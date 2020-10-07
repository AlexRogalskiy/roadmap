package com.sensiblemetrics.api.roadmap.router.service.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.sensiblemetrics.api.roadmap.router.service.model.entity.CityEntity;
import lombok.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.UUID;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class RoadDto extends BaseModelDto<UUID> {

    @JsonProperty(value = "name", required = true)
    @NotBlank(message = "{model.dto.road.name.notBlank}")
    private String name;

    @JsonProperty(value = "length", required = true)
    @Positive(message = "{model.dto.road.length.positive}")
    private long length;

    @Valid
    @JsonProperty(value = "cities", required = true)
    @NotNull(message = "{model.dto.road.cities.notNull}")
    private CoordinateDto<@NotNull CityEntity> coordinate;
}
