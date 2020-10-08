package com.sensiblemetrics.api.roadmap.router.service.service.impl;

import com.sensiblemetrics.api.roadmap.router.service.model.entity.RoadModelEntity;
import com.sensiblemetrics.api.roadmap.router.service.repository.interfaces.RoadRepository;
import com.sensiblemetrics.api.roadmap.router.service.service.interfaces.RoadModelService;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

import static com.sensiblemetrics.api.roadmap.commons.utils.FutureUtils.DEFAULT_COMPLETABLE_LOG_ACTION;

/**
 * {@link RoadModelEntity} base model service implementation
 */
@RequiredArgsConstructor
public class RoadModelServiceImpl extends BaseModelServiceImpl<RoadModelEntity, UUID> implements RoadModelService {

    private final RoadRepository roadRepository;

    @Override
    public Iterable<RoadModelEntity> findRoadsByCityName(final String name) {
        return this.getRepository().findRoadsByCityName(name)
            .whenCompleteAsync(DEFAULT_COMPLETABLE_LOG_ACTION)
            .join();
    }

    @Override
    public RoadRepository getRepository() {
        return this.roadRepository;
    }
}
