package com.sensiblemetrics.api.roadmap.router.service.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.googlecode.jmapper.annotations.JMap;
import lombok.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class CityModelDto extends BaseModelDto<UUID> {

    @JMap
    @JsonProperty(value = "name", required = true)
    @NotBlank(message = "{model.dto.city.name.notBlank}")
    private String name;

    @JMap("roadList")
    @Valid
    @JsonProperty(value = "roads", required = true)
    private List<@NotNull RoadModelDto> roads;
}
