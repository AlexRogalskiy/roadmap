package com.sensiblemetrics.api.roadmap.router.service.model.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import org.apache.http.HttpStatus;

@Data
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class Response<T> {
    /**
     * Response status code
     */
    @JsonProperty(value = "code")
    private final int status;
    /**
     * Response body
     */
    @JsonProperty(value = "payload")
    private final T body;
    /**
     * Response message
     */
    @JsonProperty(value = "description")
    private final String message;

    /**
     * Returns accepted {@link Response}
     *
     * @param <T> type of configurable body
     * @return accepted {@link Response}
     */
    public static <T> Response<T> accepted() {
        return Response.<T>builder()
            .status(HttpStatus.SC_ACCEPTED)
            .build();
    }

    /**
     * Returns ok {@link Response}
     *
     * @param <T> type of configurable body
     * @return ok {@link Response}
     */
    public static <T> Response<T> ok(final T body) {
        return Response.<T>builder()
            .status(HttpStatus.SC_OK)
            .body(body)
            .build();
    }

    /**
     * Returns failed {@link Response}
     *
     * @param <T> type of configurable body
     * @return failed {@link Response}
     */
    public static <T> Response<T> failed(final String message) {
        return Response.<T>builder()
            .status(HttpStatus.SC_BAD_REQUEST)
            .message(message)
            .build();
    }

    /**
     * Returns not-found {@link Response}
     *
     * @param <T> type of configurable body
     * @return not-found {@link Response}
     */
    public static <T> Response<T> notFound(final String message) {
        return Response.<T>builder()
            .status(HttpStatus.SC_NOT_FOUND)
            .message(message)
            .build();
    }

    /**
     * Returns no-content {@link Response}
     *
     * @param <T> type of configurable body
     * @return no-content {@link Response}
     */
    public static <T> Response<T> noContent() {
        return Response.<T>builder()
            .status(HttpStatus.SC_NO_CONTENT)
            .build();
    }
}
