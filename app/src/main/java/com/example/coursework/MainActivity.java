package com.example.coursework;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.coursework.Trips.AddTripActivity;
import com.example.coursework.databinding.ActivityMainBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Reference https://www.geeksforgeeks.org/view-binding-in-android-jetpack/
        // inflating our xml layout in our activity main binding
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        // getting our root layout in our view.
        View view = binding.getRoot();
        // Content view for our layout.
        setContentView(view);


//        FloatingActionButton add_trip_button = findViewById(R.id.add_trip_button);
//        add_trip_button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(MainActivity.this, AddTripActivity.class);
//                startActivity(intent);
//            }
//        });

        // start default trip fragment
        replaceFragment(new TripFragment());

        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.tripFragment:
                    replaceFragment(new TripFragment());
                    break;
                case R.id.searchFragment:
                    replaceFragment(new SearchFragment());
                    break;
                case R.id.uploadFragment:
                    replaceFragment(new UploadFragment());
                    break;
            }

            return true;
        });
    }

    public void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();
    }
}