package com.example.coursework.Expressions;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.coursework.DatabaseHelper;
import com.example.coursework.databinding.ActivityAddExpressionBinding;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AddExpressionActivity extends AppCompatActivity {
    private Activity activity;
    private ActivityAddExpressionBinding binding;
    private boolean isValid = true;
    private final String[] typeExpression = {
            "Travel",
            "Food",
            "Transport"
    };
    private String tripId, typeText, amountText, timeText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddExpressionBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        // Set the title
        setTitle("Add Expressions");

        // Get reference spinner
        Spinner typeSpinner = binding.typeExpressionSpinner;
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, typeExpression);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        typeSpinner.setAdapter(dataAdapter);


        Bundle extras = getIntent().getExtras();
        if (extras == null) return;
        tripId = extras.getString("tripId"); // Get trip id from UpdateTrip activity

        if (tripId.isEmpty()) {
            Toast.makeText(this, "The trip id is empty", Toast.LENGTH_SHORT).show();
        }

        // Get input and display valid from form
        amountFocusListener();
        timeExpressionFocusListener();

        // Onclick add expression button
        binding.addExpressionItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addExpressions();  // add new expression
            }
        });
    }

    // Add new expression by trip id from Intent
    private void addExpressions() {
        if (!checkIsValid()) {
            Toast.makeText(this, "Please fulfill again!", Toast.LENGTH_SHORT).show();
        } else {
            DatabaseHelper db = new DatabaseHelper(getApplicationContext());
            typeText = binding.typeExpressionSpinner.getSelectedItem().toString();
            long result = db.addExpression(tripId, typeText, amountText, timeText);

            if (result == -1) {
                Toast.makeText(this, "Failed to create new expression", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Create Expression Successfully!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(AddExpressionActivity.this, ExpressionActivity.class);
                intent.putExtra("tripId", tripId);
                startActivityForResult(intent, 999);
            }
        }


    }


    private boolean checkIsValid() {
        amountText = binding.amountInput.getText().toString();

        if (amountText.isEmpty()) {
            isValid = false;
        }

        Pattern amountPattern = Pattern.compile("[1-9]"); // Input is only number and not start with 0
        Matcher matcher1 = amountPattern.matcher(amountText);
        boolean matchFound1 = matcher1.find(); // Check if match pattern

        if (!matchFound1) {
            isValid = false;
        }

        timeText = binding.timeExpressionInput.getText().toString();

        // https://www.w3schools.com/java/java_regex.asp regex document reference
        // Validate a format is dd/mm/yyyy
        Pattern pattern = Pattern.compile("^([0-2][0-9]|(3)[0-1])(\\/)(((0)[0-9])|((1)[0-2]))(\\/)\\d{4}$"); // https://www.regextester.com/99555
        Matcher matcher = pattern.matcher(timeText);
        boolean matchFound = matcher.find(); // Check if match pattern

        if (timeText.isEmpty()) {
            isValid = false;
        }

        if (!matchFound) {
            isValid = false;
        }

        return isValid;
    }

    private void amountFocusListener() {
        binding.amountInput.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    binding.amountTextContainer.setHelperText(validAmount());
                }
            }
        });
    }

    private String validAmount() {
        amountText = binding.amountInput.getText().toString();
        Pattern amountPattern = Pattern.compile("[1-9]"); // Input is only number and not start with 0
        Matcher matcher1 = amountPattern.matcher(amountText);
        boolean matchFound1 = matcher1.find(); // Check if match pattern

        if (amountText.isEmpty()) {
            return "Required";
        } else if (!matchFound1) {
            return "Amount is invalid, not start with 0";
        } else {
            return "";
        }
    }

    private void timeExpressionFocusListener() {
        binding.timeExpressionInput.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    binding.timeExpressionTextContainer.setHelperText(validTimeExpression());
                }
            }
        });
    }

    private String validTimeExpression() {
        timeText = binding.timeExpressionInput.getText().toString();

        // https://www.w3schools.com/java/java_regex.asp regex document reference
        // Validate a format is dd/mm/yyyy
        Pattern pattern = Pattern.compile("^([0-2][0-9]|(3)[0-1])(\\/)(((0)[0-9])|((1)[0-2]))(\\/)\\d{4}$"); // https://www.regextester.com/99555
        Matcher matcher = pattern.matcher(timeText);
        boolean matchFound = matcher.find(); // Check if match pattern

        if (timeText.isEmpty()) {
            return "Required*";
        } else if (!matchFound) {
            return "Invalid format";
        } else {
            return "";
        }
    }
}