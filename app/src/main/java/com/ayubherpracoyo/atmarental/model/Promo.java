package com.ayubherpracoyo.atmarental.model;

public class Promo {
    private int id;
    private String kode_promo;
    private String jenis_promo;
    private String keterangan;
    private float diskon;

    public Promo(int id, String kode_promo, String jenis_promo, String keterangan, float diskon) {
        this.id = id;
        this.kode_promo = kode_promo;
        this.jenis_promo = jenis_promo;
        this.keterangan = keterangan;
        this.diskon = diskon;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getKode_promo() {
        return kode_promo;
    }

    public void setKode_promo(String kode_promo) {
        this.kode_promo = kode_promo;
    }

    public String getJenis_promo() {
        return jenis_promo;
    }

    public void setJenis_promo(String jenis_promo) {
        this.jenis_promo = jenis_promo;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }

    public float getDiskon() {
        return diskon;
    }

    public void setDiskon(float diskon) {
        this.diskon = diskon;
    }
}
