package com.example.coursework.Trips;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.coursework.DatabaseHelper;
import com.example.coursework.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AddTripActivity extends AppCompatActivity {

    EditText name_input, destination_input, date_of_trip_input, description_input;
    String name_text, destination_txt, date_of_trip_txt, require_assessment_txt, description_txt;
    RadioGroup require_assessment_group;
    RadioButton require_input_radio;
    Button save_trip_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_trip);

        // Get require assessment input radio text
        require_assessment_group = findViewById(R.id.require_assessment_group);
        require_input_radio = (RadioButton) findViewById(require_assessment_group.getCheckedRadioButtonId());  // Get default radio button input

        // Get new radio button input if user change input
        require_assessment_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                require_input_radio = (RadioButton) findViewById(require_assessment_group.getCheckedRadioButtonId());
            }
        });

        // Reference save trip button
        save_trip_button = findViewById(R.id.save_trip_button);
        save_trip_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Get input first
                getInputs();

                // Validate the input from user
                boolean isValid = checkValidateInputs();

                // Add a new trip if input is valid
                if (!isValid) {
                    Toast.makeText(AddTripActivity.this, "Please fill full the input again.", Toast.LENGTH_SHORT).show();
                } else {
                    addNewTrip();
                }
            }
        });
    }

    // Get input from user
    private void getInputs() {
        // Get name input text
        name_input = findViewById(R.id.name_input);
        name_text = name_input.getText().toString().trim();

        // Get destination input text
        destination_input = findViewById(R.id.destination_input);
        destination_txt = destination_input.getText().toString().trim();

        // Get date of the trim input text
        date_of_trip_input = findViewById(R.id.date_of_trip_input);
        date_of_trip_txt = date_of_trip_input.getText().toString().trim();

        // Get require assessment text value
        require_assessment_txt = require_input_radio.getText().toString();

        // Get destination input text
        description_input = findViewById(R.id.description_input);
        description_txt = description_input.getText().toString().trim();
    }

    private void addNewTrip() {
        DatabaseHelper db = new DatabaseHelper(AddTripActivity.this);
        long result = db.addTrip(name_text, destination_txt, date_of_trip_txt, require_assessment_txt, description_txt);

        if (result == -1) {
            Toast.makeText(this, "Failed to create new trip", Toast.LENGTH_SHORT).show();
        } else {
            displayResult();
        }
    }

    private void displayResult() {
        new AlertDialog.Builder(this).setTitle("Add new trip successfully.")
                .setMessage("Name: " + name_text + "\n" +
                        "Destination: " + description_txt + "\n" +
                        "Date of the trip: " + date_of_trip_txt + "\n" +
                        "Risk assessment: " + require_assessment_txt + "\n" +
                        "Description: " + description_txt
                )
                .setNeutralButton("Close", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO: should clear input
                    }
                })
                .show();
    }

    private boolean checkValidateInputs() {
        if (name_text.isEmpty() || destination_txt.isEmpty() || date_of_trip_txt.isEmpty() || require_assessment_txt.isEmpty() || description_txt.isEmpty()) {
            return false;
        }

        Pattern pattern = Pattern.compile("^([0-2][0-9]|(3)[0-1])(\\/)(((0)[0-9])|((1)[0-2]))(\\/)\\d{4}$");
        Matcher matcher = pattern.matcher(date_of_trip_txt);
        boolean matchFound = matcher.find();

        if (!matchFound) {
            Toast.makeText(this, "Date of the trip input is invalid!", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }
}