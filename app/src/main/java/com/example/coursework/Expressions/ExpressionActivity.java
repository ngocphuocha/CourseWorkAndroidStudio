package com.example.coursework.Expressions;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.coursework.CustomExpressionAdapter;
import com.example.coursework.CustomTripAdapter;
import com.example.coursework.DatabaseHelper;
import com.example.coursework.R;
import com.example.coursework.databinding.ActivityExpressionBinding;

import java.util.ArrayList;

public class ExpressionActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    ImageView emptyImageView;
    TextView noTripDataTxt;
    private DatabaseHelper db;
    private ArrayList<String> id, type, amount, time;
    CustomExpressionAdapter customExpressionAdapter;
    private ActivityExpressionBinding binding;
    private String tripId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityExpressionBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        // Set activity's title
        setTitle("Expression Detail");

        Bundle extras = getIntent().getExtras();
        if (extras == null) return;
        tripId = extras.getString("tripId"); // Get trip id from UpdateTrip activity

        // Initial object
        // Get reference recycler view
        recyclerView = findViewById(R.id.expression_recyclerView);

        emptyImageView = findViewById(R.id.emptyExpressionImageView);
        noTripDataTxt = findViewById(R.id.noExpressionDataTxt);


        db = new DatabaseHelper(getApplicationContext());
        id = new ArrayList<>();
        type = new ArrayList<>();
        amount = new ArrayList<>();
        time = new ArrayList<>();

        // Store data in array first for pass to the adapter
        storeDataInArrays();
        customExpressionAdapter = new CustomExpressionAdapter(getApplicationContext(), id, type, amount, time);
        recyclerView.setAdapter(customExpressionAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        binding.addExpressionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AddExpressionActivity.class);
                intent.putExtra("tripId", tripId);
                startActivity(intent);
            }
        });

    }

    // Store all expression in array
    private void storeDataInArrays() {
        Cursor cursor = db.getAllExpressions(tripId);

        if (cursor.getCount() == 0) {
            emptyImageView.setVisibility(View.VISIBLE);
            noTripDataTxt.setVisibility(View.VISIBLE);
            Toast.makeText(getApplicationContext(), "No data.", Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()) {
                id.add(cursor.getString(0));
                type.add(cursor.getString(2));
                amount.add(cursor.getString(3));
                time.add(cursor.getString(4));
            }
            emptyImageView.setVisibility(View.GONE);
            noTripDataTxt.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 999) { // if request code = 999
            if (getIntent().hasExtra("tripId")) {
                // get and set intent data
                tripId = getIntent().getStringExtra("tripId");
                recreate();
            }
        }
    }
}