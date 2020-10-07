package com.sensiblemetrics.api.roadmap.router.service.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.googlecode.jmapper.annotations.JMap;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class CoordinateDto<T> implements Serializable {

    @JMap("positionX")
    @JsonProperty(value = "x-coordinate", required = true)
    @NotNull(message = "{model.dto.coordinate.point-x.notNull}")
    private T pointX;

    @JMap("positionY")
    @JsonProperty(value = "y-coordinate", required = true)
    @NotNull(message = "{model.dto.coordinate.point-y.notNull}")
    private T pointY;
}
