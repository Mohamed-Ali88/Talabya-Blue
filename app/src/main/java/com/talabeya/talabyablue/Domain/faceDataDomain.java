package com.talabeya.talabyablue.Domain;

import java.io.Serializable;

public class faceDataDomain implements Serializable {
    String name,nextData;
    int id,showRepresentativeActivity;

    public faceDataDomain( int id,String name, String nextData, int showRepresentativeActivity) {
        this.id = id;
        this.name = name;
        this.nextData = nextData;
        this.showRepresentativeActivity = showRepresentativeActivity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNextData() {
        return nextData;
    }

    public void setNextData(String nextData) {
        this.nextData = nextData;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getShowRepresentativeActivity() {
        return showRepresentativeActivity;
    }

    public void setShowRepresentativeActivity(int showRepresentativeActivity) {
        this.showRepresentativeActivity = showRepresentativeActivity;
    }
}
