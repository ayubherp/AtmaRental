package com.ayubherpracoyo.atmarental.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.ayubherpracoyo.atmarental.R;
import com.ayubherpracoyo.atmarental.databinding.FragmentHomeBinding;
import com.ayubherpracoyo.atmarental.model.User;
import com.ayubherpracoyo.atmarental.preferences.UserPreferences;
import com.ayubherpracoyo.atmarental.LoginActivity;
import com.google.android.material.button.MaterialButton;

public class HomeFragment extends Fragment {
    private User user;
    private UserPreferences userPreferences;
    private FragmentHomeBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding =  DataBindingUtil.inflate(
                inflater, R.layout.fragment_home, container, false);
        userPreferences = new UserPreferences(getContext());
        user = userPreferences.getUserLogin();

        checkLogin();

        binding.tvWelcome.setText("Selamat datang di Atma Rental, \n"+user.getFull_name());
        binding.tvWelcome2.setText("Silakan bereksplorasi bersama kami :D");
        binding.btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userPreferences.logout();
                Toast.makeText(getContext(), "Kami menunggu kehadiran Anda.", Toast.LENGTH_SHORT).show();
                checkLogin();
            }
        });

        return binding.getRoot();
    }

    private void checkLogin(){
        /* this function will check if user login , akan memunculkan toast jika tidak redirect ke login activity */
        if(!userPreferences.checkLogin()){
            startActivity(new Intent(getContext(), LoginActivity.class));
            getActivity().finish();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
