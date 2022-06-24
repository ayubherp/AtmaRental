package com.ayubherpracoyo.atmarental;

import static com.android.volley.Request.Method.GET;
import static com.android.volley.Request.Method.POST;
import static com.android.volley.Request.Method.PUT;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.ayubherpracoyo.atmarental.api.MobilApi;
import com.ayubherpracoyo.atmarental.api.PromoApi;
import com.ayubherpracoyo.atmarental.api.TransaksiApi;
import com.ayubherpracoyo.atmarental.databinding.ActivityTransaksiBinding;
import com.ayubherpracoyo.atmarental.model.Mobil;
import com.ayubherpracoyo.atmarental.model.MobilResponse;
import com.ayubherpracoyo.atmarental.model.Promo;
import com.ayubherpracoyo.atmarental.model.PromoResponse;
import com.ayubherpracoyo.atmarental.model.Transaksi;
import com.ayubherpracoyo.atmarental.model.TransaksiResponse;
import com.ayubherpracoyo.atmarental.model.User;
import com.ayubherpracoyo.atmarental.preferences.UserPreferences;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.gson.Gson;
import android.text.format.DateFormat;

import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class TransaksiActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener,
        TimePickerDialog.OnTimeSetListener {
    private ActivityTransaksiBinding binding;
    private User user;
    private UserPreferences userPreferences;
    private RequestQueue queue;
    private RadioButton radio_driver, radio_metode;
    Intent intent;
    private String token;
    private int id_mobil;
    private String id_promo=null;
    private String banyak_hari;
    private int update;
    private Transaksi transaksi;
    private int id_transaksi;

    private List<Promo> promoList;
    private String tahun, bulan, hari, jam, menit;
    private int year, month, day, hour, minute;
    private int yearFinal, monthFinal, dayFinal, hourFinal, minuteFinal;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        intent = getIntent();
        binding = ActivityTransaksiBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        queue = Volley.newRequestQueue(this);
        userPreferences = new UserPreferences(getApplicationContext());
        user = userPreferences.getUserLogin();

        update = intent.getIntExtra("update",0);
        id_transaksi = intent.getIntExtra("id_transaksi",0);
        token = intent.getStringExtra("token");
        id_mobil = intent.getIntExtra("id_mobil",0);

        if(update==1 && id_transaksi>0)
        {
            getTransaksiByID(id_transaksi);
            getMobilById(id_mobil);
        }

        binding.btnTanggal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH);
                day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(TransaksiActivity.this,TransaksiActivity.this,
                        year, month, day);
                datePickerDialog.show();
            }
        });

        binding.btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        binding.btnSewa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int radioId = binding.radioDriver.getCheckedRadioButtonId();
                radio_driver = findViewById(radioId);
                radioId = binding.radioMetode.getCheckedRadioButtonId();
                radio_metode = findViewById(radioId);
                banyak_hari = binding.etHari.getText().toString();

                if(banyak_hari.equals("")){
                    Toast.makeText(TransaksiActivity.this,"Mohon isi banyak hari untuk menyewa..", Toast.LENGTH_SHORT).show();
                }
                else{
                    if(binding.txtPromo.getText().toString().equals("")) {
                        if(tahun==null)
                        {
                            Toast.makeText(TransaksiActivity.this,"Mohon pilih tanggal mulai sewa..", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            checkDriver("0");
                        }

                    }
                    else {
                        if(tahun==null)
                        {
                            Toast.makeText(TransaksiActivity.this,"Mohon pilih tanggal mulai sewa..", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            for(Promo promo : promoList) {
                                if(binding.txtPromo.getText().toString().equals(promo.getKode_promo()))
                                {
                                    id_promo = String.valueOf(promo.getId());
                                }
                            }
                            if(id_promo!=null)
                            {
                                checkDriver(id_promo);
                            }
                            else{
                                Toast.makeText(TransaksiActivity.this,"Promo tidak ditemukan, Kosongkan jika tidak menemukan", Toast.LENGTH_SHORT).show();
                            }

                        }
                    }
                }
            }
        });
        getMobilById(id_mobil);
        getAllPromo();
    }

    private void setLoading(boolean isLoading) {
        LinearLayout layoutLoading = findViewById(R.id.loading_layout);
        if (isLoading) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                    WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
            layoutLoading.setVisibility(View.VISIBLE);
        } else {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
            layoutLoading.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        yearFinal = year;
        monthFinal = month + 1;
        dayFinal = dayOfMonth;

        Calendar calendar = Calendar.getInstance();
        hour = calendar.get(Calendar.HOUR_OF_DAY);
        minute = calendar.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(TransaksiActivity.this,
                TransaksiActivity.this, hour, minute, DateFormat.is24HourFormat(this));
        timePickerDialog.show();
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        hourFinal = hourOfDay;
        minuteFinal = minute;
        convertDate(yearFinal,monthFinal,dayFinal,hourFinal,minuteFinal);
    }

    public void convertDate(int year, int month, int day, int hour, int minute)
    {
        DecimalFormat two_digit = new DecimalFormat("00");
        DecimalFormat four_digit = new DecimalFormat("0000");
        tahun = four_digit.format(year);
        bulan = two_digit.format(month);
        hari = two_digit.format(day);
        jam = two_digit.format(hour);
        menit = two_digit.format(minute);
        binding.etTanggal.setText("Tanggal : " + hari + "-" + bulan + "-" + tahun + ", Jam : " + jam + ":" + menit);
    }

    public void checkDriver(String promo) {
        if(radio_driver.getText().toString().equals("Ya")){
            checkMetodePembayaran("YA", promo);
        }
        else{
            checkMetodePembayaran("", promo);
        }

    }

    public void checkMetodePembayaran(String driver, String promo){
        String sewa_date;
        Transaksi transaksi;
        sewa_date = tahun + "-" + bulan + "-" + hari + " " + jam + ":" + menit + ":00";
        if(radio_metode.getText().toString().equals("Cash")){
            if(update==1){
                transaksi = new Transaksi(binding.txtNoTransaksi.getText().toString(),driver, user.getId_role(), id_mobil,
                        "",sewa_date,"Cash",Double.parseDouble(binding.hargaSewa.getText().toString()),
                        binding.etHari.getText().toString());
                updateTransaksi(transaksi);
            }

            else{
                transaksi = new Transaksi(driver, user.getId_role(),id_mobil, promo, sewa_date,
                        "Cash", Double.parseDouble(binding.hargaSewa.getText().toString()),
                        binding.etHari.getText().toString());
                createTransaksi(transaksi);
            }
        }
        else{
            if(update==1){
                transaksi = new Transaksi(binding.txtNoTransaksi.getText().toString(),driver, user.getId_role(), id_mobil,
                        "",sewa_date,"Transfer",Double.parseDouble(binding.hargaSewa.getText().toString()),
                        binding.etHari.getText().toString());
                updateTransaksi(transaksi);
            }
            else{
                transaksi = new Transaksi(driver, user.getId_role(),id_mobil, promo, sewa_date,
                        "Transfer", Double.parseDouble(binding.hargaSewa.getText().toString()),
                        binding.etHari.getText().toString());
                createTransaksi(transaksi);
            }

        }
    }

    private void createTransaksi(Transaksi transaksi) {
        setLoading(true);
        StringRequest stringRequest = new StringRequest(POST, TransaksiApi.ADD_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Gson gson = new Gson();

                        TransaksiResponse transaksiResponse =
                                gson.fromJson(response, TransaksiResponse.class);
                        Toast.makeText(TransaksiActivity.this,
                                transaksiResponse.getMessage(),
                                Toast.LENGTH_SHORT).show();
                        Intent returnIntent = new Intent();
                        setResult(Activity.RESULT_OK, returnIntent);
                        finish();
                        setLoading(false);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                setLoading(false);
                try {
                    String responseBody = new String(error.networkResponse.data,
                            StandardCharsets.UTF_8);
                    JSONObject errors = new JSONObject(responseBody);
                    Toast.makeText(TransaksiActivity.this,
                            errors.getString("message"), Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Toast.makeText(TransaksiActivity.this, e.getMessage(),
                            Toast.LENGTH_SHORT).show();
                }
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                String auth = "Bearer " + token;
                headers.put("Authorization", auth);
                headers.put("Accept", "application/json");
                return headers;
            }
            @Override
            public byte[] getBody() throws AuthFailureError {
                Gson gson = new Gson();
                String requestBody = gson.toJson(transaksi);
                return requestBody.getBytes(StandardCharsets.UTF_8);
            }
            @Override
            public String getBodyContentType() {
                return "application/json";
            }
        };
        queue.add(stringRequest);
    }

    private void getTransaksiByID(int id) {
        setLoading(true);
        StringRequest stringRequest = new StringRequest(GET,
                TransaksiApi.GET_BY_ID_URL + id, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson = new Gson();
                TransaksiResponse transaksiResponse =
                        gson.fromJson(response, TransaksiResponse.class);
                Transaksi transaksi = transaksiResponse.getTransaksi();
                binding.etTanggal.setText(String.format("Mulai : %s,, Selesai : %s",
                        transaksi.getTarget_mulai_sewa(), transaksi.getTarget_selesai_sewa()));
                binding.etPromo.setEnabled(false);
                binding.etPromo.setVisibility(View.VISIBLE);
                binding.etPromo.setText("nb: Promo hanya bisa diakses pada awal sewa");
                binding.txtPromo.setVisibility(View.GONE);
                binding.etHari.setText("");
                if(transaksi.getMetode_pembayaran().equals("Transfer")){
                    binding.txtTransfer.setChecked(true);
                }
                else{
                    binding.txtCash.setChecked(true);
                }

                if(transaksi.getId_driver() == null){
                    binding.txtTidak.setChecked(true);
                }
                else {
                    binding.txtYa.setChecked(true);
                }
                binding.txtTransaksi.setText("Update Transaksi");
                binding.btnSewa.setText("UPDATE");
                binding.noTransaksi.setVisibility(View.VISIBLE);
                binding.txtNoTransaksi.setVisibility(View.VISIBLE);
                binding.txtNoTransaksi.setText(transaksi.getNo_transaksi());

                Toast.makeText(TransaksiActivity.this,
                        transaksiResponse.getMessage(), Toast.LENGTH_SHORT).show();
                setLoading(false);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                setLoading(false);
                try {
                    String responseBody = new String(error.networkResponse.data,
                            StandardCharsets.UTF_8);
                    JSONObject errors = new JSONObject(responseBody);
                    Toast.makeText(TransaksiActivity.this, errors.getString("message"),
                            Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Toast.makeText(TransaksiActivity.this, e.getMessage(),
                            Toast.LENGTH_SHORT).show();
                }
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                String auth = "Bearer " + token;
                headers.put("Authorization", auth);
                headers.put("Accept", "application/json");
                return headers;
            }
        };
        queue.add(stringRequest);
    }

    private void updateTransaksi(Transaksi transaksi) {
        setLoading(true);

        StringRequest stringRequest = new StringRequest(PUT,
                TransaksiApi.UPDATE_URL + id_transaksi, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson = new Gson();
                TransaksiResponse transaksiResponse =
                        gson.fromJson(response, TransaksiResponse.class);
                Toast.makeText(TransaksiActivity.this,
                        transaksiResponse.getMessage(), Toast.LENGTH_SHORT).show();

                Intent returnIntent = new Intent();
                setResult(Activity.RESULT_OK, returnIntent);
                finish();
                setLoading(false);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                setLoading(false);
                try {
                    String responseBody = new String(error.networkResponse.data,
                            StandardCharsets.UTF_8);
                    JSONObject errors = new JSONObject(responseBody);
                    Toast.makeText(TransaksiActivity.this,
                            errors.getString("message"), Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Toast.makeText(TransaksiActivity.this, e.getMessage(),
                            Toast.LENGTH_SHORT).show();
                }
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                String auth = "Bearer " + token;
                headers.put("Authorization", auth);
                headers.put("Accept", "application/json");
                return headers;
            }
            @Override
            public byte[] getBody() throws AuthFailureError {
                Gson gson = new Gson();
                String requestBody = gson.toJson(transaksi);
                return requestBody.getBytes(StandardCharsets.UTF_8);
            }
            @Override
            public String getBodyContentType() {
                return "application/json";
            }
        };
        queue.add(stringRequest);
    }

    private void getMobilById(int id) {
        setLoading(true);
        StringRequest stringRequest = new StringRequest(GET,
                MobilApi.GET_BY_ID_URL + id, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson = new Gson();
                RequestOptions options = new RequestOptions()
                        .centerCrop()
                        .placeholder(R.mipmap.ic_launcher_round)
                        .error(R.mipmap.ic_launcher_round);

                MobilResponse mobilResponse =
                        gson.fromJson(response, MobilResponse.class);
                Mobil mobil = mobilResponse.getMobil();

                binding.setData(mobil);

                DecimalFormat rupiah = (DecimalFormat) DecimalFormat.getCurrencyInstance(new Locale("in", "ID"));
                binding.hargaSewa.setText(rupiah.format(mobil.getHarga_sewa_per_hari()));

                Toast.makeText(TransaksiActivity.this,
                        mobilResponse.getMessage(), Toast.LENGTH_SHORT).show();
                setLoading(false);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                setLoading(false);
                try {
                    String responseBody = new String(error.networkResponse.data,
                            StandardCharsets.UTF_8);
                    JSONObject errors = new JSONObject(responseBody);
                    Toast.makeText(TransaksiActivity.this, errors.getString("message"),
                            Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Toast.makeText(TransaksiActivity.this, e.getMessage(),
                            Toast.LENGTH_SHORT).show();
                }
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                String auth = "Bearer " + token;
                headers.put("Authorization", auth);
                headers.put("Accept", "application/json");
                return headers;
            }
        };
        queue.add(stringRequest);
    }

    private void getAllPromo(){
        StringRequest stringRequest = new StringRequest(GET,
                PromoApi.GET_ALL_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson = new Gson();
                PromoResponse promoResponse = gson.fromJson(response, PromoResponse.class);
                promoList = promoResponse.getPromoList();
                Toast.makeText(TransaksiActivity.this,
                        promoResponse.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                try{
                    String responseBody = new String(error.networkResponse.data,
                            StandardCharsets.UTF_8);
                    JSONObject errors = new JSONObject(responseBody);
                    Toast.makeText(getApplicationContext(),
                            errors.getString("message"), Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                String auth = "Bearer " + userPreferences.getUserLogin().getAccess_token();
                headers.put("Authorization", auth);
                headers.put("Accept", "application/json");
                return headers;
            }
        };
        queue.add(stringRequest);
    }

    public void checkButtonDriver(View v) {
        int radioId = binding.radioDriver.getCheckedRadioButtonId();

        radio_driver = findViewById(radioId);
        Toast.makeText(this, "Memakai Driver : " + radio_driver.getText(), Toast.LENGTH_SHORT).show();
    }

    public void checkButtonPay(View v) {
        int radioId = binding.radioMetode.getCheckedRadioButtonId();

        radio_metode = findViewById(radioId);
        Toast.makeText(this, "Metode Pembayaran : " + radio_metode.getText(), Toast.LENGTH_SHORT).show();
    }

}