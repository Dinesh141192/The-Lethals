package com.capstone.appointmenttracker.views;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.capstone.appointmenttracker.AppointmentTrackerApplication;
import com.capstone.appointmenttracker.R;
import com.capstone.appointmenttracker.activities.ViewUpdateActivity;
import com.capstone.appointmenttracker.model.CustomerInfo;
import com.capstone.appointmenttracker.model.CustomerInfoList;
import com.capstone.appointmenttracker.utils.AppointmentUtils;

public class AppointmentTrackerAdapter extends RecyclerView.Adapter<AppointmentTrackerAdapter.AppointmentTrackerViewHolder> {
    private Context context;
    public AppointmentTrackerAdapter(Context applicationContext){
        setHasStableIds(true);
        this.context = applicationContext;
    }

    @Override
    public long getItemId(int position) {
        return CustomerInfoList.getOBJECT().get(position).getId();
    }

    @NonNull
    @Override
    public AppointmentTrackerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_appointment_tracker,parent,false);
        return new AppointmentTrackerViewHolder(layout);
    }

    @Override
    public void onBindViewHolder(@NonNull AppointmentTrackerViewHolder holder, int position) {
        //populate
        Log.e("onBindViewHolder: ", "");
        holder.populate(CustomerInfoList.getOBJECT().get(position));
    }

    @Override
    public int getItemCount() {
        return CustomerInfoList.getOBJECT().getCount();
    }

    public class AppointmentTrackerViewHolder extends RecyclerView.ViewHolder {
        private CustomerInfo customerInfo;
        private TextView name, age, phoneNo, appointmentTime;

        public AppointmentTrackerViewHolder(@NonNull View itemView) {
            super(itemView);
            this.name = itemView.findViewById(R.id.name);
            this.age = itemView.findViewById(R.id.age);
            this.phoneNo = itemView.findViewById(R.id.phone_num);
            this.appointmentTime = itemView.findViewById(R.id.appointment_date);
        }

        public void populate(CustomerInfo customerInfo){
            this.customerInfo =customerInfo;
            name.setText(customerInfo.getFname());
            Log.e("populateage: ", ""+customerInfo.getAge());
            age.setText(""+customerInfo.getAge());
            phoneNo.setText(customerInfo.getPhoneNo());
            appointmentTime.setText(AppointmentUtils.getDateString(customerInfo.getAppointmentDay()));
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle = new Bundle();
                    Intent intent = new Intent(AppointmentTrackerApplication.getContext(), ViewUpdateActivity.class);
                    bundle.putSerializable("customerInfo",customerInfo);
                    intent.putExtras(bundle);
                    context.startActivity(intent);
                }
            });
        }

    }
}
