package com.ayubherpracoyo.atmarental.model;

import android.icu.util.Calendar;

import java.text.SimpleDateFormat;

public class Transaksi {
    private int id;
    private String no_transaksi;
    private String id_pegawai;
    private String id_driver;
    private String id_customer;
    private int id_mobil;
    private String id_promo;
    private String target_mulai_sewa;
    private String target_selesai_sewa;
    private String waktu_selesai_sewa;
    private double denda_sewa;
    private String metode_pembayaran;
    private double total_pembayaran;
    private int status_pembayaran;
    private String bukti_pembayaran;
    private int status_sewa, status_rating;
    private float rating;
    private String banyak_hari;
    private double harga_sewa;
    private String statusBayar, statusSewa;
    private String nama_mobil, nama_pegawai, nama_driver, kode_promo;
    private double total_pendapatan;
    private String bulan;
    private int tahun;

    public int getTahun() {
        return tahun;
    }

    public void setTahun(int tahun) {
        this.tahun = tahun;
    }

    public double getTotal_pendapatan() {
        return total_pendapatan;
    }

    public void setTotal_pendapatan(double total_pendapatan) {
        this.total_pendapatan = total_pendapatan;
    }

    public String getBulan() {
        if(Integer.parseInt(bulan)==1)
            return "Januari";
        else if(Integer.parseInt(bulan)==2)
            return "Februari";
        else if(Integer.parseInt(bulan)==3)
            return "Maret";
        else if(Integer.parseInt(bulan)==4)
            return "April";
        else if(Integer.parseInt(bulan)==5)
            return "Mei";
        else if(Integer.parseInt(bulan)==6)
            return "Juni";
        else if(Integer.parseInt(bulan)==7)
            return "Juli";
        else if(Integer.parseInt(bulan)==8)
            return "Agustus";
        else if(Integer.parseInt(bulan)==9)
            return "September";
        else if(Integer.parseInt(bulan)==10)
            return "Oktober";
        else if(Integer.parseInt(bulan)==11)
            return "November";
        else if(Integer.parseInt(bulan)==12)
            return "Desember";
        return null;
    }

    public void setBulan(String bulan) {
        this.bulan = bulan;
    }

    public Transaksi(double total_pendapatan, String bulan) {
        this.total_pendapatan = total_pendapatan;
        this.bulan = bulan;
    }

    public Transaksi(int id, double total_pendapatan, int tahun) {
        this.id = id;
        this.total_pendapatan = total_pendapatan;
        this.tahun = tahun;
    }

    public Transaksi(int id_mobil, double total_pendapatan, String bulan) {
        this.id_mobil = id_mobil;
        this.total_pendapatan = total_pendapatan;
        this.bulan = bulan;
    }

    public Transaksi(int id, String no_transaksi, String id_pegawai, String id_driver, String id_customer, int id_mobil, String id_promo, String target_mulai_sewa, String target_selesai_sewa, String waktu_selesai_sewa, double denda_sewa, String metode_pembayaran, double total_pembayaran, int status_pembayaran, String bukti_pembayaran, int status_sewa, String banyak_hari, double harga_sewa, String nama_mobil, String nama_pegawai, String nama_driver, String kode_promo) {
        this.id = id;
        this.no_transaksi = no_transaksi;
        this.id_pegawai = id_pegawai;
        this.id_driver = id_driver;
        this.id_customer = id_customer;
        this.id_mobil = id_mobil;
        this.id_promo = id_promo;
        this.target_mulai_sewa = target_mulai_sewa;
        this.target_selesai_sewa = target_selesai_sewa;
        this.waktu_selesai_sewa = waktu_selesai_sewa;
        this.denda_sewa = denda_sewa;
        this.metode_pembayaran = metode_pembayaran;
        this.total_pembayaran = total_pembayaran;
        this.status_pembayaran = status_pembayaran;
        this.bukti_pembayaran = bukti_pembayaran;
        this.status_sewa = status_sewa;
        this.banyak_hari = banyak_hari;
        this.harga_sewa = harga_sewa;
        this.nama_mobil = nama_mobil;
        this.nama_pegawai = nama_pegawai;
        this.nama_driver = nama_driver;
        this.kode_promo = kode_promo;
    }

