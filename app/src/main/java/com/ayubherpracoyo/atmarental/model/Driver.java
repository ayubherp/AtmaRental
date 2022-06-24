package com.ayubherpracoyo.atmarental.model;

import android.icu.text.SimpleDateFormat;

import java.text.ParseException;
import java.util.Date;

public class Driver {
    private String id;
    private String nama_driver;
    private String alamat_driver;
    private String tgl_lahir_driver;
    private String gender_driver;
    private String email_driver;
    private String no_telepon_driver;
    private String bahasa_driver;
    private String foto_driver;
    private int status_driver;
    private int jumlah_transaksi_driver;
    private float rating_driver;
    private String link_foto;
    private String tgl_lahir;
    private float nilai_performa;

    public Driver(String id, String nama_driver, String alamat_driver, String tgl_lahir_driver, String gender_driver, String email_driver, String no_telepon_driver, String bahasa_driver, String foto_driver, int status_driver, int jumlah_transaksi_driver, float rating_driver) {
        this.id = id;
        this.nama_driver = nama_driver;
        this.alamat_driver = alamat_driver;
        this.tgl_lahir_driver = tgl_lahir_driver;
        this.gender_driver = gender_driver;
        this.email_driver = email_driver;
        this.no_telepon_driver = no_telepon_driver;
        this.bahasa_driver = bahasa_driver;
        this.foto_driver = foto_driver;
        this.status_driver = status_driver;
        this.jumlah_transaksi_driver = jumlah_transaksi_driver;
        this.rating_driver = rating_driver;
    }

    public Driver(String nama_driver, String alamat_driver, String no_telepon_driver) {
        this.nama_driver = nama_driver;
        this.alamat_driver = alamat_driver;
        this.no_telepon_driver = no_telepon_driver;
    }

    public Driver(int status_driver) {
        this.status_driver = status_driver;
    }

    public String getLink_foto() {
        return "http://atmarental.my.id"+foto_driver;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNama_driver() {
        return nama_driver;
    }

    public void setNama_driver(String nama_driver) {
        this.nama_driver = nama_driver;
    }

    public String getAlamat_driver() {
        return alamat_driver;
    }

    public void setAlamat_driver(String alamat_driver) {
        this.alamat_driver = alamat_driver;
    }

    public String getTgl_lahir_driver() {
        return tgl_lahir_driver;
    }

    public void setTgl_lahir_driver(String tgl_lahir_driver) {
        this.tgl_lahir_driver = tgl_lahir_driver;
    }

    public String getGender_driver() {
        return gender_driver;
    }

    public void setGender_driver(String gender_driver) {
        this.gender_driver = gender_driver;
    }

    public String getEmail_driver() {
        return email_driver;
    }

    public void setEmail_driver(String email_driver) {
        this.email_driver = email_driver;
    }

    public String getNo_telepon_driver() {
        return no_telepon_driver;
    }

    public void setNo_telepon_driver(String no_telepon_driver) {
        this.no_telepon_driver = no_telepon_driver;
    }

    public String getBahasa_driver() {
        return bahasa_driver;
    }

    public void setBahasa_driver(String bahasa_driver) {
        this.bahasa_driver = bahasa_driver;
    }

    public String getFoto_driver() {
        return foto_driver;
    }

    public void setFoto_driver(String foto_driver) {
        this.foto_driver = foto_driver;
    }

    public int getStatus_driver() {
        return status_driver;
    }

    public void setStatus_driver(int status_driver) {
        this.status_driver = status_driver;
    }

    public int getJumlah_transaksi_driver() {
        return jumlah_transaksi_driver;
    }

    public void setJumlah_transaksi_driver(int jumlah_transaksi_driver) {
        this.jumlah_transaksi_driver = jumlah_transaksi_driver;
    }



    public float getRating_driver() {
        return rating_driver;
    }

    public void setRating_driver(float rating_driver) {
        this.rating_driver = rating_driver;
    }

    public String getTgl_lahir() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date newDate = null;
        try {
            newDate = format.parse(tgl_lahir_driver);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        format = new SimpleDateFormat("dd MMMM yyyy");
        tgl_lahir = format.format(newDate);
        return tgl_lahir;
    }

    public void setTgl_lahir(String tgl_lahir) {
        this.tgl_lahir = tgl_lahir;
    }

    public float getNilai_performa(Float banyak_transaksi) {
        nilai_performa = rating_driver*jumlah_transaksi_driver/banyak_transaksi;
        return nilai_performa;
    }

    public void setNilai_performa(float nilai_performa) {
        this.nilai_performa = nilai_performa;
    }
}
