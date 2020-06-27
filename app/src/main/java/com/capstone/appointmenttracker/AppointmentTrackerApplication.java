package com.capstone.appointmenttracker;

import android.app.Application;
import android.content.Context;

import com.capstone.appointmenttracker.db.AppointmentTrackerDBHelper;

public class AppointmentTrackerApplication extends Application {
    public static Context getContext() {
        return context;
    }

    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        AppointmentTrackerApplication.context = getApplicationContext();
        AppointmentTrackerDBHelper.initialize(getApplicationContext());

    }


}