    public String getNama_mobil() {
        return nama_mobil;
    }

    public void setNama_mobil(String nama_mobil) {
        this.nama_mobil = nama_mobil;
    }

    public String getNama_pegawai() {
        return nama_pegawai;
    }

    public void setNama_pegawai(String nama_pegawai) {
        this.nama_pegawai = nama_pegawai;
    }

    public String getNama_driver() {
        return nama_driver;
    }

    public void setNama_driver(String nama_driver) {
        this.nama_driver = nama_driver;
    }

    public String getKode_promo() {
        return kode_promo;
    }

    public void setKode_promo(String kode_promo) {
        this.kode_promo = kode_promo;
    }

    public String getStatusBayar() {
        if(status_pembayaran==0 && metode_pembayaran.equals("Cash"))
            return "Silakan lakukan pembayaran langsung di tempat";
        else if(status_pembayaran==0 && metode_pembayaran.equals("Transfer"))
            return "Silakan melakukan transfer ke rekening (klik bar ini untuk detail rekening atau klik icon biru untuk upload bukti tranfer)";
        else if(status_pembayaran==1 && metode_pembayaran.equals("Transfer"))
            return "Bukti pembayaran terkirim, menunggu verifikasi operator";
        else if(status_pembayaran==1 && metode_pembayaran.equals("Cash"))
            return "Pembayaran sudah dilakukan di tempat";
        else if(status_pembayaran==2)
            return "Selesai (Verified)";
        else if(status_pembayaran==3)
            return "Pembayaran gagal";

        return "";
    }

    public void setStatusBayar(String statusBayar) {
        this.statusBayar = statusBayar;
    }

    public String getStatusSewa() {
        if(status_sewa==0)
            return "Menunggu pembayaran diverifikasi";
        else if(status_sewa==1)
            return "Sewa mobil sedang berjalan";
        else if(status_sewa==2)
            return "Selesai";

        return "";
    }

    public void setStatusSewa(String statusSewa) {
        this.statusSewa = statusSewa;
    }

    public Transaksi(int id, String no_transaksi, String id_pegawai, String id_driver, String id_customer, int id_mobil,
                     String id_promo, String target_mulai_sewa, String target_selesai_sewa, String waktu_selesai_sewa, double denda_sewa,
                     String metode_pembayaran, double total_pembayaran, int status_pembayaran, String bukti_pembayaran, int status_sewa, int status_rating) {
        this.id = id;
        this.no_transaksi = no_transaksi;
        this.id_pegawai = id_pegawai;
        this.id_driver = id_driver;
        this.id_customer = id_customer;
        this.id_mobil = id_mobil;
        this.id_promo = id_promo;
        this.target_mulai_sewa = target_mulai_sewa;
        this.target_selesai_sewa = target_selesai_sewa;
        this.waktu_selesai_sewa = waktu_selesai_sewa;
        this.denda_sewa = denda_sewa;
        this.metode_pembayaran = metode_pembayaran;
        this.total_pembayaran = total_pembayaran;
        this.status_pembayaran = status_pembayaran;
        this.bukti_pembayaran = bukti_pembayaran;
        this.status_sewa = status_sewa;
        this.status_rating = status_rating;
    }

    public String getBanyak_hari() {
        return banyak_hari;
    }

    public void setBanyak_hari(String banyak_hari) {
        this.banyak_hari = banyak_hari;
    }

    public double getHarga_sewa() {
        return harga_sewa;
    }

    public void setHarga_sewa(double harga_sewa) {
        this.harga_sewa = harga_sewa;
    }

