package com.ayubherpracoyo.atmarental.model;

import com.ayubherpracoyo.atmarental.TransaksiActivity;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TransaksiResponse {
    private String message;
    @SerializedName("data")
    private List<Transaksi> transaksiList;
    @SerializedName("transaksi")
    private Transaksi transaksi;

    public TransaksiResponse(String message, List<Transaksi> transaksiList, Transaksi transaksi) {
        this.message = message;
        this.transaksiList = transaksiList;
        this.transaksi = transaksi;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Transaksi> getTransaksiList() {
        return transaksiList;
    }

    public void setTransaksiList(List<Transaksi> transaksiList) {
        this.transaksiList = transaksiList;
    }

    public Transaksi getTransaksi() {
        return transaksi;
    }

    public void setTransaksi(Transaksi transaksi) {
        this.transaksi = transaksi;
    }
}
