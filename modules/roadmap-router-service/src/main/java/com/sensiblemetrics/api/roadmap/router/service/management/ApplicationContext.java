package com.sensiblemetrics.api.roadmap.router.service.management;

import com.sensiblemetrics.api.roadmap.commons.executor.QueueingThreadPoolExecutor;
import com.sensiblemetrics.api.roadmap.router.service.configuration.RouterConfiguration;
import com.sensiblemetrics.api.roadmap.router.service.storage.DataStorage;
import lombok.Getter;

@Getter
public class ApplicationContext {

    private final EndpointRegistry registry;
    private final EndpointRoutingRegistry endpointRoutingRegistry;
    private final ConfigurationProperties configurationProperties;
    private final RouterConfiguration routerConfiguration;

    /**
     * Default private application context constructor
     */
    private ApplicationContext() {
        this.registry = new EndpointRegistry();
        this.endpointRoutingRegistry = new EndpointRoutingRegistry();
        this.configurationProperties = ConfigurationProperties.getInstance();

        final QueueingThreadPoolExecutor executor = QueueingThreadPoolExecutor.createInstance(
            this.configurationProperties.getThreadPoolName(),
            this.configurationProperties.getThreadPoolSize()
        );
        final DataStorage storage = DataStorage.createStorage(this.configurationProperties.getStorageInitialSize());

        this.routerConfiguration = RouterConfiguration.getInstance(executor, storage);
    }

    /**
     * /**
     * Returns new default {@link ApplicationContext}
     *
     * @return default {@link ApplicationContext}
     */
    public static ApplicationContext newInstance() {
        return new ApplicationContext();
    }
}
