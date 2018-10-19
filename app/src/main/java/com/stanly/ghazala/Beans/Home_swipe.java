package com.stanly.ghazala.Beans;


import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;


public class Home_swipe extends RealmObject {

    @PrimaryKey
    private int id;
    private RealmList<ElementSwipe> elements = new RealmList<ElementSwipe>();


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }



    public RealmList<ElementSwipe> getElements() {
        return elements;
    }

    public void setElements(RealmList<ElementSwipe> elements) {
        this.elements = elements;
    }



    public Home_swipe() {
    }

    public Home_swipe(int id,   RealmList<ElementSwipe> elements/*, Application application*/) {
        this.id = id;
         this.elements = elements;
        //this.application = application;
    }
}