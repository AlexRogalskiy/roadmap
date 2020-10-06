package com.sensiblemetrics.api.roadmap.router.service.model.domain;

import com.sensiblemetrics.api.roadmap.router.service.enumeration.StatusType;
import lombok.Builder;

@Builder
public class Response<T> {
    private final StatusType status;
    private final T body;
    private final String message;

    public static <T> Response<T> processing() {
        return Response.<T>builder()
            .status(StatusType.PROCESSING)
            .build();
    }

    public static <T> Response<T> ok(final T body) {
        return Response.<T>builder()
            .status(StatusType.OK)
            .body(body)
            .build();
    }

    public static <T> Response<T> failed(final String message) {
        return Response.<T>builder()
            .status(StatusType.FAILED)
            .message(message)
            .build();
    }

    public static <T> Response<T> notFound(final String message) {
        return Response.<T>builder()
            .status(StatusType.NOT_FOUND)
            .message(message)
            .build();
    }
}
