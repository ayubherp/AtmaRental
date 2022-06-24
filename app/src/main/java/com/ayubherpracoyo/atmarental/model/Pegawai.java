package com.ayubherpracoyo.atmarental.model;

public class Pegawai {
    private String id;
    private String nama_pegawai;
    private String alamat_pegawai;
    private String tgl_lahir_pegawai;
    private String gender_pegawai;
    private String email_pegawai;
    private String no_telepon_pegawai;
    private String foto_pegawai;
    private String role_pegawai;

    public Pegawai(String id, String nama_pegawai, String alamat_pegawai, String tgl_lahir_pegawai, String gender_pegawai, String email_pegawai, String no_telepon_pegawai, String foto_pegawai, String role_pegawai) {
        this.id = id;
        this.nama_pegawai = nama_pegawai;
        this.alamat_pegawai = alamat_pegawai;
        this.tgl_lahir_pegawai = tgl_lahir_pegawai;
        this.gender_pegawai = gender_pegawai;
        this.email_pegawai = email_pegawai;
        this.no_telepon_pegawai = no_telepon_pegawai;
        this.foto_pegawai = foto_pegawai;
        this.role_pegawai = role_pegawai;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNama_pegawai() {
        return nama_pegawai;
    }

    public void setNama_pegawai(String nama_pegawai) {
        this.nama_pegawai = nama_pegawai;
    }

    public String getAlamat_pegawai() {
        return alamat_pegawai;
    }

    public void setAlamat_pegawai(String alamat_pegawai) {
        this.alamat_pegawai = alamat_pegawai;
    }

    public String getTgl_lahir_pegawai() {
        return tgl_lahir_pegawai;
    }

    public void setTgl_lahir_pegawai(String tgl_lahir_pegawai) {
        this.tgl_lahir_pegawai = tgl_lahir_pegawai;
    }

    public String getGender_pegawai() {
        return gender_pegawai;
    }

    public void setGender_pegawai(String gender_pegawai) {
        this.gender_pegawai = gender_pegawai;
    }

    public String getEmail_pegawai() {
        return email_pegawai;
    }

    public void setEmail_pegawai(String email_pegawai) {
        this.email_pegawai = email_pegawai;
    }

    public String getNo_telepon_pegawai() {
        return no_telepon_pegawai;
    }

    public void setNo_telepon_pegawai(String no_telepon_pegawai) {
        this.no_telepon_pegawai = no_telepon_pegawai;
    }

    public String getFoto_pegawai() {
        return foto_pegawai;
    }

    public void setFoto_pegawai(String foto_pegawai) {
        this.foto_pegawai = foto_pegawai;
    }

    public String getRole_pegawai() {
        return role_pegawai;
    }

    public void setRole_pegawai(String role_pegawai) {
        this.role_pegawai = role_pegawai;
    }
}
