package com.example.pitti.event_planner;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by pitti on 30/9/17.
 */

public class Add_new_event extends AppCompatActivity {

    EditText event_name;
    EditText start_date;
    EditText start_time;
    EditText end_time;
    Db_helper db;
    Formatchecker ch = new Formatchecker();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_new_event);

        event_name = (EditText)findViewById(R.id.event_name_input);
        start_date = (EditText)findViewById(R.id.event_date_input);
        start_time = (EditText)findViewById(R.id.event_start_time_input);
        end_time = (EditText)findViewById(R.id.event_end_time_input);
        db = new Db_helper(this, null, null, 1);
    }

    public void add_new_event(View view){
        //get details

        if(ch.is_valid_date_format(start_date.getText().toString())){
            if(ch.is_valid_time_format(start_time.getText().toString())){
                if(ch.is_valid_time_format(end_time.getText().toString())){

                    Event new_event = new Event();
                    new_event.set_details(
                            event_name.getText().toString(),
                            start_date.getText().toString(),
                            start_time.getText().toString(),
                            end_time.getText().toString()
                    );

                    //register
                    db.register_new_event(new_event);

                    //go to home
                    Intent i =new Intent(this, Home_page.class);
                    startActivity(i);

                }else   Toast.makeText(this, "End time not in HH:MM AM/PM format",Toast.LENGTH_LONG).show();
            }else   Toast.makeText(this, " start time not in HH:MM AM/PM format",Toast.LENGTH_LONG).show();
        }else Toast.makeText(this, "Start date not in dd/mm/yyyy format",Toast.LENGTH_LONG).show();

    }
}
