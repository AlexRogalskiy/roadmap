package com.sensiblemetrics.api.roadmap.router.service.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.googlecode.jmapper.annotations.JMap;
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
public class RoadModelDto extends BaseModelDto<UUID> {

    @JMap
    @JsonProperty(value = "name", required = true)
    @NotBlank(message = "{model.dto.road.name.notBlank}")
    private String name;

    @JMap
    @JsonProperty(value = "length", required = true)
    @Positive(message = "{model.dto.road.length.positive}")
    private long length;

    @JMap("coordinate")
    @Valid
    @JsonProperty(value = "cities", required = true)
    @NotNull(message = "{model.dto.road.cities.notNull}")
    private CoordinateDto<@NotNull CityModelDto> cities;
}
