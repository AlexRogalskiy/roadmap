package com.sensiblemetrics.api.roadmap.router.service.service.impl;

import com.sensiblemetrics.api.roadmap.router.service.model.entity.CityEntity;
import com.sensiblemetrics.api.roadmap.router.service.repository.interfaces.CityRepository;
import com.sensiblemetrics.api.roadmap.router.service.service.interfaces.CityService;
import lombok.RequiredArgsConstructor;

import java.util.Optional;
import java.util.UUID;

import static com.sensiblemetrics.api.roadmap.commons.utils.FutureUtils.DEFAULT_COMPLETABLE_LOG_ACTION;

/**
 * {@link CityEntity} base model service implementation
 */
@RequiredArgsConstructor
public class CityServiceImpl extends BaseModelServiceImpl<CityEntity, UUID> implements CityService {

    private final CityRepository cityRepository;

    @Override
    public Optional<CityEntity> findCityByName(final String name) {
        return this.getRepository().findCityByName(name)
            .whenCompleteAsync(DEFAULT_COMPLETABLE_LOG_ACTION)
            .join();
    }

    @Override
    public CityRepository getRepository() {
        return this.cityRepository;
    }
}
