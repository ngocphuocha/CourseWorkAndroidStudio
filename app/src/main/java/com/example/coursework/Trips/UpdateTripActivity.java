package com.example.coursework.Trips;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.coursework.DatabaseHelper;
import com.example.coursework.MainActivity;
import com.example.coursework.R;
import com.example.coursework.databinding.ActivityUpdateTripBinding;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UpdateTripActivity extends AppCompatActivity {
    ActivityUpdateTripBinding binding;
    RadioButton require_input_radio;
    String id, nameText, destinationText, dateOfTripText, requireAssessmentText, descriptionText;
    boolean isValid = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUpdateTripBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Check valid on focus input text
        nameFocusListener();
        destinationFocusListener();
        dateOfTripFocusListener();
        descriptionFocusListener();

        // First we call this
        getAndSetIntentData();
        // Set actionbar after getAndSetIntentData method
        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setTitle(nameText);
        }

        // Get require assessment input radio text
        binding.requireAssessmentGroupUpdate.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                require_input_radio = binding.getRoot().findViewById(binding.requireAssessmentGroupUpdate.getCheckedRadioButtonId());
                requireAssessmentText = require_input_radio.getText().toString();
            }
        });


        // On click update button
        binding.updateTripButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitForm();
            }
        });
    }

    private void submitForm() {
        if (!checkValid()) {
            Toast.makeText(this, "Please fulfilled again!", Toast.LENGTH_SHORT).show();
        } else {
            DatabaseHelper db = new DatabaseHelper(UpdateTripActivity.this);
            db.updateTrip(id, nameText, destinationText, dateOfTripText, requireAssessmentText, descriptionText);
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivityForResult(intent, 1);
        }
    }

    private void getAndSetIntentData() {
        if (getIntent().hasExtra("trip_id")
                && getIntent().hasExtra("trip_name")
                && getIntent().hasExtra("trip_destination")
                && getIntent().hasExtra("trip_date")
                && getIntent().hasExtra("trip_requireAssessment")
                && getIntent().hasExtra("trip_description")) {
            // Getting data from intent and setting data for edit text
            id = getIntent().getStringExtra("trip_id");
            binding.nameEditTextUpdate.setText(getIntent().getStringExtra("trip_name"));
            binding.destinationEditTextUpdate.setText(getIntent().getStringExtra("trip_destination"));
            binding.dateOfTripEditTextUpdate.setText(getIntent().getStringExtra("trip_date"));
            binding.descriptionEditTextUpdate.setText(getIntent().getStringExtra("trip_description"));

            Bundle extras = getIntent().getExtras();

            // Set textValue
            nameText = extras.getString("trip_name");
            destinationText = extras.getString("trip_destination");
            dateOfTripText = extras.getString("trip_date");
            descriptionText = extras.getString("trip_description");

            // get and set radio
            requireAssessmentText = extras.getString("trip_requireAssessment");

            if (requireAssessmentText.equals("Yes")) {
                binding.requireAssessmentGroupUpdate.check(R.id.radio_yes_button_update);
            } else {
                binding.requireAssessmentGroupUpdate.check(R.id.radio_no_button_update);
            }

            require_input_radio = binding.getRoot().findViewById(binding.requireAssessmentGroupUpdate.getCheckedRadioButtonId());
            requireAssessmentText = require_input_radio.getText().toString();
        } else {
            Toast.makeText(this, "No data intent.", Toast.LENGTH_SHORT).show();
        }
    }


    private void nameFocusListener() {
        binding.nameEditTextUpdate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    binding.nameContainerUpdate.setHelperText(validName());
                }
            }
        });
    }

    private String validName() {
        nameText = binding.nameEditTextUpdate.getText().toString();

        if (nameText.isEmpty()) {
            return "Required*";
        }
        return "";
    }

    private void destinationFocusListener() {
        binding.destinationEditTextUpdate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    binding.destinationContainerUpdate.setHelperText(validDestination());
                }
            }
        });
    }

    private String validDestination() {
        destinationText = binding.destinationEditTextUpdate.getText().toString();

        if (destinationText.isEmpty()) {
            return "Required*";
        }

        return "";
    }

    private void dateOfTripFocusListener() {
        binding.dateOfTripEditTextUpdate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    binding.dateOfTripContainerUpdate.setHelperText(validDateOfTrip());
                }
            }
        });
    }

    private String validDateOfTrip() {
        dateOfTripText = binding.dateOfTripEditTextUpdate.getText().toString();

        // https://www.w3schools.com/java/java_regex.asp regex document reference
        // Validate a format is dd/mm/yyyy
        Pattern pattern = Pattern.compile("^([0-2][0-9]|(3)[0-1])(\\/)(((0)[0-9])|((1)[0-2]))(\\/)\\d{4}$"); // https://www.regextester.com/99555
        Matcher matcher = pattern.matcher(dateOfTripText);
        boolean matchFound = matcher.find(); // Check if match pattern

        if (dateOfTripText.isEmpty()) {
            return "Required*";
        } else if (!matchFound) {
            return "Invalid format";
        } else {
            return "";
        }
    }

    private void descriptionFocusListener() {
        binding.descriptionEditTextUpdate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    binding.descriptionContainerUpdate.setHelperText(validDescription());
                }
            }
        });
    }

    private String validDescription() {
        descriptionText = binding.descriptionEditTextUpdate.getText().toString();

        if (descriptionText.isEmpty()) {
            return "Required*";
        }

        return "";
    }

    public boolean checkValid() {
        nameText = binding.nameEditTextUpdate.getText().toString();
        if (nameText.isEmpty()) {
            isValid = false;
        }

        destinationText = binding.destinationEditTextUpdate.getText().toString();
        if (destinationText.isEmpty()) {
            isValid = false;
        }

        dateOfTripText = binding.dateOfTripEditTextUpdate.getText().toString();
        Pattern pattern = Pattern.compile("^([0-2][0-9]|(3)[0-1])(\\/)(((0)[0-9])|((1)[0-2]))(\\/)\\d{4}$"); // https://www.regextester.com/99555
        Matcher matcher = pattern.matcher(dateOfTripText);
        boolean matchFound = matcher.find(); // Check if match pattern
        if (dateOfTripText.isEmpty()) {
            isValid = false;
        }
        if (!matchFound) {

            isValid = true;
        }

        descriptionText = binding.descriptionEditTextUpdate.getText().toString();
        if (descriptionText.isEmpty()) {
            isValid = false;
        }

        return isValid;
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
                startActivityForResult(intent, 1);
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