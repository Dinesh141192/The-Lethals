package com.capstone.appointmenttracker.notification;


import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;

import com.capstone.appointmenttracker.activities.RecyclerViewActivity;

public class NotificationUtils {

    private static final int TIME_NOTIFICATION_ID = 1;

    private static final int FOREGROUND_NOTIFICATION_ID = 2;

    private  static NotificationUtils INSTANCE;
    private static final String CHANNEL_ID = "customerNotificationChannel";
    private  Context context;

    public static void initialize(Context context){
        INSTANCE = new NotificationUtils(context);
    }

    public static NotificationUtils getInstance(){
        return INSTANCE;
    }

    private NotificationUtils(Context context) {
        this.context = context;
        createNotificationChannel();
    }




    private void createNotificationChannel() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID, "Music Player", NotificationManager.IMPORTANCE_DEFAULT);

            NotificationManager manager = context.getSystemService(NotificationManager.class);
            if(manager!=null){

                manager.createNotificationChannel(notificationChannel);
            }

        }


    }

    public Notification getForegroundNotification(String timeInNessage){
        Intent intent = new Intent(context,RecyclerViewActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);


        PendingIntent pendingIntent = PendingIntent.getActivity(context,0,intent,0);
        return new NotificationCompat.Builder(context,CHANNEL_ID)
                .setSmallIcon(android.R.drawable.star_on)
                .setContentTitle("Customer Details")
                .setContentText(timeInNessage)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .build();
        // NotificationManagerCompat.from(context).notify(TIME_NOTIFICATION_ID,notification);
    }

    public  void notifyForegroundNotification(int id,String timeInNessage){
        Notification notification = getForegroundNotification(timeInNessage);
        NotificationManagerCompat.from(context).notify(id,notification);


    }

    public void cancelNotification(int id){
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancel(id);
    }



    public void sendTimeNotification(String timeInNessage){
        Intent intent = new Intent(context, RecyclerViewActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context,0,intent,0);
        Notification notification = new NotificationCompat.Builder(context,CHANNEL_ID)
                .setSmallIcon(android.R.drawable.star_on)
                .setContentTitle("Customer Details")
                .setContentText(timeInNessage)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .setOnlyAlertOnce(true)
                .build();
        NotificationManagerCompat.from(context).notify(TIME_NOTIFICATION_ID,notification);
    }




}
