package com.dev.domain.dao;

import com.dev.domain.model.feature.Feature;
import com.dev.domain.model.feature.FeatureType;
import com.dev.domain.model.feature.MeasurementType;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface FeatureRepository extends CrudRepository<Feature, Long> {
    List<Feature> findAllByMeasurementType(MeasurementType measurementType);

    List<Feature> findAllByFeatureType(FeatureType featureType);
}
