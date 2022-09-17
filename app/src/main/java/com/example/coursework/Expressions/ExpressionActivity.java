package com.example.coursework.Expressions;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.coursework.DatabaseHelper;
import com.example.coursework.R;
import com.example.coursework.databinding.ActivityExpressionBinding;

public class ExpressionActivity extends AppCompatActivity {

    private ActivityExpressionBinding binding;
    private DatabaseHelper db;
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

        binding.addExpressionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ExpressionActivity.this, "OK", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), AddExpressionActivity.class);
                intent.putExtra("tripId", tripId);
                startActivity(intent);
            }
        });
    }
}