package com.ayubherpracoyo.atmarental.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MobilResponse {
    private String message;
    @SerializedName("data")
    private List<Mobil> mobilList;
    @SerializedName("mobil")
    private Mobil mobil;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Mobil> getMobilList() {
        return mobilList;
    }

    public void setMobilList(List<Mobil> mobilList) {
        this.mobilList = mobilList;
    }

    public Mobil getMobil(){
        return mobil;
    }

    public void setMobil(Mobil mobil) {
        this.mobil = mobil;
    }
}
