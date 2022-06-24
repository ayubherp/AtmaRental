package com.ayubherpracoyo.atmarental.fragment;

import static com.android.volley.Request.Method.GET;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.ayubherpracoyo.atmarental.MainActivity;
import com.ayubherpracoyo.atmarental.R;
import com.ayubherpracoyo.atmarental.adapter.TransaksiAdapter;
import com.ayubherpracoyo.atmarental.api.TransaksiApi;
import com.ayubherpracoyo.atmarental.databinding.FragmentTransaksiBinding;
import com.ayubherpracoyo.atmarental.model.Transaksi;
import com.ayubherpracoyo.atmarental.model.TransaksiResponse;
import com.ayubherpracoyo.atmarental.preferences.UserPreferences;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TransaksiFragment extends Fragment {
    private FragmentTransaksiBinding binding;
    private RequestQueue queue;
    private TransaksiAdapter adapter;
    private UserPreferences userPreferences;
    private int refresh=0;

    public TransaksiFragment(){
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_transaksi,container, false);
        queue = Volley.newRequestQueue(this.getContext());
        userPreferences = new UserPreferences(getContext());

        binding.btnRiwayat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).changeFragment(new RiwayatFragment());
            }
        });
        binding.srUser.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if(refresh<1){
                    getAllTransaksi();
                    refresh++;
                }
                else
                    binding.srUser.setRefreshing(false);

            }
        });
        binding.svUser.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });
        adapter = new TransaksiAdapter(new ArrayList<>(), this.getContext());
        binding.rvUser.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        binding.rvUser.setAdapter(adapter);
        getAllTransaksi();
        refresh++;
        return binding.getRoot();
    }

    private void getAllTransaksi() {
        StringRequest stringRequest = new StringRequest(GET, TransaksiApi.GET_ALL_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson = new Gson();
                TransaksiResponse transaksiResponse = gson.fromJson(response, TransaksiResponse.class);

                adapter.setTransaksiList(transaksiResponse.getTransaksiList(), userPreferences.getUserLogin());
                adapter.getFilter().filter(binding.svUser.getQuery());
                Toast.makeText(getContext(), transaksiResponse.getMessage(), Toast.LENGTH_SHORT).show();
                binding.srUser.setRefreshing(false);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                try {
                    String responseBody = new String(error.networkResponse.data, StandardCharsets.UTF_8);
                    JSONObject errors = new JSONObject(responseBody);
                    Toast.makeText(getContext(), errors.getString("message"), Toast.LENGTH_SHORT).show();
                    binding.srUser.setRefreshing(false);
                } catch (Exception e) {
                    Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    binding.srUser.setRefreshing(false);
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
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}