package com.ayubherpracoyo.atmarental.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DriverResponse {
    private String message;
    private float total_transaksi;
    @SerializedName("data")
    private List<Driver> driverList;
    @SerializedName("driver")
    private Driver driver;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Driver> getDriverList() {
        return driverList;
    }

    public void setDriverList(List<Driver> driverList) {
        this.driverList = driverList;
    }

    public Driver getDriver() {
        return driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    public float getTotal_transaksi() {
        for(Driver D : driverList){
            total_transaksi = total_transaksi+D.getJumlah_transaksi_driver();
        }
        return total_transaksi;
    }

    public void setTotal_transaksi(float total_transaksi) {
        this.total_transaksi = total_transaksi;
    }

}
