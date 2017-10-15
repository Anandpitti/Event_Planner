package com.example.pitti.event_planner;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import java.text.SimpleDateFormat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.support.v7.widget.PopupMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.StringTokenizer;

public class Home_page extends AppCompatActivity {

    TextView user_name;
    Db_helper db;
    User user;
    ArrayList<String> job_time = new ArrayList<String>();
    Integer noti_size;

    private static final int uniqueID = 45612;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        db = new Db_helper(this, null, null, 1);
        if(db.is_active_user_persent()) {
            user = db.get_user(db.get_active_user());

            user_name = (TextView) findViewById(R.id.user_name);
            user_name.setText(user.get_user_name());

            //call some function and get the events for this guy
            //Event[] event_list = somefunction();

            job_time = db.getnotifications_for_user(user.get_mail_id());
            noti_size = job_time.size();

            String[] event_list = db.get_events_for_user(db.get_active_user());
            ListAdapter event_list_adapter = new Event_list_Adapter(this,event_list);
            ListView event_list_view = (ListView) findViewById(R.id.events_list_view);
            event_list_view.setAdapter(event_list_adapter);

            event_list_view.setOnItemClickListener(
                    new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            String event = String.valueOf(parent.getItemAtPosition(position));

                            Intent i = new Intent(parent.getContext(), Event_page.class);
                            i.putExtra("event_name",event);
                            startActivity(i);
                        }
                    }
            );

            event_list_view.setOnItemLongClickListener(
                    new AdapterView.OnItemLongClickListener() {
                        @Override
                        public boolean onItemLongClick(final AdapterView<?> parent, View view,final int position, long l) {

                            PopupMenu menu = new PopupMenu (parent.getContext(), view);

                            menu.inflate (R.menu.delete_menu_layout);

                            menu.setOnMenuItemClickListener (new PopupMenu.OnMenuItemClickListener ()
                            {
                                @Override
                                public boolean onMenuItemClick (MenuItem item)
                                {
                                    int id = item.getItemId();

                                        if( id == R.id.event_delete) {
                                            String event_name = parent.getItemAtPosition(position).toString();
                                            //String result = db.get_events_user_display(event_name);
                                            //db.delete_event(event_name);
                                            event_name += " will be deleted";
                                            Toast.makeText(parent.getContext(),event_name,Toast.LENGTH_LONG).show();

                                            //Intent i = new Intent(parent.getContext(), Home_page.class);
                                            //startActivity(i);

                                        }

                                    return true;
                                }
                            });

                            menu.show();
                            return true;
                        }
                    }
            );

        }else{
            Intent i = new Intent(this, Login_page.class);
            startActivity(i);
        }

        update_notifications();

    }

    public void update_notifications(){
        clear_notifications();

        NotificationCompat.Builder notification;
        notification = new NotificationCompat.Builder(this);
        notification.setAutoCancel(true);

        //Toast.makeText(this, noti_size,Toast.LENGTH_LONG).show();

        Integer in;
        for(in=0; in<noti_size; in++){

            String full_data = job_time.get(in);
            String noti_job = full_data.substring(0,full_data.length()-20);
            String noti_time = full_data.substring(full_data.length()-20,full_data.length());

            //Toast.makeText(this, noti_time,Toast.LENGTH_LONG).show();

            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy KK:mm aa");

            Date date = null;
            try{
                date= sdf.parse(noti_time);
            }catch(java.text.ParseException pe){
                pe.printStackTrace();
            }

            //Toast.makeText(this, String.valueOf(date.getTime()+(((5*60)+30)*60)*10000) +"\n"+ System.currentTimeMillis(),Toast.LENGTH_LONG).show();
            if((date.getTime() - System.currentTimeMillis()) > 0) {
                notification.setSmallIcon(R.drawable.appicon_48);
                notification.setTicker("You have new job alert");
                notification.setWhen(date.getTime());
                notification.setContentTitle("Job " + noti_job);
                notification.setContentText("This is a reminder for the start of job");

                //Builds notification and issues it
                NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                nm.notify(uniqueID+in, notification.build());
            }

        }

    }

    public void clear_notifications(){
        NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        nm.cancelAll();
    }

    public void logout_user(View view){

        if(db.is_active_user_persent())
            db.unset_active_user();
        Intent i = new Intent(this, Login_page.class);
        startActivity(i);

    }

    public void display_user_profile(View view){
        Intent i = new Intent(this, User_details.class);
        startActivity(i);
    }

    public void add_event(View view){
        Intent i = new Intent(this, Add_new_event.class);
        startActivity(i);
    }
}
