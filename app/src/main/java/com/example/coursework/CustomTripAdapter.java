package com.example.coursework;

import android.app.Activity;
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
    Activity activity;
    Context context;
    ArrayList trip_id, trip_name, trip_destination, trip_date_of_trip, trip_require_assessment, trip_description;

    Animation translateAnimation;

    //Constructor
    CustomTripAdapter(
//            Activity activity,
            Context context,
            ArrayList trip_id,
            ArrayList trip_name,
            ArrayList trip_destination,
            ArrayList trip_date_of_trip,
            ArrayList trip_require_assessment,
            ArrayList trip_description) {
//        this.activity = activity;
        this.context = context;
        this.trip_id = trip_id;
        this.trip_name = trip_name;
        this.trip_destination = trip_destination;
        this.trip_date_of_trip = trip_date_of_trip;
        this.trip_require_assessment = trip_require_assessment;
        this.trip_description = trip_description;
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
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.trip_id_txt.setText(String.valueOf(trip_id.get(position)));
        holder.trip_name_txt.setText(String.valueOf(trip_name.get(position)));
        holder.trip_destination_txt.setText(String.valueOf(trip_destination.get(position)));
        holder.trip_date_txt.setText(String.valueOf(trip_date_of_trip.get(position)));
        holder.trip_require_txt.setText(String.valueOf(trip_require_assessment.get(position)));

        // On click in the layout of trip data row
        holder.tripLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, UpdateTripActivity.class);
                intent.putExtra("trip_id", String.valueOf(trip_id.get(position)));
                intent.putExtra("trip_name", String.valueOf(trip_name.get(position)));
                intent.putExtra("trip_destination", String.valueOf(trip_destination.get(position)));
                intent.putExtra("trip_date", String.valueOf(trip_date_of_trip.get(position)));
                intent.putExtra("trip_requireAssessment", String.valueOf(trip_require_assessment.get(position)));
                intent.putExtra("trip_description", String.valueOf(trip_description.get(position)));
                context.startActivity(intent);
            }
        });
    }

    // Loop by size of item
    @Override
    public int getItemCount() {
        return trip_id.size();
    }

    // Get reference
    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView trip_id_txt, trip_name_txt, trip_destination_txt, trip_date_txt, trip_require_txt;
        LinearLayout tripLinearLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            trip_id_txt = itemView.findViewById(R.id.trip_id_txt);
            trip_name_txt = itemView.findViewById(R.id.trip_name_txt);
            trip_destination_txt = itemView.findViewById(R.id.trip_destination_txt);
            trip_date_txt = itemView.findViewById(R.id.trip_date_txt);
            trip_require_txt = itemView.findViewById(R.id.trip_require_txt);
            // Get reference linear layout
            tripLinearLayout = itemView.findViewById(R.id.tripLinearLayout);
            // Animation recycler view
            translateAnimation = AnimationUtils.loadAnimation(context,R.anim.translate_anim);
            tripLinearLayout.setAnimation(translateAnimation);
        }
    }
}
