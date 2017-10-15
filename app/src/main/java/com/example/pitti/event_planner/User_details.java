package com.example.pitti.event_planner;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

/**
 * Created by pitti on 29/9/17.
 */

public class User_details extends AppCompatActivity {

    TextView email_id;
    TextView password;
    TextView user_name;
    TextView phone_number;
    Db_helper db;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_details_profile);

        set_user_details();
    }

    public void go_back_to_home(View view){
        Intent i =new Intent(this,Home_page.class);
        startActivity(i);
    }

    public void set_user_details(){

        db = new Db_helper(this, null, null, 1);
        email_id = (TextView)findViewById(R.id.detials_emailid_input);
        password = (TextView)findViewById(R.id.detials_password_input);
        user_name = (TextView)findViewById(R.id.detials_user_name_input);
        phone_number = (TextView)findViewById(R.id.detials_phone_number_input);
        User active_user = new User();

        if(!db.is_active_user_persent())
            return;
        active_user = db.get_user(db.get_active_user());

        email_id.setText(active_user.get_mail_id());
        password.setText(active_user.get_Password());
        user_name.setText(active_user.get_user_name());
        phone_number.setText(active_user.get_phone_number());
    }
}
