package com.sensiblemetrics.api.roadmap.router.service.service.impl;

import com.sensiblemetrics.api.roadmap.router.service.model.entity.CityModelEntity;
import com.sensiblemetrics.api.roadmap.router.service.repository.interfaces.CityRepository;
import com.sensiblemetrics.api.roadmap.router.service.service.interfaces.CityService;
import lombok.RequiredArgsConstructor;

import java.util.Optional;
import java.util.UUID;

import static com.sensiblemetrics.api.roadmap.commons.utils.FutureUtils.DEFAULT_COMPLETABLE_LOG_ACTION;

/**
 * {@link CityModelEntity} base model service implementation
 */
@RequiredArgsConstructor
public class CityServiceImpl extends BaseModelServiceImpl<CityModelEntity, UUID> implements CityService {

    private final CityRepository cityRepository;

    @Override
    public Optional<CityModelEntity> findCityByName(final String name) {
        return this.getRepository().findCityByName(name)
            .whenCompleteAsync(DEFAULT_COMPLETABLE_LOG_ACTION)
            .join();
    }

    @Override
    public CityRepository getRepository() {
        return this.cityRepository;
    }
}
