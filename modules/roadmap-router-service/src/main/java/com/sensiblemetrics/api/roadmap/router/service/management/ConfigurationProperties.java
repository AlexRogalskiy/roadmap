package com.sensiblemetrics.api.roadmap.router.service.management;

import com.sensiblemetrics.api.roadmap.commons.exception.ConfigurationException;
import com.sensiblemetrics.api.roadmap.commons.utils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;

import static com.sensiblemetrics.api.roadmap.commons.utils.PropertyUtils.getPropertyValueAsInt;
import static com.sensiblemetrics.api.roadmap.commons.utils.PropertyUtils.getPropertyValueAsString;

public class ConfigurationProperties {
    /**
     * Default thread pool name
     */
    private static final String DEFAULT_THREAD_POOL_NAME = "roadmap";
    /**
     * Default thread pool size
     */
    private static final int DEFAULT_THREAD_POOL_SIZE = 10;

    /**
     * Returns {@link String} thread pool name
     *
     * @return {@link String} thread pool name
     */
    public String getThreadPoolName() {
        return getPropertyValueAsString(PropertyUtils.getProperty("thread-pool.name"))
            .filter(StringUtils::isNotBlank)
            .orElse(DEFAULT_THREAD_POOL_NAME);
    }

    /**
     * Returns {@code int} thread pool size
     *
     * @return {@code int} thread pool size
     */
    public int getThreadPoolSize() {
        return getPropertyValueAsInt(PropertyUtils.getProperty("thread-pool.size"))
            .filter(PropertyUtils::isPositive)
            .orElse(DEFAULT_THREAD_POOL_SIZE);
    }

    /**
     * Returns {@code int} health check server port
     *
     * @return {@code int} health check server port
     */
    public int getServerPort() {
        return getPropertyValueAsInt(PropertyUtils.getProperty("server.port"))
            .filter(PropertyUtils::isPositive)
            .orElseThrow(() -> new ConfigurationException("Port for HTTP server is invalid or missing"));
    }

    /**
     * Returns {@link String} health check server path
     *
     * @return {@link String} health check server path
     */
    public String getServerContextPath() {
        return getPropertyValueAsString(PropertyUtils.getProperty("server.context-path"))
            .map(value -> !value.startsWith("/") ? "/" + value : value)
            .orElseThrow(() -> new ConfigurationException("Path for HTTP server is invalid or missing"));
    }
}
