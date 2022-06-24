package com.ayubherpracoyo.atmarental.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PegawaiResponse {
    private String message;
    @SerializedName("data")
    private List<Pegawai> pegawaiList;
    @SerializedName("pegawai")
    private Pegawai pegawai;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Pegawai> getPegawaiList() {
        return pegawaiList;
    }

    public void setPegawaiList(List<Pegawai> pegawaiList) {
        this.pegawaiList = pegawaiList;
    }

    public Pegawai getPegawai() {
        return pegawai;
    }

    public void setPegawai(Pegawai pegawai) {
        this.pegawai = pegawai;
    }
}
