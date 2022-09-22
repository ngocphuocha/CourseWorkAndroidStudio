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
    private final ArrayList ids;
    private final ArrayList types;
    private final ArrayList amounts;
    private final ArrayList times;
    private Animation translateAnimation;

    public CustomExpressionAdapter(
            Context context,
            ArrayList ids,
            ArrayList types,
            ArrayList amounts,
            ArrayList times
    ) {
        this.context = context;
        this.ids = ids;
        this.types = types;
        this.amounts = amounts;
        this.times = times;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.expression_data_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        holder.typeText.setText(String.valueOf(types.get(position)));
        holder.amountText.setText(String.valueOf(amounts.get(position)));
        holder.timeText.setText(String.valueOf(times.get(position)));
    }

    @Override
    public int getItemCount() {
        return ids.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView typeText, amountText, timeText;
        LinearLayout expressionLinearLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            typeText = itemView.findViewById(R.id.expressionTypeTxt);
            amountText = itemView.findViewById(R.id.amountExpressionTxt);
            timeText = itemView.findViewById(R.id.timeExpressionTxt);

            // Get reference linear layout
            expressionLinearLayout = itemView.findViewById(R.id.expresonLinearLayout);

            // Animation recycle view
            translateAnimation = AnimationUtils.loadAnimation(context, R.anim.translate_anim);
            expressionLinearLayout.setAnimation(translateAnimation);
        }
    }
}
