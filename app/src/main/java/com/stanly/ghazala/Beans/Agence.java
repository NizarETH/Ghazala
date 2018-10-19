package com.stanly.ghazala.Beans;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Agence extends RealmObject {

    @PrimaryKey
    private int id;
    private String name;
    private String adresse;
    private String phone;

    public Agence(int id, String name, String adresse, String phone) {
        this.id = id;
        this.name = name;
        this.adresse = adresse;
        this.phone = phone;
    }

    public Agence() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
