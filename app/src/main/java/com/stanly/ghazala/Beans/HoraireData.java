package com.stanly.ghazala.Beans;



import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;


public class HoraireData extends RealmObject {




     @PrimaryKey
     private int id;
     private String hd;


     private String ha;

     private String prix;

    public HoraireData(String hd, String ha, String prix) {
        this.hd = hd;
        this.ha = ha;
        this.prix = prix;
    }

    public HoraireData() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getHd() {
        return hd;
    }

    public void setHd(String hd) {
        this.hd = hd;
    }

    public String getHa() {
        return ha;
    }

    public void setHa(String ha) {
        this.ha = ha;
    }

    public String getPrix() {
        return prix;
    }

    public void setPrix(String prix) {
        this.prix = prix;
    }
}
