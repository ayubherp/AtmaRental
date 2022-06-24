package com.ayubherpracoyo.atmarental.driver;

import static com.android.volley.Request.Method.DELETE;
import static com.android.volley.Request.Method.GET;
import static com.android.volley.Request.Method.PUT;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.ayubherpracoyo.atmarental.MainActivity;
import com.ayubherpracoyo.atmarental.R;
import com.ayubherpracoyo.atmarental.api.CustomerApi;
import com.ayubherpracoyo.atmarental.api.DriverApi;
import com.ayubherpracoyo.atmarental.api.UserApi;
import com.ayubherpracoyo.atmarental.databinding.FragmentEditDriverBinding;
import com.ayubherpracoyo.atmarental.databinding.FragmentEditProfileBinding;
import com.ayubherpracoyo.atmarental.model.Customer;
import com.ayubherpracoyo.atmarental.model.CustomerResponse;
import com.ayubherpracoyo.atmarental.model.Driver;
import com.ayubherpracoyo.atmarental.model.DriverResponse;
import com.ayubherpracoyo.atmarental.model.User;
import com.ayubherpracoyo.atmarental.model.UserResponse;
import com.ayubherpracoyo.atmarental.preferences.UserPreferences;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class EditDriverFragment extends Fragment {
    private RequestQueue queue;
    private Handler handler;
    private UserPreferences userPreferences;
    private User user;
    private FragmentEditDriverBinding binding;
    private int jumlah_transaksi;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding =  DataBindingUtil.inflate(
                inflater, R.layout.fragment_edit_driver, container, false);
        userPreferences = new UserPreferences(getContext());
        user = userPreferences.getUserLogin();
        queue = Volley.newRequestQueue(this.getContext());

        getDriverData();
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateDriver(user.getId_role());
                Intent i = new Intent(getContext(), DriverActivity.class);
                startActivity(i);
            }
        });

        binding.btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), DriverActivity.class);
                startActivity(i);
            }
        });
    }

    private void updateDriver(String id) {
        Driver driver  = new Driver(binding.etNama.getText().toString(), binding.etAlamat.getText().toString(),
                binding.etTelepon.getText().toString());

        StringRequest stringRequest = new StringRequest(PUT, DriverApi.UPDATE_URL + id, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson = new Gson();
                DriverResponse driverResponse = gson.fromJson(response, DriverResponse.class);
                userPreferences.getUserLogin().setFull_name(binding.etNama.getText().toString());
                Toast.makeText(getContext(),
                        driverResponse.getMessage(), Toast.LENGTH_SHORT).show();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                try {
                    String responseBody = new String(error.networkResponse.data,
                            StandardCharsets.UTF_8);
                    JSONObject errors = new JSONObject(responseBody);
                    Toast.makeText(getContext(),
                            errors.getString("message"), Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Toast.makeText(getContext(),
                            e.getMessage(), Toast.LENGTH_SHORT).show();
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
                String requestBody = gson.toJson(driver);
                return requestBody.getBytes(StandardCharsets.UTF_8);
            }

            @Override
            public String getBodyContentType() {
                return "application/json";
            }
        };
        queue.add(stringRequest);
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void getDriverData(){
        StringRequest stringRequest = new StringRequest(GET,
                DriverApi.GET_BY_ID_URL + user.getId_role(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson = new Gson();
                DriverResponse driverResponse = gson.fromJson(response, DriverResponse.class);
                binding.idDriver.setText(driverResponse.getDriver().getId());
                binding.etNama.setText(driverResponse.getDriver().getNama_driver());
                binding.etAlamat.setText(driverResponse.getDriver().getAlamat_driver());
                binding.etTelepon.setText(driverResponse.getDriver().getNo_telepon_driver());
                binding.etEmail.setText(driverResponse.getDriver().getEmail_driver());
                binding.etEmail.setEnabled(false);
                binding.etGender.setText(driverResponse.getDriver().getGender_driver());
                binding.etGender.setEnabled(false);
                binding.etTanggal.setText(driverResponse.getDriver().getTgl_lahir_driver());
                binding.etTanggal.setEnabled(false);
                binding.etBahasa.setText(driverResponse.getDriver().getBahasa_driver());
                binding.etBahasa.setEnabled(false);
                Toast.makeText(getContext(),
                        driverResponse.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                try{
                    String responseBody = new String(error.networkResponse.data,
                            StandardCharsets.UTF_8);
                    JSONObject errors = new JSONObject(responseBody);
                    Toast.makeText(getContext(),
                            errors.getString("message"), Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        })  {
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
}

