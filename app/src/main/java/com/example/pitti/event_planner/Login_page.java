package com.example.pitti.event_planner;

/**
 * Created by pitti on 28/9/17.
 */

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class Login_page extends AppCompatActivity{

    Db_helper db;
    Formatchecker ch = new Formatchecker();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        db = new Db_helper(this, null, null, 1);
    }

    public void set_login_page(View view){
        setContentView(R.layout.activity_login);
    }

    public void set_signup_page(View view){
        setContentView(R.layout.activity_signup);
    }

    public void login_user(View view){
        //get details
        EditText emailid_input = (EditText) findViewById(R.id.login_emailid_input);
        EditText password_input = (EditText) findViewById(R.id.login_password_input);

        String user_emailid = emailid_input.getText().toString();
        String password = password_input.getText().toString();

        if(db.is_user_valid(user_emailid)){

            if(db.login_check(user_emailid, password)){

                if(db.is_active_user_persent())
                    db.unset_active_user();
                db.set_active_user(user_emailid);
                Intent i = new Intent(this, Home_page.class);
                startActivity(i);

            }else{
                //incorrect password
                Toast.makeText(this, "Incorrect password. try again",Toast.LENGTH_LONG).show();
            }

       }else{
            //incorrect username
            Toast.makeText(this, "Invalid user name. try again",Toast.LENGTH_LONG).show();
        }

    }

    public void register_new_user(View view){
        //get details
        EditText emailid_input = (EditText) findViewById(R.id.emailid_input);
        EditText password_input = (EditText) findViewById(R.id.password_input);
        EditText user_name_input = (EditText) findViewById(R.id.user_name_input);
        EditText phone_number_input = (EditText) findViewById(R.id.phone_number_input);

        String user_emailid = emailid_input.getText().toString();
        String password = password_input.getText().toString();
        String user_name = user_name_input.getText().toString();
        String phone_number = phone_number_input.getText().toString();

        if(ch.is_valid_email_id(user_emailid)){
            if(ch.is_valid_phone_number(phone_number)){
                if(!db.is_user_valid(user_emailid)){

                    User new_user = new User();
                    new_user.set_user_details(user_emailid, password, user_name, phone_number);
                    db.register_user(new_user);
                    if(db.is_active_user_persent())
                        db.unset_active_user();
                    db.set_active_user(user_emailid);
                    Intent i = new Intent(this, Home_page.class);
                    startActivity(i);

                }else Toast.makeText(this, "User has already registered.", Toast.LENGTH_LONG).show();
            }else Toast.makeText(this, "Phone number must contain exctly 10 digits", Toast.LENGTH_LONG).show();
        }else Toast.makeText(this, "email id not valid", Toast.LENGTH_LONG).show();

    }

}
