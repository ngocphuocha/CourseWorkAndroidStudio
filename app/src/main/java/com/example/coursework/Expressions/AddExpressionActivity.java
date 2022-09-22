package com.example.coursework.Expressions;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.coursework.DatabaseHelper;
import com.example.coursework.databinding.ActivityAddExpressionBinding;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AddExpressionActivity extends AppCompatActivity {
    private final String[] typeExpression = {
            "Travel",
            "Food",
            "Transport"
    };
    private ActivityAddExpressionBinding binding;
    private String tripId;
    private String amountText;
    private String timeText;

    private TextInputLayout amountTextLayout;
    private TextInputLayout timeTextLayout;

    private TextInputEditText amountInput;
    private TextInputEditText timeInput;

    private Button addButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddExpressionBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Set the title
        setTitle("Add Expressions");

        // Get reference text layout and input edit text
        amountTextLayout = binding.amountTextLayout;
        timeTextLayout = binding.timeTextLayout;
        amountInput = binding.amountInput;
        timeInput = binding.timeInput;


        // Get reference spinner
        Spinner typeSpinner = binding.typeExpressionSpinner;
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                typeExpression
        );
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        typeSpinner.setAdapter(dataAdapter);


        // Get the extras intent
        Bundle extras = getIntent().getExtras();

        if (extras == null) return;
        tripId = extras.getString("tripId"); // Get trip id from UpdateTrip activity

        if (tripId.isEmpty()) {
            Toast.makeText(this, "The trip id is empty",
                    Toast.LENGTH_SHORT
            ).show();
        }

        // Get input and display valid from form
        amountFocusListener();
        timeExpressionFocusListener();

        // Onclick add expression button
        addButton = binding.addExpressionItem;
        addButton.setEnabled(false); // set false by default
        addButton.setOnClickListener(v -> {
            addExpressions();  // add new expression
        });
    }

    // Add new expression by trip id from Intent
    private void addExpressions() {
        DatabaseHelper db = new DatabaseHelper(getApplicationContext());
        String typeText = binding.typeExpressionSpinner.getSelectedItem().toString();
        long result = db.addExpression(tripId, typeText, amountText, timeText);

        if (result == -1) {
            Toast.makeText(this,
                    "Failed to create new expression",
                    Toast.LENGTH_SHORT
            ).show();
        } else {
            Toast.makeText(this,
                    "Create Expression Successfully!",
                    Toast.LENGTH_SHORT
            ).show();

            Intent intent = new Intent(
                    AddExpressionActivity.this,
                    ExpressionActivity.class
            );

            intent.putExtra("tripId", tripId);
            startActivity(intent);
        }
    }

    private void checkIsValid() {
        boolean isValid = true;

        if (amountTextLayout.getHelperText() != null) {
            isValid = false;
        }

        if (timeTextLayout.getHelperText() != null) {
            isValid = false;
        }

        addButton.setEnabled(isValid);
    }

    private void amountFocusListener() {
        amountInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                amountTextLayout.setHelperText(validAmount());
                checkIsValid();
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    private String validAmount() {
        amountText = binding.amountInput.getText().toString().trim();

        // Input is only number and not start with 0
        Pattern amountPattern = Pattern.compile("[1-9]");
        Matcher matcher = amountPattern.matcher(amountText);
        boolean matchFound = matcher.find(); // Check if match pattern

        if (amountText.isEmpty()) {
            return "Required";
        }

        if (!matchFound) {
            return "Amount is invalid, not start with 0";
        }

        return null;

    }

    private void timeExpressionFocusListener() {
        timeInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                timeTextLayout.setHelperText(validTimeExpression());
                checkIsValid();
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

    }

    private String validTimeExpression() {
        timeText = timeInput.getText().toString().trim();

        // https://www.w3schools.com/java/java_regex.asp regex document reference
        // Validate a format is dd/mm/yyyy
        String regex = "^([0-2][0-9]|(3)[0-1])(\\/)(((0)[0-9])|((1)[0-2]))(\\/)\\d{4}$";
        // https://www.regextester.com/99555
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(timeText);
        boolean matchFound = matcher.find(); // Check if match pattern

        if (timeText.isEmpty()) {
            return "Required";
        }

        if (!matchFound) {
            return "Invalid format";
        }
        return null;
    }
}