package com.example.coursework;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "travelManager.db";
    private static final int DATABASE_VERSION = 1;
    private static final String ID_COLUMN = "Id";

    //Trips table columns
    private static final String TRIPS_TABLE = "Trips";
    private static final String NAME_COLUMN = "Name";
    private static final String DESTINATION_COLUMN = "Destination";
    private static final String DATE_OF_TRIP_COLUMN = "DateOfTrip";
    private static final String REQUIRE_ASSESSMENT = "RequireAssessment";
    private static final String DESCRIPTION_COLUMN = "Description";

    //Expenses table columns
    private static final String EXPENSES_TABLE = "Expenses";
    private static final String TRIP_ID_COLUMN = "TripId";
    private static final String TYPE_COLUMN = "Type";
    private static final String AMOUNT_COLUMN = "Amount";
    private static final String TIME_OF_EXPENSE = "TIME_OF_EXPENSE";

    //Trips table create string
    private static final String CREATE_TRIPS_TABLE = String.format(
            "CREATE TABLE %s (" +
                    "%s INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "%s TEXT NOT NULL," +
                    "%s TEXT NOT NULL," +
                    "%s TEXT NOT NULL," +
                    "%s BOOLEAN NOT NULL," +
                    "%s TEXT NOT NULL);",
            TRIPS_TABLE, ID_COLUMN, NAME_COLUMN, DESTINATION_COLUMN, DATE_OF_TRIP_COLUMN, REQUIRE_ASSESSMENT, DESCRIPTION_COLUMN
    );

    //Expenses table create string
    private static final String CREATE_EXPENSES_TABLE = String.format(
            "CREATE TABLE %s (" +
                    "%s INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "%s INTEGER NOT NULL," +
                    "FOREIGN KEY (trip_id) REFERENCES trips (id)," +
                    "%s TEXT, NOT NULL" +
                    "%s TEXT, NOT NULL" +
                    "%s TEXT NOT NULL);",
            EXPENSES_TABLE, ID_COLUMN, TRIP_ID_COLUMN, TYPE_COLUMN, AMOUNT_COLUMN, TIME_OF_EXPENSE
    );


    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create the trips table first
        db.execSQL(CREATE_TRIPS_TABLE);
        // Then create the expenses table
        db.execSQL(CREATE_EXPENSES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Remove expenses table first
        db.execSQL("DROP TABLE IF EXISTS " + EXPENSES_TABLE);
        // Then remove trips tables
        db.execSQL("DROP TABLE IF EXISTS " + TRIPS_TABLE);

        Log.v(this.getClass().getName(), DATABASE_NAME +
                "database upgrade to version" + newVersion + " - old data lost"
        );
        onCreate(db);
    }
}
