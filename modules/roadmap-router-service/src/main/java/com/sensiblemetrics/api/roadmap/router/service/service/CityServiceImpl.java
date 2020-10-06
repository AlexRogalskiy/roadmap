package com.sensiblemetrics.api.roadmap.router.service.service;

import com.sensiblemetrics.api.roadmap.router.service.model.entity.City;
import com.sensiblemetrics.api.roadmap.router.service.repository.CityRepository;
import lombok.RequiredArgsConstructor;

import java.util.Optional;
import java.util.UUID;

import static com.sensiblemetrics.api.roadmap.commons.utils.ServiceUtils.DEFAULT_COMPLETABLE_LOG_ACTION;

/**
 * {@link City} base model service implementation
 */
@RequiredArgsConstructor
public class CityServiceImpl extends BaseModelServiceImpl<City, UUID> implements CityService {

    private final CityRepository cityRepository;

    @Override
    public Optional<City> findCityByName(final String name) {
        return this.getRepository().findCityByName(name)
            .whenCompleteAsync(DEFAULT_COMPLETABLE_LOG_ACTION)
            .join();
    }

    @Override
    public CityRepository getRepository() {
        return this.cityRepository;
    }
}
