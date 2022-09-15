package com.example.coursework;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomTripAdapter extends RecyclerView.Adapter<CustomTripAdapter.MyViewHolder> {
    Context context;
    ArrayList trip_id, trip_name, trip_destination, trip_date_of_trip, trip_require_assessment;

    //Constructor
    CustomTripAdapter(Context context,
                      ArrayList trip_id,
                      ArrayList trip_name,
                      ArrayList trip_destination,
                      ArrayList trip_date_of_trip,
                      ArrayList trip_require_assessment) {
        this.context = context;
        this.trip_id = trip_id;
        this.trip_name = trip_name;
        this.trip_destination = trip_destination;
        this.trip_date_of_trip = trip_date_of_trip;
        this.trip_require_assessment = trip_require_assessment;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.trip_data_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.trip_id_txt.setText(String.valueOf(trip_id.get(position)));
        holder.trip_name_txt.setText(String.valueOf(trip_name.get(position)));
        holder.trip_destination_txt.setText(String.valueOf(trip_destination.get(position)));
        holder.trip_date_txt.setText(String.valueOf(trip_date_of_trip.get(position)));
        holder.trip_require_txt.setText(String.valueOf(trip_require_assessment.get(position)));
    }

    @Override
    public int getItemCount() {
        return trip_id.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView trip_id_txt, trip_name_txt, trip_destination_txt, trip_date_txt, trip_require_txt;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            trip_id_txt = itemView.findViewById(R.id.trip_id_txt);
            trip_name_txt = itemView.findViewById(R.id.trip_name_txt);
            trip_destination_txt = itemView.findViewById(R.id.trip_destination_txt);
            trip_date_txt = itemView.findViewById(R.id.trip_date_txt);
            trip_require_txt = itemView.findViewById(R.id.trip_require_txt);

        }
    }
}
