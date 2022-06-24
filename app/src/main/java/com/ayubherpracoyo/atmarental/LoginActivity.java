package com.ayubherpracoyo.atmarental;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import com.ayubherpracoyo.atmarental.api.UserApi;
import com.ayubherpracoyo.atmarental.databinding.ActivityLoginBinding;

import com.ayubherpracoyo.atmarental.driver.DriverActivity;
import com.ayubherpracoyo.atmarental.model.AuthResponse;
import com.ayubherpracoyo.atmarental.model.Customer;
import com.ayubherpracoyo.atmarental.model.Driver;
import com.ayubherpracoyo.atmarental.model.Pegawai;
import com.ayubherpracoyo.atmarental.model.User;
import com.ayubherpracoyo.atmarental.preferences.UserPreferences;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    public static final String KEY_EMAIL = "email";
    public static final String KEY_PASSWORD = "password";
    private ActivityLoginBinding binding;
    private UserPreferences userPreferences;
    private RequestQueue queue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        userPreferences = new UserPreferences(LoginActivity.this);
        queue = Volley.newRequestQueue(this);

        /* Apps will check the login first from shared preferences */
        checkLogin();

        /* to clear the field just set text to "" */
        binding.btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.etEmail.setText("");
                binding.etPassword.setText("");
            }
        });

        binding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validateForm()){
                    attemptLogin();
                }
            }
        });

    }


    private void attemptLogin(){
        setLoading(true);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, UserApi.LOGIN_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String success = jsonObject.getString("success");
                    String token = jsonObject.getString("access_token");
                    if(success.equals("1"))
                    {
                        Gson gson = new Gson();
                        AuthResponse authResponse = gson.fromJson(response, AuthResponse.class);
                        User user = authResponse.getUser();
                        Customer customer = authResponse.getCustomer();
                        Pegawai pegawai = authResponse.getPegawai();
                        Driver driver = authResponse.getDriver();

                        if(user.getEmail_verified_at()!=null)
                        {
                            if(customer!=null)
                            {
                                userPreferences.setUser(user.getId(), user.getName(),
                                        user.getEmail(), user.getPassword(), token, user.getRole(), customer.getId(), customer.getNama_customer());
                                checkLogin();
                            }
                            else if(pegawai!=null){
                                userPreferences.setUser(user.getId(), user.getName(),
                                        user.getEmail(), user.getPassword(), token, user.getRole(), pegawai.getId(), pegawai.getNama_pegawai());
                                checkLogin();
                            }
                            else if(driver!=null){
                                userPreferences.setUser(user.getId(), user.getName(),
                                        user.getEmail(), user.getPassword(), token, user.getRole(), driver.getId(), driver.getNama_driver());
                                checkLogin();
                            }

                        }
                        else
                        {
                            Toast.makeText(LoginActivity.this,
                                    "Harus verifikasi email terlebih dahulu. Cek email Anda.", Toast.LENGTH_SHORT).show();

                        }
                        setLoading(false);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    setLoading(false);
                }

                checkLogin();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                try {
                    String responseBody = new String(error.networkResponse.data, StandardCharsets.UTF_8);
                    JSONObject errors = new JSONObject(responseBody);
                    Toast.makeText(LoginActivity.this, errors.getString("message"), Toast.LENGTH_SHORT).show();
                    setLoading(false);
                } catch (Exception e) {
                    Toast.makeText(LoginActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    setLoading(false);
                }
            }
        }) {
            @Override
            public Map<String, String> getParams() throws AuthFailureError{
                Map<String, String> params= new HashMap<String, String>();
                params.put(KEY_EMAIL, binding.etEmail.getText().toString());
                params.put(KEY_PASSWORD, binding.etPassword.getText().toString());
                return params;
            }
        };
        queue.add(stringRequest);
    }

    private boolean validateForm(){
        /* Check username & password is empty or not */
        if(binding.etEmail.getText().toString().trim().isEmpty() || binding.etPassword.getText().toString().trim().isEmpty()){
            Toast.makeText(LoginActivity.this,"Username or Password still empty",Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void checkLogin(){
        if(userPreferences.checkLogin() && userPreferences.getUserLogin().getRole().equals("Customer"))
        {
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();
        }
        else if(userPreferences.checkLogin() && userPreferences.getUserLogin().getRole().equals("Manager")){
            startActivity(new Intent(LoginActivity.this, ManagerActivity.class));
            finish();
        }
        else if(userPreferences.checkLogin() && userPreferences.getUserLogin().getRole().equals("Driver")){
            startActivity(new Intent(LoginActivity.this, DriverActivity.class));
            finish();
        }
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

}