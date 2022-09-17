package com.example.coursework;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.jar.Attributes;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "travelManager.db";
    private static final int DATABASE_VERSION = 1;
    private static final String ID_COLUMN = "Id";

    //Trips table columns
    private static final String TRIPS_TABLE = "Trips";
    private static final String NAME_COLUMN = "Name";
    private static final String DESTINATION_COLUMN = "Destination";
    private static final String DATE_OF_TRIP_COLUMN = "DateOfTrip";
    private static final String REQUIRE_ASSESSMENT_COLUMN = "RequireAssessment";
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
                    "%s TEXT NOT NULL," +
                    "%s TEXT NOT NULL);",
            TRIPS_TABLE, ID_COLUMN, NAME_COLUMN, DESTINATION_COLUMN, DATE_OF_TRIP_COLUMN, REQUIRE_ASSESSMENT_COLUMN, DESCRIPTION_COLUMN
    );
    //Expenses table create string
    private static final String CREATE_EXPENSES_TABLE = String.format(
            "CREATE TABLE %s (" +
                    "%s INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "%s INTEGER NOT NULL," +
//                    "FOREIGN KEY (TripId) REFERENCES Trips (id)," +
                    "%s TEXT NOT NULL," +
                    "%s TEXT NOT NULL," +
                    "%s TEXT NOT NULL);",
            EXPENSES_TABLE, ID_COLUMN, TRIP_ID_COLUMN, TYPE_COLUMN, AMOUNT_COLUMN, TIME_OF_EXPENSE
    );

    private final Context context;

    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
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

    public long addTrip(String name, String destination, String dateOfTrip, String requireRisk, String description) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        // Insert row value
        cv.put(NAME_COLUMN, name);
        cv.put(DESTINATION_COLUMN, destination);
        cv.put(DATE_OF_TRIP_COLUMN, dateOfTrip);
        cv.put(REQUIRE_ASSESSMENT_COLUMN, requireRisk);
        cv.put(DESCRIPTION_COLUMN, description);

        return db.insertOrThrow(TRIPS_TABLE, null, cv);
    }

    Cursor getAllTrips() {
        String query = "SELECT * FROM " + TRIPS_TABLE;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;

        if (db != null) {
            cursor = db.rawQuery(query, null);
        }

        return cursor;
    }

    public void updateTrip(String id, String name, String destination, String date, String requireAssessment, String description) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        //Insert row value
        cv.put(NAME_COLUMN, name);
        cv.put(DESTINATION_COLUMN, destination);
        cv.put(DATE_OF_TRIP_COLUMN, date);
        cv.put(REQUIRE_ASSESSMENT_COLUMN, requireAssessment);
        cv.put(DESCRIPTION_COLUMN, description);

        long result = db.update(TRIPS_TABLE, cv, "Id=?", new String[]{id});

        if (result == -1) {
            Toast.makeText(context, "Failed to update.", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Successfully updated! ", Toast.LENGTH_SHORT).show();
        }
    }

    public void deleteTripRow(String id) {
        SQLiteDatabase db = this.getWritableDatabase();

        // Delete all the expenses belong to the trip
        db.delete(EXPENSES_TABLE, "TripId=?", new String[]{id});
        long result = db.delete(TRIPS_TABLE, "Id=?", new String[]{id});

        if (result == -1) {
            Toast.makeText(context, "Failed to delete", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Delete successfully.", Toast.LENGTH_SHORT).show();
        }
    }

    public void deleteAllTrip() {
        SQLiteDatabase db = this.getWritableDatabase();
        // Delete all records from expenses table first
        db.execSQL("DELETE FROM " + EXPENSES_TABLE);
        // Then delete trips table records
        db.execSQL("DELETE FROM " + TRIPS_TABLE);
    }

    public Cursor searchTrip(String queryString) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        cursor = db.rawQuery("SELECT * FROM Trips WHERE Name LIKE '%" + queryString + "%'", null);
        return cursor;
    }


    // Expression
    public long addExpression(String tripId, String type, String amount, String time) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(TRIP_ID_COLUMN, tripId);
        cv.put(TYPE_COLUMN, type);
        cv.put(AMOUNT_COLUMN, amount);
        cv.put(TIME_OF_EXPENSE, time);

        return db.insert(EXPENSES_TABLE, null, cv);
    }
}
