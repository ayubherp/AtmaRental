package com.ayubherpracoyo.atmarental.api;

public class TransaksiApi {
    public static final String BASE_URL = "http://atmarental.my.id/api/";

    public static final String GET_ALL_URL = BASE_URL + "transaksi";
    public static final String GET_DETAIL_INCOME_BULANAN = BASE_URL + "transaksidpb";
    public static final String GET_DETAIL_INCOME_TAHUNAN = BASE_URL + "transaksidpt";
    public static final String GET_SEWA_BULANAN_MOBIL = BASE_URL + "transaksispb";
    public static final String GET_BY_ID_URL = BASE_URL + "transaksi/";
    public static final String ADD_URL = BASE_URL + "transaksi";
    public static final String UPDATE_URL = BASE_URL + "transaksi/";
    public static final String DELETE_URL = BASE_URL + "transaksi/";
    public static final String SUBMIT_RATING = BASE_URL + "transaksirating/";
}
