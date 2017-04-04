package com.dev.domain.model.feature;

import com.dev.domain.model.questionnaire.QuestionnaireItem;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Feature {
    @Id
    @GeneratedValue
    private long id;

    private String name;
    private String description;

    @Enumerated(EnumType.STRING)
    private MeasurementType measurementType;

    @Enumerated(EnumType.STRING)
    private FeatureType featureType;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "feature")
    private List<QuestionnaireItem> questionnaireItems = new ArrayList<>();

    public Feature() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public MeasurementType getMeasurementType() {
        return measurementType;
    }

    public void setMeasurementType(MeasurementType measurementType) {
        this.measurementType = measurementType;
    }

    public FeatureType getFeatureType() {
        return featureType;
    }

    public void setFeatureType(FeatureType featureType) {
        this.featureType = featureType;
    }
}
