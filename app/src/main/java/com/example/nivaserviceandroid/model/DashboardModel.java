package com.example.nivaserviceandroid.model;

import androidx.annotation.Nullable;

public class DashboardModel {
    private int imgid;
    @Nullable
    private String value;
    private String label;

    public DashboardModel(){}

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public int getImgid() {
        return imgid;
    }

    public void setImgid(int imgid) {
        this.imgid = imgid;
    }
}
