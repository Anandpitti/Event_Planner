package com.example.pitti.event_planner;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by pitti on 30/9/17.
 */

public class Add_new_job extends AppCompatActivity {

    String event_name;
    Job new_job = new Job();
    Db_helper db;
    Formatchecker ch = new Formatchecker();

    //page1
    EditText job_name;
    EditText subject;
    EditText description;
    EditText start_time;
    EditText end_time;
    RadioButton stationary;
    RadioButton transport;

    //page stationary
    EditText place;

    //page transport
    EditText start_point;
    EditText destination;

    //page2
    EditText people_single;
    TextView people_in_job;
    ArrayList<String> people_involved = new ArrayList<String>();
    String display_people;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_new_job1);

        Bundle event_bundle = getIntent().getExtras();
        if(event_bundle == null)
            return;
        event_name = event_bundle.getString("event_name");
        new_job.set_parent_event(event_name);

        //initialise
        db = new Db_helper(this, null, null, 1);

        //page1
        job_name = (EditText) findViewById(R.id.job_name_input);
        subject = (EditText) findViewById(R.id.job_subject_input);
        description = (EditText) findViewById(R.id.job_description_input);
        start_time = (EditText) findViewById(R.id.job_start_time_input);
        end_time = (EditText) findViewById(R.id.job_end_time_input);
        //handle radiobutton
        stationary = (RadioButton) findViewById(R.id.job_stationary);
        transport = (RadioButton) findViewById(R.id.job_transport);

        //page stationary
        place = (EditText) findViewById(R.id.place_name_input);

        //page transport
        start_point = (EditText) findViewById(R.id.place_start_name_input);
        destination = (EditText) findViewById(R.id.place_end_name_input);

        //page2
        people_single =(EditText) findViewById(R.id.person_mail_id_input);
        people_in_job = (TextView) findViewById(R.id.people_in_job_output);

    }

    public void on_return_to_job_page(View view){
        Intent i = new Intent(this, Event_page.class);
        i.putExtra("event_name", event_name);
        startActivity(i);
    }

    public void on_page1_finished(View view){

        if(ch.is_valid_time_format(start_time.getText().toString())){
            if(ch.is_valid_time_format(end_time.getText().toString())){

                new_job.set_job_name(job_name.getText().toString());
                new_job.set_subject(subject.getText().toString());
                new_job.set_description(description.getText().toString());
                new_job.set_start_time(start_time.getText().toString());
                new_job.set_end_time(end_time.getText().toString());
                if(stationary.isChecked())
                    new_job.set_type("Stationary");
                else new_job.set_type("Transport");

                //Toast.makeText(this, new_job.get_type(), Toast.LENGTH_LONG).show();
                if(new_job.get_type().equals("Stationary"))
                    setContentView(R.layout.add_new_job_stationary);
                else setContentView(R.layout.add_new_job_transport);

            }else Toast.makeText(this, "end time not in HH:MM AM/PM format",Toast.LENGTH_LONG).show();
        }else Toast.makeText(this, "start time not in HH:MM AM/PM format",Toast.LENGTH_LONG).show();

    }

    public void on_return_page1(View view){
        setContentView(R.layout.add_new_job1);

        job_name = (EditText) findViewById(R.id.job_name_input);
        subject = (EditText) findViewById(R.id.job_subject_input);
        description = (EditText) findViewById(R.id.job_description_input);
        start_time = (EditText) findViewById(R.id.job_start_time_input);
        end_time = (EditText) findViewById(R.id.job_end_time_input);
        stationary = (RadioButton) findViewById(R.id.job_stationary);
        transport = (RadioButton) findViewById(R.id.job_transport);

        job_name.setText(new_job.get_job_name());
        subject.setText(new_job.get_subject());
        description.setText(new_job.get_description());
        start_time.setText(new_job.get_start_time());
        end_time.setText(new_job.get_end_time());

        if(new_job.get_type().equals("Transport"))
            transport.setChecked(true);
        else stationary.setChecked(true);

    }

    public void on_page_stationary_finished(View view){
        place = (EditText) findViewById(R.id.place_name_input);

        new_job.set_place(place.getText().toString());
        new_job.set_start_point("");
        new_job.set_destination("");

        setContentView(R.layout.add_new_job2);
    }

    public void on_return_stationary_page(View view){
        setContentView(R.layout.add_new_job_stationary);

        place = (EditText) findViewById(R.id.place_name_input);

        place.setText(new_job.get_place());
    }

    public void on_page_transport_finished(View view){
        start_point = (EditText) findViewById(R.id.place_start_name_input);
        destination = (EditText) findViewById(R.id.place_end_name_input);

        new_job.set_place("");
        new_job.set_start_point(start_point.getText().toString());
        new_job.set_destination(destination.getText().toString());

        setContentView(R.layout.add_new_job2);
    }

    public void on_return_transport_page(View view){
        setContentView(R.layout.add_new_job_transport);

        start_point = (EditText) findViewById(R.id.place_start_name_input);
        destination = (EditText) findViewById(R.id.place_end_name_input);

        start_point.setText(new_job.get_start_point());
        destination.setText(new_job.get_destination());
    }

    public void on_create_job(View view){
        new_job.set_status("");
        new_job.set_people_involved(people_involved);
        db.register_new_job(new_job);

        //Notifications hi = new Notifications();
        //hi.update_notifications();

        Intent i = new Intent(this, Event_page.class);
        i.putExtra("event_name", event_name);
        startActivity(i);
    }

    public void onreturn_inter_page(View view){
        if(new_job.get_type().equals("Stationary"))
            on_return_stationary_page(view);
        else on_return_transport_page(view);
    }

    public void on_add_people(View view){
        people_single =(EditText) findViewById(R.id.person_mail_id_input);
        people_in_job = (TextView) findViewById(R.id.people_in_job_output);

        if(db.is_user_valid(people_single.getText().toString())) {
            if (people_involved.indexOf(people_single.getText().toString()) == -1)
                people_involved.add(people_single.getText().toString());
            else Toast.makeText(this, "user already added",Toast.LENGTH_LONG).show();
        }
        else    Toast.makeText(this, "not a valid user",Toast.LENGTH_LONG).show();

        display_people = "";
        for(int i=0;i<people_involved.size();i++) {
            display_people += people_involved.get(i);
            display_people += '\n';
        }

        people_single.setText("");
        people_in_job.setText(display_people);
    }

    public void on_remove_people(View view){
        people_single =(EditText) findViewById(R.id.person_mail_id_input);
        people_in_job = (TextView) findViewById(R.id.people_in_job_output);

        if(people_involved.indexOf(people_single.getText().toString()) != -1)
            people_involved.remove(people_single.getText().toString());

        display_people = "";
        for(int i=0;i<people_involved.size();i++) {
            display_people += people_involved.get(i);
            display_people += '\n';
        }

        people_in_job.setText(display_people);
    }

}
