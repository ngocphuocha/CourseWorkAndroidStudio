package com.example.coursework;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.coursework.Trips.AddTripActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TripFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TripFragment extends Fragment {
    RecyclerView recyclerView;
    ImageView emptyImageView;
    TextView noTripDataTxt;
    FloatingActionButton add_trip_button;

    DatabaseHelper db;
    ArrayList<String> idArray, nameArray, destinationArray, dateOfTripArray, requireAssessmentArray, descriptionArray;
    CustomTripAdapter customTripAdapter;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public TripFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TripFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TripFragment newInstance(String param1, String param2) {
        TripFragment fragment = new TripFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_trip, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Get reference recycler view
        recyclerView = getView().findViewById(R.id.tripRecyclerView);

        emptyImageView = getView().findViewById(R.id.emptyTripImageView);
        noTripDataTxt = getView().findViewById(R.id.noTripDataTxt);

        add_trip_button = getView().findViewById(R.id.add_trip_button);
        add_trip_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), AddTripActivity.class);
                startActivity(intent);
            }
        });

        // Set title of bar
        getActivity().setTitle("My Trips");

        // Initial object
        db = new DatabaseHelper(getContext());
        idArray = new ArrayList<>();
        nameArray = new ArrayList<>();
        destinationArray = new ArrayList<>();
        dateOfTripArray = new ArrayList<>();
        requireAssessmentArray = new ArrayList<>();
        descriptionArray = new ArrayList<>();

        // Store data in array first for pass to the custom trip adapter
        storeTripsDataInArrays();

        customTripAdapter = new CustomTripAdapter(getContext(), idArray, nameArray, destinationArray, dateOfTripArray, requireAssessmentArray, descriptionArray);
        recyclerView.setAdapter(customTripAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    void storeTripsDataInArrays() {
        Cursor cursor = db.getAllTrips();

        if (cursor.getCount() == 0) {
            emptyImageView.setVisibility(View.VISIBLE);
            noTripDataTxt.setVisibility(View.VISIBLE);
            Toast.makeText(getContext(), "No data.", Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()) {
                idArray.add(cursor.getString(0));
                nameArray.add(cursor.getString(1));
                destinationArray.add(cursor.getString(2));
                dateOfTripArray.add(cursor.getString(3));
                requireAssessmentArray.add(cursor.getString(4));
                descriptionArray.add(cursor.getString(5));
            }
            emptyImageView.setVisibility(View.GONE);
            noTripDataTxt.setVisibility(View.GONE);
        }
    }
}