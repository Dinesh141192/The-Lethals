package com.capstone.appointmenttracker.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import com.capstone.appointmenttracker.model.CustomerInfoList;
import com.capstone.appointmenttracker.notification.NotificationUtils;
import com.capstone.appointmenttracker.utils.AppointmentUtils;

import java.util.Calendar;

public class AppointmentService extends Service {
    private int secondsPassed = 0;
    private int timeInfo = 0;
    private Handler timeHandler = new Handler();
    private Runnable timeRunnable = new Runnable() {
        @Override
        public void run() {
            Calendar calendar = Calendar.getInstance();
            //calendar.setTime(yourdate);
            int hours = calendar.get(Calendar.HOUR_OF_DAY);
            int minutes = calendar.get(Calendar.MINUTE);
            int seconds = calendar.get(Calendar.SECOND);
            long millis = calendar.getTimeInMillis();
            Log.e("getNotification: ", ""+hours+minutes);
            secondsPassed += 1;
            if (secondsPassed % 5 == 0) {
                //Toast.makeText(getApplicationContext(), "Service runnning", Toast.LENGTH_SHORT).show();

                for(int i = 0; i< CustomerInfoList.getOBJECT().getCount(); i++){
                    boolean isSameDay = AppointmentUtils.getDateString(millis).equals(AppointmentUtils.getDateString(CustomerInfoList.getOBJECT().get(i).getAppointmentDay()));
                    long timeDifference = CustomerInfoList.getOBJECT().get(i).getAppointmentTime() - (hours*60+minutes);
                    if(isSameDay && timeDifference <=30 && timeDifference>0){
                        Log.e( "doInBackground: ","appointment"+i+ AppointmentUtils.getTimeInString(CustomerInfoList.getOBJECT().get(i).getAppointmentTime()) );
                        String message = CustomerInfoList.getOBJECT().get(i).getFname()+" has appointment with you at "+ AppointmentUtils.getTimeInString(CustomerInfoList.getOBJECT().get(i).getAppointmentTime());

                        //NotificationUtils.getInstance().sendTimeNotification(message);
                        NotificationUtils.getInstance().notifyForegroundNotification(i,message);


                    }else{
                        NotificationUtils.getInstance().cancelNotification(i);
                    }

                    Log.e( "doInBackground: ","triggered"+ AppointmentUtils.getTimeInString(CustomerInfoList.getOBJECT().get(i).getAppointmentTime()) );

                    Log.e( "11111: ",""+((hours*60+minutes) - CustomerInfoList.getOBJECT().get(i).getAppointmentTime() ));
                    Log.e( "2222: ",""+ (hours*60+minutes));
                    Log.e( "3333: ",""+ AppointmentUtils.getDateString(calendar.getTimeInMillis()));

                    Log.e( "3333: ",""+ AppointmentUtils.getDateString(CustomerInfoList.getOBJECT().get(i).getAppointmentDay()));


                    Log.e("run:444 ", ""+AppointmentUtils.getDateString(millis).equals(AppointmentUtils.getDateString(CustomerInfoList.getOBJECT().get(i).getAppointmentDay())));





                }

            }

            timeHandler.postDelayed(timeRunnable, 1000);
        }
    };

    public class AppointmentBinder extends Binder {
        public AppointmentService getService() {
            return AppointmentService.this;
        }
    }

    private AppointmentBinder binder = new AppointmentBinder();

    @Override
    public void onCreate() {
        //Toast.makeText(this, "Service created", Toast.LENGTH_SHORT).show();
        secondsPassed = 0;
        timeHandler.postDelayed(timeRunnable, 1000);
    }


    @Override
    public void onDestroy() {
        //Toast.makeText(this, "Service destroyed", Toast.LENGTH_SHORT).show();
        timeHandler.removeCallbacks(timeRunnable);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //Toast.makeText(this, "onStartCommand", Toast.LENGTH_SHORT).show();
        return START_NOT_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

}
