package com.dev.domain.model.doctor;

import com.dev.domain.model.NetworkModel;
import com.dev.domain.model.spreadsheet.Spreadsheet;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Doctor implements Serializable {
    @Id
    @GeneratedValue
    private long id;
    @Column(unique = true)
    private String login;
    private String name;
    private String surname;
    private String middleName;
    private String position;
    private String password;
    private String passwordConfirm;
    private String email;
    @ManyToMany(fetch = FetchType.EAGER)
    private List<Role> roles;
    private boolean enabled;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "owner")
    private List<Spreadsheet> spreadsheets;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "owner")
    private List<NetworkModel> networkModels;

    public Doctor() {
        spreadsheets = new ArrayList<>();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Transient
    public String getPasswordConfirm() {
        return passwordConfirm;
    }

    public void setPasswordConfirm(String passwordConfirm) {
        this.passwordConfirm = passwordConfirm;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public List<Spreadsheet> getSpreadsheets() {
        return spreadsheets;
    }

    public void setSpreadsheets(List<Spreadsheet> spreadsheets) {
        this.spreadsheets = spreadsheets;
    }

    public List<NetworkModel> getNetworkModels() {
        return networkModels;
    }

    public void setNetworkModels(List<NetworkModel> networkModels) {
        this.networkModels = networkModels;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    @Override
    public String toString() {
        return "Doctor{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", middleName='" + middleName + '\'' +
                ", position='" + position + '\'' +
                ", password='" + password + '\'' +
                ", passwordConfirm='" + passwordConfirm + '\'' +
                ", email='" + email + '\'' +
                ", roles=" + roles +
                ", enabled=" + enabled +
                '}';
    }
}