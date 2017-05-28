package com.dev.domain.model.network;

import com.dev.domain.model.NetworkModel;
import com.dev.domain.model.spreadsheet.ColumnType;
import com.dev.domain.model.spreadsheet.MeasurementType;

import javax.persistence.*;

@Entity
public class NetworkModelColumnDefinition {
    @Id
    @GeneratedValue
    private long id;

    private String name;
    private MeasurementType measurementType;
    private ColumnType columnType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "in_model_id")
    private NetworkModel inNetworkModel;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "out_model_id")
    private NetworkModel outNetworkModel;

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

    public MeasurementType getMeasurementType() {
        return measurementType;
    }

    public void setMeasurementType(MeasurementType measurementType) {
        this.measurementType = measurementType;
    }

    public ColumnType getColumnType() {
        return columnType;
    }

    public void setColumnType(ColumnType columnType) {
        this.columnType = columnType;
    }

    public NetworkModel getInNetworkModel() {
        return inNetworkModel;
    }

    public void setInNetworkModel(NetworkModel inNetworkModel) {
        this.inNetworkModel = inNetworkModel;
    }

    public NetworkModel getOutNetworkModel() {
        return outNetworkModel;
    }

    public void setOutNetworkModel(NetworkModel outNetworkModel) {
        this.outNetworkModel = outNetworkModel;
    }
}
