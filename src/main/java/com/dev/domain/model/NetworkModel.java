package com.dev.domain.model;

import com.dev.domain.model.doctor.Doctor;
import com.dev.domain.model.network.Perceptron;
import com.dev.domain.model.network.RadialBasisFunctionsNetwork;
import org.encog.persist.EncogDirectoryPersistence;

import javax.persistence.*;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Date;

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
    private ActivationFunction hiddenActivationFunction;
    private ActivationFunction outActivationFunction;

    private String hiddenFuncFormatted;
    private String outFuncFormatted;

    @ManyToOne
    @JoinColumn(name = "doctorId")
    private Doctor owner;

    public NetworkModel() {
        this.dateOfCreation = new Date();
    }

    private void writeObject(ObjectOutputStream oos) throws IOException {
        EncogDirectoryPersistence.saveObject(oos,
                isPerceptronModel() ? perceptron.getNetwork() : rbfNetwork.getNetwork());
        oos.writeUTF(name);
        oos.writeUTF(description);
        oos.writeBoolean(isPerceptronModel);
        oos.writeDouble(error);
        oos.writeObject(hiddenActivationFunction);
        oos.writeObject(outActivationFunction);
        oos.writeUTF(hiddenFuncFormatted);
        oos.writeUTF(outFuncFormatted);
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

    public ActivationFunction getHiddenActivationFunction() {
        return hiddenActivationFunction;
    }

    public void setHiddenActivationFunction(ActivationFunction hiddenActivationFunction) {
        this.hiddenActivationFunction = hiddenActivationFunction;
    }

    public ActivationFunction getOutActivationFunction() {
        return outActivationFunction;
    }

    public void setOutActivationFunction(ActivationFunction outActivationFunction) {
        this.outActivationFunction = outActivationFunction;
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

    public String getHiddenFuncFormatted() {
        return hiddenFuncFormatted;
    }

    public void setHiddenFuncFormatted(String hiddenFuncFormatted) {
        this.hiddenFuncFormatted = hiddenFuncFormatted;
    }

    public String getOutFuncFormatted() {
        return outFuncFormatted;
    }

    public void setOutFuncFormatted(String outFuncFormatted) {
        this.outFuncFormatted = outFuncFormatted;
    }
}
