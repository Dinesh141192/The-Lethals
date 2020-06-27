package com.capstone.appointmenttracker.utils;

import android.app.DatePickerDialog;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.Toast;

import com.capstone.appointmenttracker.AppointmentTrackerApplication;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

public class AppointmentUtils {

    public static final long ONE_DAY = 1 * 24 * 60 * 60 * 1000;
    private long appointmentDate;
    public static String getDateString(long dateInMilli) {
        DateFormat format = DateFormat.getDateInstance(DateFormat.MEDIUM);
        return format.format(new Date(dateInMilli));
    }

    public  static long getDateInLong(String dateString){
        DateFormat format = DateFormat.getDateInstance(DateFormat.MEDIUM);
        long dateInLong = 0;
        try {
            Date date = format.parse(dateString);
            dateInLong = date.getTime();

        }catch (ParseException pe){
            pe.printStackTrace();
        }
        return dateInLong;
    }

    public  static String getTimeInString(long time){

        String timeInString = "";
        int hourOfDay = (int)time/60;
        int minute = (int)time%60;
        String amPm = "";

        if(hourOfDay>=12){
            amPm = "pm";
        }else{
            amPm = "am";
        }
        //appointmentTime = hourOfDay*60 + minute;
        //textView.setText(String.format("%2d:%2d "+amPm,hourOfDay,minute));

        return String.format("%02d:%02d "+amPm,hourOfDay,minute);
    }

    public  static long getTimeInLong(String time){
        Log.e("getTimeInLong111: ", ""+time);
        String temp[] = time.split(":");
        int hourOfDay = Integer.parseInt(temp[0]);
        String temp1[] = temp[1].split(" ");;
        //appointmentTime = hourOfDay*60 + minute;
        //textView.setText(String.format("%2d:%2d "+amPm,hourOfDay,minute));
        Log.e("getTimeInLong: ",""+hourOfDay + " "+ temp1[0]);
        return (hourOfDay*60) + Integer.parseInt(temp1[0]);
    }
    public static boolean getAppointmentDatevalidity(long dateInMilli) {
        long currentTime = new Date().getTime();
        if (dateInMilli - currentTime >= ONE_DAY) {
            return false;
        } else {
            Toast.makeText(AppointmentTrackerApplication.getContext(), "You mush pick appointment date two days from the present date",
                    Toast.LENGTH_LONG).show();
            return true;
        }
    }



}
