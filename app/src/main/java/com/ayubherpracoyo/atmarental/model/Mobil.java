package com.ayubherpracoyo.atmarental.model;

public class Mobil {
    private int id;
    private String tipe_mobil;
    private String nama_mobil;
    private String jenis_transmisi;
    private String jenis_bahan_bakar;
    private String warna_mobil;
    private float volume_bagasi;
    private String fasilitas;
    private double harga_sewa_per_hari;
    private String plat_nomor;
    private String nomor_stnk;
    private int kapasitas_penumpang;
    private float volume_bahan_bakar;
    private String foto_mobil;
    private int status_mobil;
    private String status;
    private String link_foto;

    public String getLink_foto() {
        return "http://atmarental.my.id"+foto_mobil;
    }

    public Mobil(int id, String tipe_mobil, String nama_mobil, String jenis_transmisi, String jenis_bahan_bakar, String warna_mobil, float volume_bagasi, String fasilitas, double harga_sewa_per_hari, String plat_nomor, String nomor_stnk, int kapasitas_penumpang, float volume_bahan_bakar, String foto_mobil, int status_mobil) {
        this.id = id;
        this.tipe_mobil = tipe_mobil;
        this.nama_mobil = nama_mobil;
        this.jenis_transmisi = jenis_transmisi;
        this.jenis_bahan_bakar = jenis_bahan_bakar;
        this.warna_mobil = warna_mobil;
        this.volume_bagasi = volume_bagasi;
        this.fasilitas = fasilitas;
        this.harga_sewa_per_hari = harga_sewa_per_hari;
        this.plat_nomor = plat_nomor;
        this.nomor_stnk = nomor_stnk;
        this.kapasitas_penumpang = kapasitas_penumpang;
        this.volume_bahan_bakar = volume_bahan_bakar;
        this.foto_mobil = foto_mobil;
        this.status_mobil = status_mobil;
    }

    public Mobil(String tipe_mobil, String nama_mobil, String jenis_transmisi, String jenis_bahan_bakar, String warna_mobil, float volume_bagasi, String fasilitas, double harga_sewa_per_hari, String plat_nomor, String nomor_stnk, int kapasitas_penumpang, float volume_bahan_bakar, String foto_mobil, int status_mobil) {
        this.tipe_mobil = tipe_mobil;
        this.nama_mobil = nama_mobil;
        this.jenis_transmisi = jenis_transmisi;
        this.jenis_bahan_bakar = jenis_bahan_bakar;
        this.warna_mobil = warna_mobil;
        this.volume_bagasi = volume_bagasi;
        this.fasilitas = fasilitas;
        this.harga_sewa_per_hari = harga_sewa_per_hari;
        this.plat_nomor = plat_nomor;
        this.nomor_stnk = nomor_stnk;
        this.kapasitas_penumpang = kapasitas_penumpang;
        this.volume_bahan_bakar = volume_bahan_bakar;
        this.foto_mobil = foto_mobil;
        this.status_mobil = status_mobil;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTipe_mobil() {
        return tipe_mobil;
    }

    public void setTipe_mobil(String tipe_mobil) {
        this.tipe_mobil = tipe_mobil;
    }

    public String getNama_mobil() {
        return nama_mobil;
    }

    public void setNama_mobil(String nama_mobil) {
        this.nama_mobil = nama_mobil;
    }

    public String getJenis_transmisi() {
        return jenis_transmisi;
    }

    public void setJenis_transmisi(String jenis_transmisi) {
        this.jenis_transmisi = jenis_transmisi;
    }

    public String getJenis_bahan_bakar() {
        return jenis_bahan_bakar;
    }

    public void setJenis_bahan_bakar(String jenis_bahan_bakar) {
        this.jenis_bahan_bakar = jenis_bahan_bakar;
    }

    public String getWarna_mobil() {
        return warna_mobil;
    }

    public void setWarna_mobil(String warna_mobil) {
        this.warna_mobil = warna_mobil;
    }

    public float getVolume_bagasi() {
        return volume_bagasi;
    }

    public void setVolume_bagasi(float volume_bagasi) {
        this.volume_bagasi = volume_bagasi;
    }

    public String getFasilitas() {
        return fasilitas;
    }

    public void setFasilitas(String fasilitas) {
        this.fasilitas = fasilitas;
    }

    public double getHarga_sewa_per_hari() {
        return harga_sewa_per_hari;
    }

    public void setHarga_sewa_per_hari(double harga_sewa_per_hari) {
        this.harga_sewa_per_hari = harga_sewa_per_hari;
    }

    public String getPlat_nomor() {
        return plat_nomor;
    }

    public void setPlat_nomor(String plat_nomor) {
        this.plat_nomor = plat_nomor;
    }

    public String getNomor_stnk() {
        return nomor_stnk;
    }

    public void setNomor_stnk(String nomor_stnk) {
        this.nomor_stnk = nomor_stnk;
    }

    public int getKapasitas_penumpang() {
        return kapasitas_penumpang;
    }

    public void setKapasitas_penumpang(int kapasitas_penumpang) {
        this.kapasitas_penumpang = kapasitas_penumpang;
    }

    public float getVolume_bahan_bakar() {
        return volume_bahan_bakar;
    }

    public void setVolume_bahan_bakar(float volume_bahan_bakar) {
        this.volume_bahan_bakar = volume_bahan_bakar;
    }

    public String getFoto_mobil() {
        return foto_mobil;
    }

    public void setFoto_mobil(String foto_mobil) {
        this.foto_mobil = foto_mobil;
    }

    public int getStatus_mobil() {
        return status_mobil;
    }

    public void setStatus_mobil(int status_mobil) {
        this.status_mobil = status_mobil;
    }

    public String getStatus() {
        if(status_mobil==1)
            return "Tersedia";
        else
            return "Tidak Tersedia";
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
