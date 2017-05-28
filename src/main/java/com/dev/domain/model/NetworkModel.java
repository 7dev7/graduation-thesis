package com.dev.domain.model;

import com.dev.domain.model.doctor.Doctor;
import com.dev.domain.model.network.Perceptron;
import com.dev.domain.model.network.RadialBasisFunctionsNetwork;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

    @ElementCollection
    @CollectionTable(name = "model_columns", joinColumns = @JoinColumn(name = "model_id"))
    @Column(name = "inputColumn")
    private List<String> inputColumns;

    @ElementCollection
    @CollectionTable(name = "model_columns", joinColumns = @JoinColumn(name = "model_id"))
    @Column(name = "outColumn")
    private List<String> outColumns;

    public NetworkModel() {
        this.dateOfCreation = new Date();
        this.inputColumns = new ArrayList<>();
        this.outColumns = new ArrayList<>();
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

    public List<String> getInputColumns() {
        return inputColumns;
    }

    public void setInputColumns(List<String> inputColumns) {
        this.inputColumns = inputColumns;
    }

    public List<String> getOutColumns() {
        return outColumns;
    }

    public void setOutColumns(List<String> outColumns) {
        this.outColumns = outColumns;
    }
}
