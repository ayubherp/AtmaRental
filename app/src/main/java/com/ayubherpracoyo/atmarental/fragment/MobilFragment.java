package com.ayubherpracoyo.atmarental.fragment;

import static com.android.volley.Request.Method.GET;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
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
import com.ayubherpracoyo.atmarental.R;
import com.ayubherpracoyo.atmarental.adapter.MobilAdapter;
import com.ayubherpracoyo.atmarental.api.MobilApi;
import com.ayubherpracoyo.atmarental.databinding.FragmentMobilBinding;
import com.ayubherpracoyo.atmarental.model.MobilResponse;
import com.ayubherpracoyo.atmarental.preferences.UserPreferences;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MobilFragment extends Fragment {

    private RequestQueue queue;
    private MobilAdapter adapter;
    private FragmentMobilBinding binding;
    private UserPreferences userPreferences;

    public MobilFragment(){
    }
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_mobil, container, false);
        queue = Volley.newRequestQueue(this.getContext());
        userPreferences = new UserPreferences(getContext());
        binding.svItem.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
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

        adapter = new MobilAdapter(new ArrayList<>(), this.getContext());
        int orientation = getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            binding.rvItem.setLayoutManager(new GridLayoutManager(this.getContext(), 4));
        } else {
            binding.rvItem.setLayoutManager(new GridLayoutManager(this.getContext(), 2));
        }
        binding.rvItem.setAdapter(adapter);
        binding.srItem.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getAllItem();
            }
        });
        getAllItem();
        return binding.getRoot();
    }


    private void getAllItem(){
        binding.srItem.setRefreshing(true);

        StringRequest stringRequest = new StringRequest(GET,
                MobilApi.GET_ALL_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson = new Gson();
                MobilResponse mobilResponse = gson.fromJson(response, MobilResponse.class);
                adapter.setItemList(mobilResponse.getMobilList());
                adapter.getFilter().filter(binding.svItem.getQuery());
                Toast.makeText(getContext(),
                        mobilResponse.getMessage(), Toast.LENGTH_SHORT).show();
                binding.srItem.setRefreshing(false);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                binding.srItem.setRefreshing(false);
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