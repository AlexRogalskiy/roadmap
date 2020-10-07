package com.sensiblemetrics.api.roadmap.router.service.management;

import com.sensiblemetrics.api.roadmap.router.service.configuration.RouterConfiguration;
import lombok.Data;

@Data
public class ApplicationContext {
    private final EndpointRegistry registry;
    private final RouterConfiguration routerConfiguration;
    private final ConfigurationProperties configurationProperties;

    /**
     * Default private health check context constructor
     */
    private ApplicationContext() {
        this.registry = new EndpointRegistry();
        this.routerConfiguration = new RouterConfiguration();
        this.configurationProperties = new ConfigurationProperties();
    }

    /**
     * /**
     * Returns new default {@link ApplicationContext}
     *
     * @return default {@link ApplicationContext}
     */
    public static ApplicationContext create() {
        return new ApplicationContext();
    }
}
