package com.example.coursework;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomExpressionAdapter extends RecyclerView.Adapter<CustomExpressionAdapter.MyViewHolder> {
    private final Context context;
    private final ArrayList id;
    private final ArrayList type;
    private final ArrayList amount;
    private final ArrayList time;
    private Animation translateAnimation;

    public CustomExpressionAdapter(Context context, ArrayList id, ArrayList type, ArrayList amount, ArrayList time) {
        this.context = context;
        this.id = id;
        this.type = type;
        this.amount = amount;
        this.time = time;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.expression_data_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.type_txt.setText(String.valueOf(type.get(position)));
        holder.amount_txt.setText(String.valueOf(amount.get(position)));
        holder.time_txt.setText(String.valueOf(time.get(position)));
    }

    @Override
    public int getItemCount() {
        return id.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView type_txt, amount_txt, time_txt;
        LinearLayout expressionLinearLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            type_txt = itemView.findViewById(R.id.expressionTypeTxt);
            amount_txt = itemView.findViewById(R.id.amountExpressionTxt);
            time_txt = itemView.findViewById(R.id.timeExpressionTxt);
            // Get reference linear layout
            expressionLinearLayout = itemView.findViewById(R.id.expresonLinearLayout);
            // Animation recycle view
            translateAnimation = AnimationUtils.loadAnimation(context, R.anim.translate_anim);
            expressionLinearLayout.setAnimation(translateAnimation);
        }
    }
}
