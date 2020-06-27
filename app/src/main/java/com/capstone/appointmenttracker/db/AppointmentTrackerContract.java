package com.capstone.appointmenttracker.db;

import android.provider.BaseColumns;

public class AppointmentTrackerContract {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Appointment.db";
    public AppointmentTrackerContract() {
    }

    public static class AppointmentTrackerEntry implements BaseColumns{
        public static final String TABLE_NAME = "appointments";
        public static final String COLUMN_NAME_PHONENO = "phoneNo";
        public static final String COLUMN_NAME_AGE = "age";
        public static final String COLUMN_NAME_CREATEDTIME = "createdTime";
        public static final String COLUMN_NAME_APPOINTMENTDAY = "appointmentDay";
        public static final String COLUMN_NAME_APPOINTMENTTIME = "appointmentTime";
        public static final String COLUMN_NAME_ISCANCELLED = "iscancelled";
        public static final String COLUMN_NAME_ISATTENDED = "isattended";
        public static final String COLUMN_NAME_FNAME = "fname";
        public static final String COLUMN_NAME_LNAME = "lname";
        public static final String COLUMN_NAME_EMAILID = "emailid";
        public static final String COLUMN_NAME_COMMENTS = "comments";
        public static final String COLUMN_NAME_ASSIGNEDTO = "assignedto";

    }
}
