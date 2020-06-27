package com.capstone.appointmenttracker.activities;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.capstone.appointmenttracker.R;
import com.capstone.appointmenttracker.model.CustomerInfo;
import com.capstone.appointmenttracker.model.CustomerInfoList;
import com.capstone.appointmenttracker.utils.AppointmentUtils;
import com.capstone.appointmenttracker.views.AppointmentTrackerAdapter;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Calendar;
import java.util.Date;

public class ViewUpdateActivity extends AppCompatActivity {
    private TextInputEditText fnameEditText,lnameEditText,emailIDEditText,commentsEditText,assignedEditText,ageEditText,phoneNumEditText;
    private TextView dateTextView,timeTextView;
    private Button update,cancelAppointment,attended;
    private long appointmentDate, appointmentTime;
    CustomerInfo customerInfo;
    AppointmentTrackerAdapter adapter;
    TimePickerDialog timePickerDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_customer);
        Intent intent = this.getIntent();
        customerInfo = (CustomerInfo) intent.getExtras().getSerializable("customerInfo");
        fnameEditText = findViewById(R.id.fname);
        lnameEditText = findViewById(R.id.lname);
        emailIDEditText = findViewById(R.id.email_id);
        commentsEditText = findViewById(R.id.comments);
        assignedEditText = findViewById(R.id.assigned_to);
        ageEditText = findViewById(R.id.age);
        phoneNumEditText = findViewById(R.id.phone_num);
        dateTextView = findViewById(R.id.appointment_date);
        timeTextView = findViewById(R.id.appointment_time);
        timeTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectTime(timeTextView);
            }
        });
        dateTextView.setOnClickListener(this::addDatePickDialog);
        populateCustomerInfo(customerInfo);
        update = findViewById(R.id.update);
        cancelAppointment =findViewById(R.id.cancel_appointment);
        attended = findViewById(R.id.attended);
        update.setOnClickListener(this::onUpdate);
        cancelAppointment.setOnClickListener(this::onCancel);
        attended.setOnClickListener(this::onAttended);

    }

    private void onUpdate(View view) {
        Log.e("onUpdate: ", "calling update");
        customerInfo.setAge(Integer.parseInt(ageEditText.getText().toString()));
        customerInfo.setFname(fnameEditText.getText().toString());
        customerInfo.setLname(lnameEditText.getText().toString());
        customerInfo.setEmailID(emailIDEditText.getText().toString());
        customerInfo.setComments(commentsEditText.getText().toString());
        customerInfo.setAssignedTo(assignedEditText.getText().toString());
        customerInfo.setAppointmentDay(AppointmentUtils.getDateInLong(dateTextView.getText().toString()));
        customerInfo.setAppointmentTime(AppointmentUtils.getTimeInLong(timeTextView.getText().toString()));
        Log.e("Appointment time : ", timeTextView.getText().toString());
        customerInfo.setPhoneNo(phoneNumEditText.getText().toString());

        CustomerInfoList.getOBJECT().update(customerInfo.getId(), customerInfo);
        onBackPressed();
    }
    private void onAttended(View view) {
        CustomerInfoList.getOBJECT().addAttendedStatus(customerInfo);
        adapter = new AppointmentTrackerAdapter(ViewUpdateActivity.this);
        onBackPressed();

    }
    private void onCancel(View view) {
        CustomerInfoList.getOBJECT().addCancelledStatus(customerInfo);
        adapter = new AppointmentTrackerAdapter(ViewUpdateActivity.this);
        onBackPressed();
    }

    private void populateCustomerInfo(CustomerInfo customerInfo) {
        fnameEditText.setText(customerInfo.getFname());
        lnameEditText.setText(customerInfo.getLname());
        emailIDEditText.setText(customerInfo.getEmailID());
        commentsEditText.setText(customerInfo.getComments());
        assignedEditText.setText(customerInfo.getAssignedTo());
        ageEditText.setText(""+customerInfo.getAge());
        phoneNumEditText.setText(customerInfo.getPhoneNo());
        dateTextView.setText(AppointmentUtils.getDateString(customerInfo.getAppointmentDay()));
        timeTextView.setText(AppointmentUtils.getTimeInString(customerInfo.getAppointmentTime()));
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
        timePickerDialog = new TimePickerDialog(ViewUpdateActivity.this, new TimePickerDialog.OnTimeSetListener() {
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
