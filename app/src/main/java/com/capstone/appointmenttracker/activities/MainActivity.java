package com.capstone.appointmenttracker.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.capstone.appointmenttracker.R;
import com.capstone.appointmenttracker.constants.AppointmentConsts;
import com.capstone.appointmenttracker.views.AppointmentTrackerAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

public class MainActivity extends AppCompatActivity {
    TextInputEditText businessName;
    Button submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        businessName =findViewById(R.id.business_name);
        submit = findViewById(R.id.submit);
        submit.setOnClickListener(this::openActivity);

    }
    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences sharedPreferences = getSharedPreferences(AppointmentConsts.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        if(!sharedPreferences.getString(AppointmentConsts.BUSINESS_PREF_NAME,"").equals("")){
            Intent intent =  new Intent(getApplicationContext(), RecyclerViewActivity.class);
            startActivity(intent);
        }

    }

    private void openActivity(View view) {
        SharedPreferences sharedPreferences = MainActivity.this.getSharedPreferences(AppointmentConsts.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(AppointmentConsts.BUSINESS_PREF_NAME,businessName.getText().toString());
        editor.commit();
        Intent intent =  new Intent(getApplicationContext(), RecyclerViewActivity.class);
        startActivity(intent);
    }
}
