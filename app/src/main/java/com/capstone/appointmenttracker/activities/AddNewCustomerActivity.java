package com.capstone.appointmenttracker.activities;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.capstone.appointmenttracker.AppointmentTrackerApplication;
import com.capstone.appointmenttracker.R;
import com.capstone.appointmenttracker.listeners.OnNewCustomerListener;
import com.capstone.appointmenttracker.model.CustomerInfoList;
import com.capstone.appointmenttracker.utils.AppointmentUtils;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Calendar;
import java.util.Date;

public class AddNewCustomerActivity extends AppCompatActivity {
    private OnNewCustomerListener listener;
    private TextInputEditText fnameEditText,lnameEditText,emailIDEditText,commentsEditText, assignedEditText, ageEditText,phoneNumEditText;
    private TextView dateTextView,timeTextView;
    private long appointmentDate, appointmentTime;
    Button add, cancel;
    TimePickerDialog timePickerDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_customer);
        fnameEditText = findViewById(R.id.fname);
        lnameEditText = findViewById(R.id.lname);
        emailIDEditText = findViewById(R.id.email_id);
        commentsEditText = findViewById(R.id.comments);
        assignedEditText = findViewById(R.id.assigned_to);
        ageEditText = findViewById(R.id.age);
        phoneNumEditText = findViewById(R.id.phone_num);
        dateTextView = findViewById(R.id.appointment_date);
        timeTextView = findViewById(R.id.appointment_time);
        dateTextView.setOnClickListener(this::addDatePickDialog);
        timeTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectTime(timeTextView);
            }
        });
        add = findViewById(R.id.add);
        add.setOnClickListener(this::addCustomer);
        cancel = findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }

    private void addCustomer(View view) {
        if(fnameEditText.getText()==null || lnameEditText.getText()==null || emailIDEditText.getText()==null || ageEditText.getText()==null || commentsEditText.getText()==null || assignedEditText.getText() == null || phoneNumEditText.getText()== null)
        {
            return;
        }

        String fname = fnameEditText.getText().toString();
        String lname = lnameEditText.getText().toString();
        String emailID = emailIDEditText.getText().toString();
        String comments = commentsEditText.getText().toString();
        String assignedTo = assignedEditText.getText().toString();
        String phoneNum = phoneNumEditText.getText().toString();
        String age = ageEditText.getText().toString();
        if(fname.isEmpty() || age.isEmpty() || phoneNum.isEmpty()){
            Toast.makeText(AppointmentTrackerApplication.getContext(), "Please fill all the details properly",
                    Toast.LENGTH_LONG).show();
            return;
        }
        CustomerInfoList.getOBJECT().add(fname, lname, emailID, comments, assignedTo, phoneNum, Integer.parseInt(age), new Date().getTime(),appointmentDate, appointmentTime);
        onBackPressed();
    }

    private void addDatePickDialog(View view) {
        Calendar appointmentCalendar = Calendar.getInstance();
        appointmentCalendar.setTime(appointmentDate > 0 ? new Date(appointmentDate) : new Date());
        int year = appointmentCalendar.get(Calendar.YEAR);
        int month = appointmentCalendar.get(Calendar.MONTH);
        int day = appointmentCalendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePicker = new DatePickerDialog(view.getContext(),this::onAppointmentDateSet,year,month,day);
        datePicker.show();
    }
    private void onAppointmentDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(year,month,dayOfMonth);
        appointmentDate = calendar.getTimeInMillis();
        dateTextView.setText(AppointmentUtils.getDateString(appointmentDate));

    }
    private void selectTime(TextView textView) {
        timePickerDialog = new TimePickerDialog(AddNewCustomerActivity.this, new TimePickerDialog.OnTimeSetListener() {
            Calendar calendar = Calendar.getInstance();
            String amPm = "";



            @Override
            public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
                if(hourOfDay>=12){
                    amPm = "pm";
                }else{
                    amPm = "am";
                }
                appointmentTime = hourOfDay*60 + minute;
                textView.setText(String.format("%02d:%02d "+amPm,hourOfDay,minute));
            }
        },0,0,false);
        timePickerDialog.show();
    }
}
