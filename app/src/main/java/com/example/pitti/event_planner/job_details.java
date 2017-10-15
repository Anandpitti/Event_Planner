package com.example.pitti.event_planner;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by pitti on 2/10/17.
 */

public class job_details extends AppCompatActivity {

    TextView job_name;
    TextView subject;
    TextView description;
    TextView start_time;
    TextView end_time;
    TextView type;
    TextView place;
    TextView start_point;
    TextView destination;
    TextView status;
    TextView people_invloved;

    Db_helper db;
    String event_name;
    String job_name_from_intent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.job_details);

        Bundle job_bundle = getIntent().getExtras();
        if(job_bundle == null)
            return;
        event_name = job_bundle.getString("event_name");
        job_name_from_intent = job_bundle.getString("job_name");
        set_job_details();

    }

    public void set_job_details(){
        job_name = (TextView) findViewById(R.id.detials_job_name_input);
        subject = (TextView) findViewById(R.id.detials_job_subject_input);
        description = (TextView) findViewById(R.id.detials_job_description_input);
        start_time = (TextView) findViewById(R.id.detials_start_time_input);
        end_time = (TextView) findViewById(R.id.detials_end_time_input);
        type = (TextView) findViewById(R.id.detials_type_input);
        place = (TextView) findViewById(R.id.detials_place_input);
        start_point = (TextView) findViewById(R.id.detials_start_point_input);
        destination = (TextView) findViewById(R.id.detials_destination_input);
        status = (TextView) findViewById(R.id.detials_status_input);
        people_invloved = (TextView) findViewById(R.id.detials_people_involved_input);

        Job job = new Job();
        db = new Db_helper(this, null, null, 1);
        job = db.get_job_details(job_name_from_intent);

        job_name.setText(job.get_job_name());
        subject.setText(job.get_subject());
        description.setText(job.get_description());
        start_time.setText(job.get_start_time());
        end_time.setText(job.get_end_time());
        type.setText(job.get_type());
        place.setText(job.get_place());
        start_point.setText(job.get_start_point());
        destination.setText(job.get_destination());
        status.setText(job.get_status());
        people_invloved.setText(job.get_people_involved_string(job.get_people_involved()));

    }

    public void go_back_to_job_list(View view){
        Intent i = new Intent(this, Event_page.class);
        i.putExtra("event_name",event_name);
        startActivity(i);
    }
}
