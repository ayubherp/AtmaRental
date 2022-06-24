package com.ayubherpracoyo.atmarental.driver;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioButton;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.ayubherpracoyo.atmarental.R;
import com.ayubherpracoyo.atmarental.databinding.ActivityDriverBinding;
import com.ayubherpracoyo.atmarental.databinding.FragmentStatusBinding;
import com.ayubherpracoyo.atmarental.fragment.HomeFragment;
import com.ayubherpracoyo.atmarental.model.User;
import com.ayubherpracoyo.atmarental.preferences.UserPreferences;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Objects;

public class DriverActivity extends AppCompatActivity {
    private ActivityDriverBinding binding;
    private User user;
    private UserPreferences userPreferences;
    private RequestQueue queue;
    private FragmentStatusBinding fragmentStatusBinding;
    RadioButton radioStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDriverBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        queue = Volley.newRequestQueue(this);
        userPreferences = new UserPreferences(getApplicationContext());
        user = userPreferences.getUserLogin();

        binding.navView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menu) {
                switch (menu.getItemId()){
                    case R.id.navigation_status:
                        changeFragment(new StatusFragment());
                        return true;
                    case R.id.navigation_driver:
                        changeFragment(new ProfileDriverFragment());
                        return true;
                }
                return false;
            }

        });
        changeFragment(new StatusFragment());
    }
    public void changeFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.layout_fragment_driver,fragment)
                .commit();
    }
}