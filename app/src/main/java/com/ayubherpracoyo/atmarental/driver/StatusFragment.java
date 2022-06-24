package com.ayubherpracoyo.atmarental.driver;

import static com.android.volley.Request.Method.GET;
import static com.android.volley.Request.Method.PUT;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.ayubherpracoyo.atmarental.LoginActivity;
import com.ayubherpracoyo.atmarental.R;
import com.ayubherpracoyo.atmarental.TransaksiActivity;
import com.ayubherpracoyo.atmarental.api.DriverApi;
import com.ayubherpracoyo.atmarental.api.MobilApi;
import com.ayubherpracoyo.atmarental.api.TransaksiApi;
import com.ayubherpracoyo.atmarental.databinding.FragmentHomeBinding;
import com.ayubherpracoyo.atmarental.databinding.FragmentStatusBinding;
import com.ayubherpracoyo.atmarental.model.Driver;
import com.ayubherpracoyo.atmarental.model.DriverResponse;
import com.ayubherpracoyo.atmarental.model.Mobil;
import com.ayubherpracoyo.atmarental.model.MobilResponse;
import com.ayubherpracoyo.atmarental.model.TransaksiResponse;
import com.ayubherpracoyo.atmarental.model.User;
import com.ayubherpracoyo.atmarental.preferences.UserPreferences;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

public class StatusFragment extends Fragment {
    private User user;
    private UserPreferences userPreferences;
    private FragmentStatusBinding binding;
    private RequestQueue queue;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding =  DataBindingUtil.inflate(
                inflater, R.layout.fragment_status, container, false);
        userPreferences = new UserPreferences(getContext());
        user = userPreferences.getUserLogin();
        queue = Volley.newRequestQueue(getContext());

        checkLogin();

        binding.tvWelcome2.setText("Mohon untuk melakukan update status ketersediaan secara berkala.");
        binding.btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userPreferences.logout();
                Toast.makeText(getContext(), "Logout..", Toast.LENGTH_SHORT).show();
                checkLogin();
            }
        });
        binding.btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Driver driver;
                if(binding.buttonOffline.isChecked()){
                    driver = new Driver(0);
                    updateDriverStatus(user.getId_role(),driver);
                }
                else if(binding.buttonAvailable.isChecked()){
                    driver = new Driver(1);
                    updateDriverStatus(user.getId_role(),driver);
                }
                else if(binding.buttonOnRent.isChecked()){
                    driver = new Driver(2);
                    updateDriverStatus(user.getId_role(),driver);
                }
            }
        });
        binding.buttonOffline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.buttonOffline.setChecked(true);
                Toast.makeText(getContext(), "Status : Offline (Terpilih)", Toast.LENGTH_SHORT).show();
            }
        });
        binding.buttonAvailable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.buttonAvailable.setChecked(true);
                Toast.makeText(getContext(), "Status : Available (Terpilih)", Toast.LENGTH_SHORT).show();
            }
        });

        getDriverById(user.getId_role());

        return binding.getRoot();
    }

    private void checkLogin(){
        /* this function will check if user login , akan memunculkan toast jika tidak redirect ke login activity */
        if(!userPreferences.checkLogin()){
            startActivity(new Intent(getContext(), LoginActivity.class));
            getActivity().finish();
        }
    }

    private void updateDriverStatus(String id, Driver driver){
        setLoading(true);
        StringRequest stringRequest = new StringRequest(PUT,
                DriverApi.UPDATE_STATUS_URL + id, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson = new Gson();
                DriverResponse driverResponse =
                        gson.fromJson(response, DriverResponse.class);
                Toast.makeText(getContext(),
                        driverResponse.getMessage(), Toast.LENGTH_SHORT).show();

                startActivity(new Intent(getActivity(), DriverActivity.class));
                Objects.requireNonNull(getActivity()).finish();
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
                    Toast.makeText(getContext(),
                            errors.getString("message"), Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Toast.makeText(getContext(), e.getMessage(),
                            Toast.LENGTH_SHORT).show();
                }
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                String auth = "Bearer " + user.getAccess_token();
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

    private void getDriverById(String id) {
        setLoading(true);
        StringRequest stringRequest = new StringRequest(GET,
                DriverApi.GET_BY_ID_URL + id, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson = new Gson();
                RequestOptions options = new RequestOptions()
                        .centerCrop()
                        .placeholder(R.mipmap.ic_launcher_round)
                        .error(R.mipmap.ic_launcher_round);

                DriverResponse driverResponse =
                        gson.fromJson(response, DriverResponse.class);
                Driver driver = driverResponse.getDriver();

                binding.tvWelcome.setText("Selamat datang, \nDriver : "+driver.getNama_driver());
                binding.ratingBar.setRating(driver.getRating_driver());
                if(driver.getStatus_driver()==0)
                {
                    binding.buttonOffline.setChecked(true);
                }
                else if(driver.getStatus_driver()==1)
                {
                    binding.buttonAvailable.setChecked(true);
                }
                else if(driver.getStatus_driver()==2)
                {
                    binding.buttonAvailable.setEnabled(false);
                    binding.buttonOffline.setEnabled(false);
                    binding.btnUpdate.setEnabled(false);
                    binding.buttonOnRent.setChecked(true);
                    binding.tvWelcome2.setText("Anda sedang dalam penyewaan mobil Atma Rental. Tidak dapat mengubah status hingga penyewaan selesai.");
                }

                Toast.makeText(getContext(),
                        driverResponse.getMessage(), Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(getContext(), errors.getString("message"),
                            Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Toast.makeText(getContext(), e.getMessage(),
                            Toast.LENGTH_SHORT).show();
                }
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                String auth = "Bearer " + user.getAccess_token();
                headers.put("Authorization", auth);
                headers.put("Accept", "application/json");
                return headers;
            }
        };
        queue.add(stringRequest);
    }

    private void setLoading(boolean isLoading) {
        LinearLayout layoutLoading = getActivity().findViewById(R.id.loading_layout);
        if (isLoading) {
            getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                    WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
            layoutLoading.setVisibility(View.VISIBLE);
        } else {
            getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
            layoutLoading.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}