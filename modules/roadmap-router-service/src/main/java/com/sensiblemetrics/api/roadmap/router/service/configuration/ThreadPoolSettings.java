package com.sensiblemetrics.api.roadmap.router.service.configuration;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ThreadPoolSettings {
    /**
     * Default thread pool name (should be loaded from properties)
     */
    private static final String DEFAULT_THREAD_POOL_NAME = "roadmap";
    /**
     * Default thread pool size (should be loaded from properties)
     */
    private static final int DEFAULT_THREAD_POOL_SIZE = 10;

    public static String getThreadPoolName() {
        return DEFAULT_THREAD_POOL_NAME;
    }

    public static int getThreadPoolSize() {
        return DEFAULT_THREAD_POOL_SIZE;
    }
}
