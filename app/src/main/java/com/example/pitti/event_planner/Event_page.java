package com.example.pitti.event_planner;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by pitti on 30/9/17.
 */

public class Event_page extends AppCompatActivity {

    TextView event_name_in_bar;
    String event_name;
    Db_helper db;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_page);

        final Bundle event_bundle = getIntent().getExtras();
        if(event_bundle == null)
            return;
        event_name = event_bundle.getString("event_name");
        event_name_in_bar = (TextView) findViewById(R.id.event_tab_name);
        event_name_in_bar.setText(event_name);

        db = new Db_helper(this, null, null, 1);
        String[] job_list = db.get_jobs_for_event(event_name);
        //String[] job_list = {"a","b","c"};
        ListAdapter job_list_adapter = new job_list_Adapter(this,job_list);
        ListView job_list_view = (ListView) findViewById(R.id.jobs_list_view);
        job_list_view.setAdapter(job_list_adapter);

        job_list_view.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        String job = String.valueOf(parent.getItemAtPosition(position));

                        Intent i = new Intent(parent.getContext(), job_details.class);
                        i.putExtra("job_name",job);
                        i.putExtra("event_name",event_name);
                        //Toast.makeText(parent.getContext(), job + " " + event_name,Toast.LENGTH_LONG).show();
                        startActivity(i);
                    }
                }
        );

        job_list_view.setOnItemLongClickListener(
                new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(final AdapterView<?> parent, View view,final int position, long l) {

                        PopupMenu menu = new PopupMenu (parent.getContext(), view);

                        menu.inflate (R.menu.job_menu_layout);

                        menu.setOnMenuItemClickListener (new PopupMenu.OnMenuItemClickListener ()
                        {
                            @Override
                            public boolean onMenuItemClick (MenuItem item)
                            {
                                int id = item.getItemId();

                                if( id == R.id.job_delete) {
                                    String event_name_ = parent.getItemAtPosition(position).toString();
                                    //String result = db.get_events_user_display(event_name);
                                    //db.delete_event(event_name);
                                    event_name_ += " will be deleted";
                                    Toast.makeText(parent.getContext(),event_name_,Toast.LENGTH_LONG).show();

                                    //Intent i = new Intent(parent.getContext(), Home_page.class);
                                    //startActivity(i);

                                }

                                if( id == R.id.mark_as_done) {
                                    String event_name_ = parent.getItemAtPosition(position).toString();
                                    db.set_job_status(event_name_, "done");
                                    Intent i = new Intent(parent.getContext(), Event_page.class);
                                    i.putExtra("event_name",event_name);
                                    startActivity(i);
                                }

                                if( id == R.id.mark_as_delay) {
                                    String event_name_ = parent.getItemAtPosition(position).toString();
                                    db.set_job_status(event_name_, "delay");
                                    Intent i = new Intent(parent.getContext(), Event_page.class);
                                    i.putExtra("event_name",event_name);
                                    startActivity(i);
                                }

                                if( id == R.id.mark_as_not_done) {
                                    String event_name_ = parent.getItemAtPosition(position).toString();
                                    db.set_job_status(event_name_, "not_done");
                                    Intent i = new Intent(parent.getContext(), Event_page.class);
                                    i.putExtra("event_name",event_name);
                                    startActivity(i);
                                }

                                return true;
                            }
                        });

                        menu.show();
                        return true;
                    }
                }
        );


    }

    public void go_back_to_event_home(View view){
        Intent i = new Intent(this, Home_page.class);
        startActivity(i);
    }

    public void add_new_job(View view){
        Intent i = new Intent(this, Add_new_job.class);
        i.putExtra("event_name",event_name);
        startActivity(i);
    }
}
