package com.sensiblemetrics.api.roadmap.commons.utils;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;
import java.util.Collection;
import java.util.Objects;
import java.util.concurrent.*;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.stream.Stream;

@Slf4j
@UtilityClass
public class FutureUtils {

    /**
     * Fail safe {@link ScheduledThreadPoolExecutor}
     */
    private static final ScheduledThreadPoolExecutor FAILSAFE_SCHEDULER_EXECUTOR = createFailsafeScheduler();

    /**
     * Creates new fail safe {@link ScheduledThreadPoolExecutor}
     *
     * @return new fail safe {@link ScheduledThreadPoolExecutor}
     */
    private static ScheduledThreadPoolExecutor createFailsafeScheduler() {
        final ScheduledThreadPoolExecutor scheduledThreadPoolExecutor = new ScheduledThreadPoolExecutor(1, createThreadFactory("health-failsafe-scheduler-%d"));
        scheduledThreadPoolExecutor.setRemoveOnCancelPolicy(true);
        return scheduledThreadPoolExecutor;
    }

    /**
     * Default {@link BiConsumer} completable action operator
     */
    public static final BiConsumer<? super Object, ? super Throwable> DEFAULT_COMPLETABLE_LOG_ACTION = (response, error) -> {
        try {
            if (Objects.nonNull(error)) {
                log.info("Canceled completable future request [response={}, error={}]", response, error.getMessage());
            } else {
                log.info("Received completable future response [from={}]", response);
            }
        } catch (RuntimeException e) {
            log.error("ERROR: cannot process completable future request callback", e);
        }
    };

    /**
     * Returns {@link CompletableFuture} by input parameters
     *
     * @param promise  initial input {@link CompletableFuture}
     * @param timeout  initial input {@code long} time out
     * @param timeUnit initial input {@link TimeUnit} value
     * @param <T>      type of configurable future value
     * @return {@link CompletableFuture}
     */
    public static <T> CompletableFuture<T> failAfter(final CompletableFuture<T> promise,
                                                     final long timeout,
                                                     final TimeUnit timeUnit) {
        FAILSAFE_SCHEDULER_EXECUTOR.schedule(
            () -> {
                final TimeoutException ex = new TimeoutException(String.format("Timeout after %s %s", timeout, timeUnit));
                return promise.completeExceptionally(ex);
            },
            timeout,
            timeUnit
        );
        return promise;
    }

    /**
     * Exceptionally completes this CompletableFuture with
     * a {@link TimeoutException} if not otherwise completed
     * before the given timeout.
     *
     * @param promise how long to wait before completing exceptionally
     *                with a TimeoutException, in units of {@code unit}
     * @param timeout a {@code TimeUnit} determining how to interpret the
     *                {@code timeout} parameter
     * @return {@link CompletableFuture} instance
     */
    public static <T> CompletableFuture<T> wrapWithTimeout(final CompletableFuture<T> promise,
                                                           final Duration timeout) {
        final CompletableFuture<T> timeoutPromise = failAfter(timeout);
        return promise.applyToEither(timeoutPromise, Function.identity());
    }

    /**
     * Returns {@link ThreadFactory} by input {@link String} thread name format
     *
     * @param nameFormat initial input {@link String} thread name format
     * @return {@link ThreadFactory}
     */
    public static ThreadFactory createThreadFactory(String nameFormat) {
        return r -> new ThreadFactoryBuilder()
            .setNameFormat(nameFormat)
            .setUncaughtExceptionHandler((t, e) -> log.error("HealthChecks have not been refreshed.", e))
            .build()
            .newThread(r);
    }

    /**
     * Returns {@link CompletableFuture} by input {@link Duration}
     *
     * @param duration initial input {@link Duration} value
     * @param <T>      type of configurable future value
     * @return {@link CompletableFuture}
     */
    public static <T> CompletableFuture<T> failAfter(final Duration duration) {
        final CompletableFuture<T> promise = new CompletableFuture<>();
        return failAfter(promise, duration.toMillis(), TimeUnit.MILLISECONDS);
    }

    /**
     * Returns {@link CompletableFuture} {@link Stream} of {@link T} result values by input collection of {@link CompletableFuture}s
     *
     * @param futures initial input collection of {@link CompletableFuture}s
     * @param <T>     type of configurable result value
     * @return {@link CompletableFuture} {@link Stream} of {@link T} result values
     */
    public static <T> CompletableFuture<Stream<T>> allOf(final Collection<CompletableFuture<T>> futures) {
        final CompletableFuture<Void> allFuturesResult = CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]));
        return allFuturesResult.thenApply(value -> futures.stream().map(CompletableFuture::join));
    }
}
