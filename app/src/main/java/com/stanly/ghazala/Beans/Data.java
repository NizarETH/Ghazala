package com.stanly.ghazala.Beans;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Data extends RealmObject {

    @PrimaryKey
     private String id;
     private String depart;
     private String arrive;
     private String horaire;
     private RealmList<HoraireData> horaireData;



    public Data() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public RealmList<HoraireData> getHoraireData() {
        return horaireData;
    }

    public void setHoraireData(RealmList<HoraireData> horaireData) {
        this.horaireData = horaireData;
    }

    public String getDepart() {
        return depart;
    }

    public void setDepart(String depart) {
        this.depart = depart;
    }

    public String getArrive() {
        return arrive;
    }

    public void setArrive(String arrive) {
        this.arrive = arrive;
    }

    public String getHoraire() {
        return horaire;
    }

    public void setHoraire(String horaire) {
        this.horaire = horaire;
    }

    public Data(String id, String depart, String arrive, String horaire) {
        this.id = id;
        this.depart = depart;
        this.arrive = arrive;
        this.horaire = horaire;
    }
}
