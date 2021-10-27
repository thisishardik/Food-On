package com.example.foodon;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.foodon.CustomerFoodPanel.CustomerCartFragment;
import com.example.foodon.CustomerFoodPanel.CustomerHomeFragment;
import com.example.foodon.CustomerFoodPanel.CustomerOrderFragment;
import com.example.foodon.CustomerFoodPanel.CustomerProfileFragment;
import com.example.foodon.CustomerFoodPanel.CustomerTrackFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class CustFoodPanelActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cust_food_panel);
        BottomNavigationView navigationView = findViewById(R.id.bottom_navigation);
        navigationView.setOnNavigationItemSelectedListener(this);
        String name = getIntent().getStringExtra("PAGE");
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        if (name != null) {
            if (name.equalsIgnoreCase("HomePage")) {
                loadChefFragment(new CustomerHomeFragment());
            } else if (name.equalsIgnoreCase("PreparingPage")) {
                loadChefFragment(new CustomerTrackFragment());
            } else if (name.equalsIgnoreCase("PreparedPage")) {
                loadChefFragment(new CustomerTrackFragment());
            } else if (name.equalsIgnoreCase("DeliverOrderPage")) {
                loadChefFragment(new CustomerTrackFragment());
            } else if (name.equalsIgnoreCase("ThankYouPage")) {
                loadChefFragment(new CustomerHomeFragment());
            }
        } else {
            loadChefFragment(new CustomerHomeFragment());
        }
    }
    
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment = null;
        switch (item.getItemId()) {
            case R.id.cust_home:
                fragment = new CustomerHomeFragment();
                break;
        }
        switch (item.getItemId()) {
            case R.id.cust_cart:
                fragment = new CustomerCartFragment();
                break;
        }
        switch (item.getItemId()) {
            case R.id.cust_profile:
                fragment = new CustomerProfileFragment();
                break;
        }
        switch (item.getItemId()) {
            case R.id.cust_order:
                fragment = new CustomerOrderFragment();
                break;
        }
        switch (item.getItemId()) {
            case R.id.cust_track:
                fragment = new CustomerTrackFragment();
                break;
        }
        return loadChefFragment(fragment);
    }

    private boolean loadChefFragment(Fragment fragment) {
        if (fragment != null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();
            return true;
        }
        return false;
    }
}