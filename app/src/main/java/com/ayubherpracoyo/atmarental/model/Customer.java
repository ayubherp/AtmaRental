package com.ayubherpracoyo.atmarental.model;

import android.icu.text.SimpleDateFormat;

import java.sql.Timestamp;
import java.text.ParseException;
import java.util.Date;

public class Customer {
    private String id;
    private String nama_customer;
    private String no_ktp_customer;
    private String no_sim_customer;
    private String alamat_customer;
    private String gender_customer;
    private String email_customer;
    private String tgl_lahir_customer;
    private String no_telepon_customer;
    private int jumlah_transaksi_customer;
    private String tgl_lahir;

    public Customer(String id, String nama_customer, String no_ktp_customer, String no_sim_customer, String alamat_customer, String gender_customer, String email_customer, String tgl_lahir_customer, String no_telepon_customer, int jumlah_transaksi_customer) {
        this.id = id;
        this.nama_customer = nama_customer;
        this.no_ktp_customer = no_ktp_customer;
        this.no_sim_customer = no_sim_customer;
        this.alamat_customer = alamat_customer;
        this.gender_customer = gender_customer;
        this.email_customer = email_customer;
        this.tgl_lahir_customer = tgl_lahir_customer;
        this.no_telepon_customer = no_telepon_customer;
        this.jumlah_transaksi_customer = jumlah_transaksi_customer;
    }

    public Customer(String id, String nama_customer, String no_ktp_customer, String no_sim_customer, String alamat_customer, String gender_customer, String email_customer, String no_telepon_customer, int jumlah_transaksi_customer) {
        this.id = id;
        this.nama_customer = nama_customer;
        this.no_ktp_customer = no_ktp_customer;
        this.no_sim_customer = no_sim_customer;
        this.alamat_customer = alamat_customer;
        this.gender_customer = gender_customer;
        this.email_customer = email_customer;
        this.no_telepon_customer = no_telepon_customer;
        this.jumlah_transaksi_customer = jumlah_transaksi_customer;
    }

    public Customer(String nama_customer, String no_ktp_customer, String no_sim_customer, String alamat_customer, String gender_customer, String no_telepon_customer, int jumlah_transaksi_customer) {
        this.nama_customer = nama_customer;
        this.no_ktp_customer = no_ktp_customer;
        this.no_sim_customer = no_sim_customer;
        this.alamat_customer = alamat_customer;
        this.gender_customer = gender_customer;
        this.email_customer = email_customer;
        this.no_telepon_customer = no_telepon_customer;
        this.jumlah_transaksi_customer = jumlah_transaksi_customer;
    }

    public Customer(String nama_customer, String alamat_customer, String no_telepon_customer) {
        this.nama_customer = nama_customer;
        this.alamat_customer = alamat_customer;
        this.no_telepon_customer = no_telepon_customer;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNama_customer() {
        return nama_customer;
    }

    public void setNama_customer(String nama_customer) {
        this.nama_customer = nama_customer;
    }

    public String getNo_ktp_customer() {
        return no_ktp_customer;
    }

    public void setNo_ktp_customer(String no_ktp_customer) {
        this.no_ktp_customer = no_ktp_customer;
    }

    public String getNo_sim_customer() {
        return no_sim_customer;
    }

    public void setNo_sim_customer(String no_sim_customer) {
        this.no_sim_customer = no_sim_customer;
    }

    public String getAlamat_customer() {
        return alamat_customer;
    }

    public void setAlamat_customer(String alamat_customer) {
        this.alamat_customer = alamat_customer;
    }

    public String getGender_customer() {
        return gender_customer;
    }

    public void setGender_customer(String gender_customer) {
        this.gender_customer = gender_customer;
    }

    public String getEmail_customer() {
        return email_customer;
    }

    public void setEmail_customer(String email_customer) {
        this.email_customer = email_customer;
    }

    public String getNo_telepon_customer() {
        return no_telepon_customer;
    }

    public void setNo_telepon_customer(String no_telepon_customer) {
        this.no_telepon_customer = no_telepon_customer;
    }

    public int getJumlah_transaksi_customer() {
        return jumlah_transaksi_customer;
    }

    public void setJumlah_transaksi_customer(int jumlah_transaksi_customer) {
        this.jumlah_transaksi_customer = jumlah_transaksi_customer;
    }

    public String getTgl_lahir() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date newDate = null;
        try {
            newDate = format.parse(tgl_lahir_customer);
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
}
