package com.sensiblemetrics.api.roadmap.router.service.service.impl;

import com.sensiblemetrics.api.roadmap.router.service.model.entity.RoadEntity;
import com.sensiblemetrics.api.roadmap.router.service.repository.interfaces.RoadRepository;
import com.sensiblemetrics.api.roadmap.router.service.service.interfaces.RoadService;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

import static com.sensiblemetrics.api.roadmap.commons.utils.ServiceUtils.DEFAULT_COMPLETABLE_LOG_ACTION;

/**
 * {@link RoadEntity} base model service implementation
 */
@RequiredArgsConstructor
public class RoadServiceImpl extends BaseModelServiceImpl<RoadEntity, UUID> implements RoadService {

    private final RoadRepository roadRepository;

    @Override
    public Iterable<RoadEntity> findRoadsByCityName(final String name) {
        return this.getRepository().findRoadsByCityName(name)
            .whenCompleteAsync(DEFAULT_COMPLETABLE_LOG_ACTION)
            .join();
    }

    @Override
    public RoadRepository getRepository() {
        return this.roadRepository;
    }
}
