package com.dev.domain.model;

import com.dev.domain.model.doctor.Doctor;
import com.dev.domain.model.network.NetworkModelColumnDefinition;
import com.dev.domain.model.network.Perceptron;
import com.dev.domain.model.network.RadialBasisFunctionsNetwork;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Entity
public class NetworkModel implements Serializable {
    @Id
    @GeneratedValue
    private long id;
    private String name;
    private String description;
    private Date dateOfCreation;

    private boolean isPerceptronModel;

    @Lob
    private Perceptron perceptron;

    @Lob
    private RadialBasisFunctionsNetwork rbfNetwork;

    private double error;

    @ManyToOne
    @JoinColumn(name = "doctorId")
    private Doctor owner;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "inNetworkModel", cascade = CascadeType.ALL)
    private List<NetworkModelColumnDefinition> inputColumns;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "outNetworkModel", cascade = CascadeType.ALL)
    private List<NetworkModelColumnDefinition> outColumns;

    @ElementCollection
    @CollectionTable(name = "in_out_values", joinColumns = @JoinColumn(name = "model_id"))
    @Column(name = "max_in")
    private List<Double> maxIns;

    @ElementCollection
    @CollectionTable(name = "in_out_values", joinColumns = @JoinColumn(name = "model_id"))
    @Column(name = "min_in")
    private List<Double> minIns;

    @ElementCollection
    @CollectionTable(name = "in_out_values", joinColumns = @JoinColumn(name = "model_id"))
    @Column(name = "max_out")
    private List<Double> maxOuts;

    @ElementCollection
    @CollectionTable(name = "in_out_values", joinColumns = @JoinColumn(name = "model_id"))
    @Column(name = "min_out")
    private List<Double> minOuts;

    public NetworkModel() {
        this.dateOfCreation = new Date();
        this.inputColumns = new ArrayList<>();
        this.outColumns = new ArrayList<>();

        this.maxIns = new ArrayList<>();
        this.minIns = new ArrayList<>();

        this.maxOuts = new ArrayList<>();
        this.minOuts = new ArrayList<>();
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

    public Perceptron getPerceptron() {
        return perceptron;
    }

    public void setPerceptron(Perceptron perceptron) {
        this.perceptron = perceptron;
    }

    public double getError() {
        return error;
    }

    public void setError(double error) {
        this.error = error;
    }

    public Doctor getOwner() {
        return owner;
    }

    public void setOwner(Doctor owner) {
        this.owner = owner;
    }

    public boolean isPerceptronModel() {
        return isPerceptronModel;
    }

    public void setPerceptronModel(boolean perceptronModel) {
        isPerceptronModel = perceptronModel;
    }

    public boolean getIsPerceptronModel() {
        return isPerceptronModel;
    }

    public RadialBasisFunctionsNetwork getRbfNetwork() {
        return rbfNetwork;
    }

    public void setRbfNetwork(RadialBasisFunctionsNetwork rbfNetwork) {
        this.rbfNetwork = rbfNetwork;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDateOfCreation() {
        return dateOfCreation;
    }

    public void setDateOfCreation(Date dateOfCreation) {
        this.dateOfCreation = dateOfCreation;
    }

    public List<NetworkModelColumnDefinition> getInputColumns() {
        return inputColumns;
    }

    public void setInputColumns(List<NetworkModelColumnDefinition> inputColumns) {
        this.inputColumns = inputColumns;
    }

    public List<NetworkModelColumnDefinition> getOutColumns() {
        return outColumns;
    }

    public void setOutColumns(List<NetworkModelColumnDefinition> outColumns) {
        this.outColumns = outColumns;
    }

    public List<Double> getMaxIns() {
        return maxIns;
    }

    public void setMaxIns(List<Double> maxIns) {
        this.maxIns = maxIns;
    }

    public List<Double> getMinIns() {
        return minIns;
    }

    public void setMinIns(List<Double> minIns) {
        this.minIns = minIns;
    }

    public List<Double> getMaxOuts() {
        return maxOuts;
    }

    public void setMaxOuts(List<Double> maxOuts) {
        this.maxOuts = maxOuts;
    }

    public List<Double> getMinOuts() {
        return minOuts;
    }

    public void setMinOuts(List<Double> minOuts) {
        this.minOuts = minOuts;
    }

    public List<String> getInColumnsNames() {
        return inputColumns.stream().map(NetworkModelColumnDefinition::getName).collect(Collectors.toList());
    }

    public List<String> getOutColumnsNames() {
        return outColumns.stream().map(NetworkModelColumnDefinition::getName).collect(Collectors.toList());
    }
}