    public Transaksi(String id_driver, String id_customer, int id_mobil, String id_promo, String target_mulai_sewa, String metode_pembayaran, Double harga_sewa, String banyak_hari) {
        this.id_driver = id_driver;
        this.id_customer = id_customer;
        this.id_mobil = id_mobil;
        this.id_promo = id_promo;
        this.target_mulai_sewa = target_mulai_sewa;
        this.metode_pembayaran = metode_pembayaran;
        this.harga_sewa = harga_sewa;
        this.banyak_hari = banyak_hari;
    }

    public Transaksi(String no_transaksi, String id_driver, String id_customer, int id_mobil, String id_promo, String target_mulai_sewa, String metode_pembayaran, Double harga_sewa, String banyak_hari) {
        this.no_transaksi = no_transaksi;
        this.id_driver = id_driver;
        this.id_customer = id_customer;
        this.id_mobil = id_mobil;
        this.id_promo = id_promo;
        this.target_mulai_sewa = target_mulai_sewa;
        this.metode_pembayaran = metode_pembayaran;
        this.banyak_hari = banyak_hari;
        this.harga_sewa = harga_sewa;
    }

    public Transaksi(int id, float rating) {
        this.id = id;
        this.rating = rating;
    }

    public String getWaktu_selesai_sewa() {
        return waktu_selesai_sewa;
    }

    public void setWaktu_selesai_sewa(String waktu_selesai_sewa) {
        this.waktu_selesai_sewa = waktu_selesai_sewa;
    }

    public double getDenda_sewa() {
        return denda_sewa;
    }

    public void setDenda_sewa(double denda_sewa) {
        this.denda_sewa = denda_sewa;
    }

    public String getMetode_pembayaran() {
        return metode_pembayaran;
    }

    public void setMetode_pembayaran(String metode_pembayaran) {
        this.metode_pembayaran = metode_pembayaran;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNo_transaksi() {
        return no_transaksi;
    }

    public void setNo_transaksi(String no_transaksi) {
        this.no_transaksi = no_transaksi;
    }

    public String getId_pegawai() {
        return id_pegawai;
    }

    public void setId_pegawai(String id_pegawai) {
        this.id_pegawai = id_pegawai;
    }

    public String getId_driver() {
        return id_driver;
    }

    public void setId_driver(String id_driver) {
        this.id_driver = id_driver;
    }

    public String getId_customer() {
        return id_customer;
    }

    public void setId_customer(String id_customer) {
        this.id_customer = id_customer;
    }

    public int getId_mobil() {
        return id_mobil;
    }

    public void setId_mobil(int id_mobil) {
        this.id_mobil = id_mobil;
    }

    public String getId_promo() {
        return id_promo;
    }

    public void setId_promo(String id_promo) {
        this.id_promo = id_promo;
    }

    public String getTarget_mulai_sewa() {
        return target_mulai_sewa;
    }

    public void setTarget_mulai_sewa(String target_mulai_sewa) {
        this.target_mulai_sewa = target_mulai_sewa;
    }

    public String getTarget_selesai_sewa() {
        return target_selesai_sewa;
    }

    public void setTarget_selesai_sewa(String target_selesai_sewa) {
        this.target_selesai_sewa = target_selesai_sewa;
    }
    public double getTotal_pembayaran() {
        return total_pembayaran;
    }

    public void setTotal_pembayaran(double total_pembayaran) {
        this.total_pembayaran = total_pembayaran;
    }

    public int getStatus_pembayaran() {
        return status_pembayaran;
    }

    public void setStatus_pembayaran(int status_pembayaran) {
        this.status_pembayaran = status_pembayaran;
    }

    public String getBukti_pembayaran() {
        return bukti_pembayaran;
    }

    public void setBukti_pembayaran(String bukti_pembayaran) {
        this.bukti_pembayaran = bukti_pembayaran;
    }

    public int getStatus_sewa() {
        return status_sewa;
    }

    public void setStatus_sewa(int status_sewa) {
        this.status_sewa = status_sewa;
    }

    public int getStatus_rating() {
        return status_rating;
    }

    public void setStatus_rating(int status_rating) {
        this.status_rating = status_rating;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }
}
