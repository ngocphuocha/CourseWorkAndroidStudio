package com.example.coursework;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coursework.Trips.UpdateTripActivity;


import java.util.ArrayList;

public class CustomTripAdapter extends RecyclerView.Adapter<CustomTripAdapter.MyViewHolder> {
    Context context;
    ArrayList<String> tripId;
    ArrayList<String> tripName;
    ArrayList<String> tripDestination;
    ArrayList<String> tripDateOfTrip;
    ArrayList<String> tripRequireAssessment;
    ArrayList<String> tripDescription;

    Animation translateAnimation;

    //Constructor
    CustomTripAdapter(
            Context context,  // Main activity context
            ArrayList<String> tripId,
            ArrayList<String> tripName,
            ArrayList<String> tripDestination,
            ArrayList<String> tripDateOfTrip,
            ArrayList<String> tripRequireAssessment,
            ArrayList<String> tripDescription
    ) {
        this.context = context;
        this.tripId = tripId;
        this.tripName = tripName;
        this.tripDestination = tripDestination;
        this.tripDateOfTrip = tripDateOfTrip;
        this.tripRequireAssessment = tripRequireAssessment;
        this.tripDescription = tripDescription;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.trip_data_row, parent, false);
        return new MyViewHolder(view);
    }

    // Binding to the view
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        holder.tripIdText.setText(String.valueOf(tripId.get(position)));
        holder.tripNameText.setText(String.valueOf(tripName.get(position)));
        holder.tripDestinationText.setText(String.valueOf(tripDestination.get(position)));
        holder.tripDateText.setText(String.valueOf(tripDateOfTrip.get(position)));
        holder.tripRequireText.setText(String.valueOf(tripRequireAssessment.get(position)));

        // On click in the layout of trip data row
        holder.tripLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, UpdateTripActivity.class);

                intent.putExtra("tripId", String.valueOf(tripId.get(position)));
                intent.putExtra("tripName", String.valueOf(tripName.get(position)));
                intent.putExtra("tripDestination",
                        String.valueOf(tripDestination.get(position))
                );
                intent.putExtra("tripDate",
                        String.valueOf(tripDateOfTrip.get(position))
                );
                intent.putExtra("tripRequireAssessment",
                        String.valueOf(tripRequireAssessment.get(position))
                );
                intent.putExtra("tripDescription",
                        String.valueOf(tripDescription.get(position))
                );

                context.startActivity(intent);
            }
        });
    }

    // Loop by size of item
    @Override
    public int getItemCount() {
        return tripId.size();
    }

    // Get reference
    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tripIdText;
        TextView tripNameText;
        TextView tripDestinationText;
        TextView tripDateText;
        TextView tripRequireText;

        LinearLayout tripLinearLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tripIdText = itemView.findViewById(R.id.trip_id_txt);
            tripNameText = itemView.findViewById(R.id.trip_name_txt);
            tripDestinationText = itemView.findViewById(R.id.trip_destination_txt);
            tripDateText = itemView.findViewById(R.id.trip_date_txt);
            tripRequireText = itemView.findViewById(R.id.trip_require_txt);
            // Get reference linear layout
            tripLinearLayout = itemView.findViewById(R.id.tripLinearLayout);
            // Animation recycler view
            translateAnimation = AnimationUtils.loadAnimation(context, R.anim.translate_anim);
            tripLinearLayout.setAnimation(translateAnimation);
        }
    }
}
