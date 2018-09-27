package com.example.android.evcharge;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.MyViewHolder> {

        private ArrayList<String> duration;
        private ArrayList<String> power;
        private ArrayList<String> money;
        private ArrayList<String> location;
        private ArrayList<String> timeStamp;
        public TransactionFragment context;


        public TransactionAdapter(TransactionFragment context, ArrayList<String> duration, ArrayList<String> power, ArrayList<String> money, ArrayList<String> location, ArrayList<String> timeStamp){
            this.context=context;
            this.duration=duration;
            this.power=power;
            this.money=money;
            this.location=location;
            this.timeStamp=timeStamp;
        }


        //This method inflates view present in the RecyclerView
        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v=LayoutInflater.from(parent.getContext()).inflate(R.layout.card_transaction, parent, false);
            MyViewHolder holder=new MyViewHolder(v);
            return holder;
        }

        //Binding the data using get() method of POJO object
        @Override
        public void onBindViewHolder(final TransactionAdapter.MyViewHolder holder, int position) {
            holder.tvDuration.setText(duration.get(position));
            holder.tvPower.setText(power.get(position));
            holder.tvMoney.setText(money.get(position));
            holder.tvLocation.setText(location.get(position));
            holder.tvTimeStamp.setText(timeStamp.get(position));

        }



        @Override
        public int getItemCount() {
            return duration.size();
        }


        //View holder class, where all view components are defined
        class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

            public TextView tvDuration;
            public TextView tvPower;
            public TextView tvMoney;
            public TextView tvLocation;
            public TextView tvTimeStamp;
            public MyViewHolder(View itemView) {
                super(itemView);
                itemView.setOnClickListener(this);
                tvDuration=(TextView)itemView.findViewById(R.id.tv_duration_transaction);
                tvPower=(TextView)itemView.findViewById(R.id.tv_power_transaction);
                tvMoney=(TextView)itemView.findViewById(R.id.tv_money_transaction);
                tvLocation=(TextView)itemView.findViewById(R.id.tv_location_transaction);
                tvTimeStamp=(TextView)itemView.findViewById(R.id.tv_time_stamp_transaction);

            }

            @Override
            public void onClick(View v) {

            }

        }


    }



