package com.stanly.ghazala.Beans;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

import io.realm.RealmObject;

public class SuperData extends RealmObject {


    private Data horaires;

    public Data getHoraires() {
        return horaires;
    }

    public void setHoraires(Data horaires) {
        this.horaires = horaires;
    }

    public SuperData() {
    }

    public SuperData(Data horaires) {
        this.horaires = horaires;
    }
}
