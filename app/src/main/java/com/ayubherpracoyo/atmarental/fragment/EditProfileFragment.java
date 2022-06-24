package com.ayubherpracoyo.atmarental.fragment;

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
import com.ayubherpracoyo.atmarental.api.UserApi;
import com.ayubherpracoyo.atmarental.databinding.FragmentEditProfileBinding;
import com.ayubherpracoyo.atmarental.model.Customer;
import com.ayubherpracoyo.atmarental.model.CustomerResponse;
import com.ayubherpracoyo.atmarental.model.User;
import com.ayubherpracoyo.atmarental.model.UserResponse;
import com.ayubherpracoyo.atmarental.preferences.UserPreferences;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class EditProfileFragment extends Fragment {
    private RequestQueue queue;
    private Handler handler;
    private UserPreferences userPreferences;
    private User user;
    private FragmentEditProfileBinding binding;
    private int jumlah_transaksi;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding =  DataBindingUtil.inflate(
                inflater, R.layout.fragment_edit_profile, container, false);
        userPreferences = new UserPreferences(getContext());
        user = userPreferences.getUserLogin();
        queue = Volley.newRequestQueue(this.getContext());

        getCustomerData();
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateUser(user.getId());
                Intent i = new Intent(getContext(), MainActivity.class);
                startActivity(i);
            }
        });

        binding.btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), MainActivity.class);
                startActivity(i);
            }
        });

        binding.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteUser(user.getId());
                userPreferences.logout();
                Toast.makeText(getActivity(), "Data Anda Sudah Dihapus", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void deleteUser(long id) {
        StringRequest stringRequest = new StringRequest(DELETE, UserApi.DELETE_URL + id, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson = new Gson();
                UserResponse itemResponse = gson.fromJson(response, UserResponse.class);
                Toast.makeText(getActivity(), itemResponse.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                try {
                    String responseBody = new String(error.networkResponse.data, StandardCharsets.UTF_8);
                    JSONObject errors = new JSONObject(responseBody);
                    Toast.makeText(getActivity(), errors.getString("message"), Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
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

    private void updateUser(long id) {
        Customer customer  = new Customer(binding.etNama.getText().toString(), binding.etAlamat.getText().toString(),
                binding.etTelepon.getText().toString());

        StringRequest stringRequest = new StringRequest(PUT, CustomerApi.UPDATE_URL + id, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson = new Gson();
                CustomerResponse customerResponse = gson.fromJson(response, CustomerResponse.class);
                Toast.makeText(getContext(),
                        customerResponse.getMessage(), Toast.LENGTH_SHORT).show();
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
                String requestBody = gson.toJson(customer);
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

    private void getCustomerData(){
        StringRequest stringRequest = new StringRequest(GET,
                CustomerApi.GET_BY_ID_URL + userPreferences.getUserLogin().getId_role(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson = new Gson();
                CustomerResponse customerResponse = gson.fromJson(response, CustomerResponse.class);
                binding.idCustomer.setText(customerResponse.getCustomer().getId());
                binding.etNama.setText(customerResponse.getCustomer().getNama_customer());
                binding.etAlamat.setText(customerResponse.getCustomer().getAlamat_customer());
                binding.etGender.setText(customerResponse.getCustomer().getGender_customer());
                binding.etGender.setEnabled(false);
                binding.etKtp.setText(customerResponse.getCustomer().getNo_ktp_customer());
                binding.etKtp.setEnabled(false);
                binding.etSim.setText(customerResponse.getCustomer().getNo_sim_customer());
                binding.etSim.setEnabled(false);
                binding.etTelepon.setText(customerResponse.getCustomer().getNo_telepon_customer());
                binding.etTanggal.setText(customerResponse.getCustomer().getTgl_lahir());
                binding.etTanggal.setEnabled(false);
                jumlah_transaksi=customerResponse.getCustomer().getJumlah_transaksi_customer();
                Toast.makeText(getContext(),
                        customerResponse.getMessage(), Toast.LENGTH_SHORT).show();
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

