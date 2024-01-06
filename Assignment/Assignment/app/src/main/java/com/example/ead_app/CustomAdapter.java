package com.example.ead_app;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

    private Context context;
    Activity activity;
    private ArrayList res_id, res_nic, res_train, res_start, res_end, res_date, res_time, res_seats;


    CustomAdapter(Activity activity, Context context, ArrayList res_id, ArrayList res_nic, ArrayList res_train,
                  ArrayList res_start, ArrayList res_end, ArrayList res_date, ArrayList res_time, ArrayList res_seats){
        this.activity = activity;
        this.context = context;
        this.res_id = res_id;
        this.res_nic = res_nic;
        this.res_train = res_train;
        this.res_start = res_start;
        this.res_end = res_end;
        this.res_date = res_date;
        this.res_time = res_time;
        this.res_seats = res_seats;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.my_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomAdapter.MyViewHolder holder, int position) {

        holder.nic_text.setText(String.valueOf(res_nic.get(position)));
        holder.train_text.setText(String.valueOf(res_train.get(position)));
        holder.start_text.setText(String.valueOf(res_start.get(position)));
        holder.end_text.setText(String.valueOf(res_end.get(position)));
        holder.date_text.setText(String.valueOf(res_date.get(position)));
        holder.time_text.setText(String.valueOf(res_time.get(position)));
        holder.seats_text.setText(String.valueOf(res_seats.get(position)));

        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, UpdateReservation.class);
                intent.putExtra("id", String.valueOf(res_id.get(position)));
                intent.putExtra("nic", String.valueOf(res_nic.get(position)));
                intent.putExtra("train", String.valueOf(res_train.get(position)));
                intent.putExtra("start", String.valueOf(res_start.get(position)));
                intent.putExtra("end", String.valueOf(res_end.get(position)));
                intent.putExtra("date", String.valueOf(res_date.get(position)));
                intent.putExtra("time", String.valueOf(res_time.get(position)));
                intent.putExtra("seats", String.valueOf(res_seats.get(position)));
                activity.startActivityForResult(intent, 1);
            }
        });
    }

    //total reservations
    @Override
    public int getItemCount() {
        return res_id.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView nic_text, train_text, start_text, end_text, date_text, time_text, seats_text;
        LinearLayout mainLayout;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            nic_text = itemView.findViewById(R.id.nic_text);
            train_text = itemView.findViewById(R.id.train_text);
            start_text = itemView.findViewById(R.id.start_text);
            end_text = itemView.findViewById(R.id.end_text);
            date_text = itemView.findViewById(R.id.date_text);
            time_text = itemView.findViewById(R.id.time_text);
            seats_text = itemView.findViewById(R.id.seats_text);
            mainLayout = itemView.findViewById(R.id.mainLayout);
        }
    }
}
