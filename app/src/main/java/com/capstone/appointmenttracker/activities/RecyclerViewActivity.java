package com.capstone.appointmenttracker.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.capstone.appointmenttracker.R;
import com.capstone.appointmenttracker.constants.AppointmentConsts;
import com.capstone.appointmenttracker.model.CustomerInfo;
import com.capstone.appointmenttracker.model.CustomerInfoList;
import com.capstone.appointmenttracker.notification.NotificationUtils;
import com.capstone.appointmenttracker.service.AppointmentService;
import com.capstone.appointmenttracker.utils.AppointmentUtils;
import com.capstone.appointmenttracker.views.AppointmentTrackerAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.net.HttpURLConnection;
import java.util.Calendar;

public class RecyclerViewActivity extends AppCompatActivity {
    private AppointmentTrackerAdapter adapter;
    SharedPreferences sharedPreferences;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        sharedPreferences = getSharedPreferences(AppointmentConsts.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        String businessName = sharedPreferences.getString(AppointmentConsts.BUSINESS_PREF_NAME,"");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(businessName.toUpperCase());

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new AppointmentTrackerAdapter(RecyclerViewActivity.this);
        recyclerView.setAdapter(adapter);
        FloatingActionButton add = findViewById(R.id.add);
        add.setOnClickListener(this::addPatient);
        NotificationUtils.initialize(this);

        Intent serviceIntent = new Intent(this, AppointmentService.class);
        startService(serviceIntent);

    }




    @Override
    protected void onResume() {
        super.onResume();
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);
        //SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        //getActivities();

    }

    private void addPatient(View view) {
        Intent intent = new Intent (this, AddNewCustomerActivity.class);
        startActivity(intent);

    }

}
