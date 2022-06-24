package com.ayubherpracoyo.atmarental.driver;

import static com.android.volley.Request.Method.GET;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.ayubherpracoyo.atmarental.LoginActivity;
import com.ayubherpracoyo.atmarental.MainActivity;
import com.ayubherpracoyo.atmarental.R;
import com.ayubherpracoyo.atmarental.api.DriverApi;
import com.ayubherpracoyo.atmarental.databinding.FragmentProfileBinding;
import com.ayubherpracoyo.atmarental.databinding.FragmentProfileDriverBinding;
import com.ayubherpracoyo.atmarental.fragment.EditProfileFragment;
import com.ayubherpracoyo.atmarental.model.Driver;
import com.ayubherpracoyo.atmarental.model.DriverResponse;
import com.ayubherpracoyo.atmarental.model.User;
import com.ayubherpracoyo.atmarental.preferences.UserPreferences;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;


public class ProfileDriverFragment extends Fragment {
    private User user;
    private UserPreferences userPreferences;
    private FragmentProfileDriverBinding binding;
    private RequestQueue queue;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_profile_driver, container, false);

        userPreferences = new UserPreferences(getContext());
        user = userPreferences.getUserLogin();
        queue = Volley.newRequestQueue(getContext());
        checkLogin();
        getDriverById(user.getId_role());

        binding.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((DriverActivity)getActivity()).changeFragment(new EditDriverFragment());
            }
        });

        return binding.getRoot();
    }

    private void checkLogin(){
        /* check if user login */
        if(!userPreferences.checkLogin()){
            startActivity(new Intent(getContext(), LoginActivity.class));
            getActivity().finish();
        }
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
                String link = "http://atmarental.my.id" + driver.getFoto_driver();
                binding.setDriver(driver);
                Glide.with(getContext()).load(link)
                        .apply(options).dontAnimate().into(binding.imgDriver);

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