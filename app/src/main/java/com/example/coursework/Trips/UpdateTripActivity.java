package com.example.coursework.Trips;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.coursework.DatabaseHelper;
import com.example.coursework.Expressions.ExpressionActivity;
import com.example.coursework.MainActivity;
import com.example.coursework.R;
import com.example.coursework.databinding.ActivityUpdateTripBinding;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UpdateTripActivity extends AppCompatActivity {
    private ActivityUpdateTripBinding binding;

    private RadioButton requireInputRadio;

    private String id;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUpdateTripBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Set reference
        nameInputLayout = binding.nameInputLayout2;
        destinationInputLayout = binding.destinationInputLayout2;
        dateInputLayout = binding.dateInputLayout2;
        descriptionInputLayout = binding.descriptionInputLayout2;

        nameInput = binding.nameInput2;
        destinationInput = binding.destinationInput2;
        dateInput = binding.dateInput2;
        descriptionInput = binding.descriptionInput2;

        requireAssessmentGroup = binding.requireAssessmentGroup2;

        // Check valid on focus input text first
        nameFocusListener();
        destinationFocusListener();
        dateOfTripFocusListener();
        descriptionFocusListener();

        // Get the intent data and set the input
        getAndSetIntentData();

        // Set actionbar after getAndSetIntentData method
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(nameText);
        }

        // Get require assessment input radio text
        requireAssessmentGroup.setOnCheckedChangeListener((group, checkedId) -> {
            requireInputRadio = binding.getRoot().findViewById(
                    requireAssessmentGroup.getCheckedRadioButtonId()
            );
            requireAssessmentText = requireInputRadio.getText().toString();
        });

        // On click update button and update trip data
        binding.updateTripButton.setOnClickListener(v -> submitForm());

        // Go to expression activity
        binding.expressionButtonActivity.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), ExpressionActivity.class);
            intent.putExtra("tripId", id);
            startActivity(intent);
        });
    }

    private void submitForm() {
        DatabaseHelper db = new DatabaseHelper(UpdateTripActivity.this);
        db.updateTrip(
                id,
                nameText,
                destinationText,
                dateOfTripText,
                requireAssessmentText,
                descriptionText
        );
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }

    private void getAndSetIntentData() {
        Bundle extras = getIntent().getExtras();

        if (extras == null) {
            Toast.makeText(this, "No intent data.", Toast.LENGTH_SHORT).show();
            return;
        }
        // Getting data from intent and setting data for edit text
        nameInput.setText(extras.getString("tripName"));
        destinationInput.setText(extras.getString("tripDestination"));
        dateInput.setText(extras.getString("tripDate"));
        descriptionInput.setText(extras.getString("tripDescription"));

        // Set to input value
        id = extras.getString("tripId");
        nameText = extras.getString("tripName");
        destinationText = extras.getString("tripDestination");
        dateOfTripText = extras.getString("tripDate");
        descriptionText = extras.getString("tripDescription");

        // get and set radio
        requireAssessmentText = extras.getString("tripRequireAssessment");

        if (requireAssessmentText.equals("Yes")) {
            binding.requireAssessmentGroup2.check(R.id.radio_yes_button2);
        } else {
            binding.requireAssessmentGroup2.check(R.id.radio_no_button2);
        }

        requireInputRadio = binding.getRoot().findViewById(
                binding.requireAssessmentGroup2.getCheckedRadioButtonId()
        );

        requireAssessmentText = requireInputRadio.getText().toString();
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
        binding.updateTripButton.setEnabled(isValid);
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


    // Delete a trip
    private void confirmDeleteDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete " + nameText + " ?");
        builder.setMessage("Are you sure you want to delete " + nameText + " ?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                DatabaseHelper db = new DatabaseHelper(UpdateTripActivity.this);
                db.deleteTripRow(id);
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.create().show();
    }

    // Menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.update_trip_activity_top_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.delete_trip:
                confirmDeleteDialog();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}