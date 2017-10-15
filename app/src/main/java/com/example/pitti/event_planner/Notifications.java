package com.example.pitti.event_planner;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Date;

import static android.content.Context.NOTIFICATION_SERVICE;

/**
 * Created by pitti on 3/10/17.
 */

public class Notifications extends AppCompatActivity{

    NotificationCompat.Builder notification;
    private static final int uniqueID = 45612;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        notification = new NotificationCompat.Builder(this);
        notification.setAutoCancel(true);
    }

    public Notifications(){
    }

    public void update_notifications(){
        //clear_notifications();

        Db_helper db = new Db_helper(this, null, null, 1);

        //Intent intent = new Intent(this, Home_page.class);
        //PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        //ArrayList<String> job_time = db.getnotifications_for_user(db.get_active_user());
/*        for(int i = 0; i < job_time.size(); i++){

            String full_data = job_time.get(i);
            String noti_job = full_data.substring(0,full_data.length()-20);
            String noti_time = full_data.substring(full_data.length()-19,full_data.length());

            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy KK:mm aa");

            Date date = null;
            try{
                date= sdf.parse(noti_time);
            }catch(java.text.ParseException pe){
                pe.printStackTrace();
            }

            notification = new NotificationCompat.Builder(this);
            notification.setAutoCancel(true);

            notification.setSmallIcon(R.drawable.appicon_48);
            notification.setTicker("You have new job alert");
            notification.setWhen(date.getTime());
            notification.setContentTitle("Job " + noti_job);
            notification.setContentText("This is a reminder for the start of job");

            notification.setContentIntent(pendingIntent);

            //Builds notification and issues it
            NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            nm.notify(uniqueID, notification.build());

        }
        */
    }

    public void clear_notifications(){
        NotificationManager nm = (NotificationManager) getSystemService(this.NOTIFICATION_SERVICE);
        nm.cancelAll();
    }
}
