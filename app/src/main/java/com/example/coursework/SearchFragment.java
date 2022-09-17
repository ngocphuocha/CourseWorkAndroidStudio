package com.example.coursework;

import android.database.Cursor;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.coursework.databinding.FragmentSearchBinding;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SearchFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchFragment extends Fragment {
    private FragmentSearchBinding binding;
    ImageView emptyImageView;
    TextView noTripDataTxt;
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

    public SearchFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SearchFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SearchFragment newInstance(String param1, String param2) {
        SearchFragment fragment = new SearchFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSearchBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Search Trips");
        // Get reference recycler view
        RecyclerView recyclerView = binding.tripRecyclerView2;

        emptyImageView = binding.emptyTripImageView2;
        noTripDataTxt = binding.noTripDataTxt2;

        // Initial object
        db = new DatabaseHelper(getContext());
        idArray = new ArrayList<>();
        nameArray = new ArrayList<>();
        destinationArray = new ArrayList<>();
        dateOfTripArray = new ArrayList<>();
        requireAssessmentArray = new ArrayList<>();
        descriptionArray = new ArrayList<>();


        customTripAdapter = new CustomTripAdapter(getContext(), idArray, nameArray, destinationArray, dateOfTripArray, requireAssessmentArray, descriptionArray);
        recyclerView.setAdapter(customTripAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        binding.tripSearchInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                searchTrips(binding.tripSearchInput.getText().toString());
//                customTripAdapter.notifyItemChanged(idArray.size());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }

        });

    }

    private void searchTrips(String query) {
        idArray.clear();
        nameArray.clear();
        descriptionArray.clear();
        dateOfTripArray.clear();
        requireAssessmentArray.clear();
        descriptionArray.clear();
        Cursor cursor = db.searchTrip(query);

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