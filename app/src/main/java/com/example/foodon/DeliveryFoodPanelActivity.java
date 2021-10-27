package com.example.foodon;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.foodon.CustomerFoodPanel.CustomerHomeFragment;
import com.example.foodon.CustomerFoodPanel.CustomerTrackFragment;
import com.example.foodon.DeliveryFoodPanel.DeliveryPendingOrdersFragment;
import com.example.foodon.DeliveryFoodPanel.DeliveryShipOrdersFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class DeliveryFoodPanelActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_food_panel);
        BottomNavigationView navigationView = findViewById(R.id.delivery_bottom_navigation);
        navigationView.setOnNavigationItemSelectedListener(this);
        String name = getIntent().getStringExtra("PAGE");
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        if (name != null) {
            if (name.equalsIgnoreCase("DeliveryOrderPage")) {
                loadDeliveryFragment(new DeliveryPendingOrdersFragment());
            }
        } else {
            loadDeliveryFragment(new DeliveryShipOrdersFragment());
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment = null;
        switch (item.getItemId()) {
            case R.id.ship_orders:
                fragment = new DeliveryShipOrdersFragment();
                break;
        }
        switch (item.getItemId()) {
            case R.id.pending_orders:
                fragment = new DeliveryPendingOrdersFragment();
                break;
        }
        return loadDeliveryFragment(fragment);
    }

    private boolean loadDeliveryFragment(Fragment fragment) {
        if (fragment != null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();
            return true;
        }
        return false;
    }
}