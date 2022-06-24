package com.ayubherpracoyo.atmarental;

import static com.android.volley.Request.Method.DELETE;
import static com.android.volley.Request.Method.PUT;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.ayubherpracoyo.atmarental.api.TransaksiApi;
import com.ayubherpracoyo.atmarental.databinding.ActivityMainBinding;
import com.ayubherpracoyo.atmarental.fragment.HomeFragment;
import com.ayubherpracoyo.atmarental.fragment.MobilFragment;
import com.ayubherpracoyo.atmarental.fragment.ProfileFragment;
import com.ayubherpracoyo.atmarental.fragment.PromoFragment;
import com.ayubherpracoyo.atmarental.fragment.RiwayatFragment;
import com.ayubherpracoyo.atmarental.fragment.TransaksiFragment;
import com.ayubherpracoyo.atmarental.model.Transaksi;
import com.ayubherpracoyo.atmarental.model.TransaksiResponse;
import com.ayubherpracoyo.atmarental.model.User;
import com.ayubherpracoyo.atmarental.preferences.UserPreferences;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private User user;
    private UserPreferences userPreferences;
    private RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        queue = Volley.newRequestQueue(this);
        userPreferences = new UserPreferences(getApplicationContext());
        user = userPreferences.getUserLogin();
        binding.navView.setSelectedItemId(R.id.navigation_home);
        binding.navView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menu) {
                switch(menu.getItemId()){
                    case R.id.navigation_home:
                        changeFragment(new HomeFragment());
                        return true;
                    case R.id.navigation_profile:
                        changeFragment(new ProfileFragment());
                        return true;
                    case R.id.navigation_mobil:
                        changeFragment(new MobilFragment());
                        return true;
                    case R.id.navigation_promo:
                        changeFragment(new PromoFragment());
                        return true;
                    case R.id.navigation_transaksi:
                        changeFragment(new TransaksiFragment());
                        return true;

                }
                return false;
            }
        });
        changeFragment(new HomeFragment());
    }
    public void changeFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.layout_fragment_main,fragment)
                .commit();
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

    public void bringMobilToTransaksi(int id) {
        Intent intent = new Intent(MainActivity.this, TransaksiActivity.class);
        intent.putExtra("token",userPreferences.getUserLogin().getAccess_token());
        intent.putExtra("user_id",userPreferences.getUserLogin().getId());
        intent.putExtra("user_name",userPreferences.getUserLogin().getName());
        intent.putExtra("id_mobil", id);
        startActivity(intent);
    }

    public void bringTransaksiToUpdateTransaksi(int id_transaksi, int id_mobil) {
        Intent intent = new Intent(MainActivity.this, TransaksiActivity.class);
        intent.putExtra("token",userPreferences.getUserLogin().getAccess_token());
        intent.putExtra("user_id",userPreferences.getUserLogin().getId());
        intent.putExtra("user_name",userPreferences.getUserLogin().getName());
        intent.putExtra("id_transaksi", id_transaksi);
        intent.putExtra("id_mobil", id_mobil);
        intent.putExtra("update",1);
        startActivity(intent);
    }

    public void deleteTransaksi(int id) {
        setLoading(true);
        StringRequest stringRequest = new StringRequest(DELETE, TransaksiApi.DELETE_URL + id, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson = new Gson();
                TransaksiResponse transaksiResponse = gson.fromJson(response, TransaksiResponse.class);
                setLoading(false);
                Toast.makeText(MainActivity.this,
                        transaksiResponse.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                setLoading(false);
                try {
                    String responseBody = new String(error.networkResponse.data, StandardCharsets.UTF_8);
                    JSONObject errors = new JSONObject(responseBody);
                    Toast.makeText(MainActivity.this, errors.getString("message"), Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
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

    public void submitRating(int id_transaksi, float rating) {
        setLoading(true);
        Transaksi transaksi = new Transaksi(id_transaksi,rating);
        StringRequest stringRequest = new StringRequest(PUT,
                TransaksiApi.SUBMIT_RATING + id_transaksi, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson = new Gson();
                TransaksiResponse transaksiResponse =
                        gson.fromJson(response, TransaksiResponse.class);
                Toast.makeText(MainActivity.this,
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
                    Toast.makeText(MainActivity.this,
                            errors.getString("message"), Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Toast.makeText(MainActivity.this, e.getMessage(),
                            Toast.LENGTH_SHORT).show();
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
}