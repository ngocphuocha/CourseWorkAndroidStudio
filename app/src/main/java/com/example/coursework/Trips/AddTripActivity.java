package com.example.coursework.Trips;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.coursework.DatabaseHelper;
import com.example.coursework.MainActivity;
import com.example.coursework.databinding.ActivityAddTripBinding;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AddTripActivity extends AppCompatActivity {
    ActivityAddTripBinding binding;

    private Button saveTripButton;

    private String nameText;
    private String destinationText;
    private String dateOfTripText;
    private String requireAssessmentText;
    private String descriptionText;

    private TextInputLayout nameInputLayout;
    private TextInputLayout destinationInputLayout;
    private TextInputLayout dateInputLayout;
    private TextInputLayout descriptionInputLayout;

    private TextInputEditText nameInput;
    private TextInputEditText destinationInput;
    private TextInputEditText dateInput;
    private TextInputEditText descriptionInput;

    private RadioGroup requireAssessmentGroup;

    private RadioButton requireInputRadio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddTripBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        // Get reference Text input layout
        nameInputLayout = binding.nameInputLayout;
        destinationInputLayout = binding.destinationInputLayout;
        dateInputLayout = binding.dateInputLayout;
        descriptionInputLayout = binding.descriptionInputLayout;

        nameInput = binding.nameInput;
        destinationInput = binding.destinationInput;
        dateInput = binding.dateInput;
        descriptionInput = binding.descriptionInput;

        // Reference save trip button
        saveTripButton = binding.saveTripButton;

        // Check valid on focus input text first
        nameFocusListener();
        destinationFocusListener();
        dateOfTripFocusListener();
        descriptionFocusListener();

        // Get default radio button input
        requireAssessmentGroup = binding.requireAssessmentGroup;
        requireInputRadio = binding.getRoot().findViewById(
                requireAssessmentGroup.getCheckedRadioButtonId()
        );

        requireAssessmentText = requireInputRadio.getText().toString();

        // Get new radio button input if user change input
        requireAssessmentGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                requireInputRadio = binding.getRoot().findViewById(
                        requireAssessmentGroup.getCheckedRadioButtonId()
                );
                requireAssessmentText = requireInputRadio.getText().toString();
            }
        });

        saveTripButton.setEnabled(false); // false by default
        saveTripButton.setOnClickListener(v -> {
            // Add a new trip if input is valid
            addNewTrip();
        });
    }

    private void addNewTrip() {
        DatabaseHelper db = new DatabaseHelper(AddTripActivity.this);
        long result = db.addTrip(nameText, destinationText, dateOfTripText, requireAssessmentText, descriptionText);

        if (result == -1) {
            Toast.makeText(this, "Failed to create new trip", Toast.LENGTH_SHORT).show();
        } else {
            displayResult();
        }
    }

    private void displayResult() {
        new AlertDialog.Builder(this).setTitle("Add new trip successfully.")
                .setMessage("Name: " + nameText + "\n" +
                        "Destination: " + destinationText + "\n" +
                        "Date of the trip: " + dateOfTripText + "\n" +
                        "Risk assessment: " + requireAssessmentText + "\n" +
                        "Description: " + descriptionText
                )
                .setNeutralButton("Close", (dialog, which) -> {
                    Intent intent = new Intent(
                            AddTripActivity.this,
                            MainActivity.class
                    );
                    startActivity(intent);
                })
                .show();
    }

    public void checkValid() {
        // if the helper not empty then set isValid to false
        // flag for validate the input
        boolean isValid = true;

        if (nameInputLayout.getHelperText() != null) {
            isValid = false;
        }

        if (destinationInputLayout.getHelperText() != null) {
            isValid = false;
        }

        if (dateInputLayout.getHelperText() != null) {
            isValid = false;
        }

        if (descriptionInputLayout.getHelperText() != null) {
            isValid = false;
        }

        //Set status enable base on isValid status
        saveTripButton.setEnabled(isValid);
    }

    private void nameFocusListener() {
        nameInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                nameInputLayout.setHelperText(validName());
                checkValid();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private String validName() {
        nameText = nameInput.getText().toString().trim();

        if (nameText.isEmpty()) {
            return "Required*";
        }

        return null;
    }

    private void destinationFocusListener() {
        destinationInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                destinationInputLayout.setHelperText(validDestination());
                checkValid();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private String validDestination() {
        destinationText = destinationInput.getText().toString().trim();

        if (destinationText.isEmpty()) {
            return "Required*";
        }

        return null;
    }

    private void dateOfTripFocusListener() {
        dateInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                dateInputLayout.setHelperText(validDateOfTrip());
                checkValid();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private String validDateOfTrip() {
        dateOfTripText = dateInput.getText().toString().trim();

        // https://www.w3schools.com/java/java_regex.asp regex document reference
        // Validate a format is dd/mm/yyyy
        Pattern pattern = Pattern.compile("^([0-2][0-9]|(3)[0-1])(\\/)(((0)[0-9])|((1)[0-2]))(\\/)\\d{4}$"); // https://www.regextester.com/99555
        Matcher matcher = pattern.matcher(dateOfTripText);
        boolean matchFound = matcher.find(); // Check if match pattern

        if (dateOfTripText.isEmpty()) {
            return "Required*";
        }

        if (!matchFound) {
            return "Invalid format";
        }

        return null;
    }

    private void descriptionFocusListener() {
        descriptionInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                descriptionInputLayout.setHelperText(validDescription());
                checkValid();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private String validDescription() {
        descriptionText = descriptionInput.getText().toString().trim();

        if (descriptionText.isEmpty()) {
            return "Required*";
        }

        return null;
    }
}