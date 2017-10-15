package com.example.pitti.event_planner;

/**
 * Created by pitti on 28/9/17.
 */

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;


public class Start_up extends AppCompatActivity{

    private static final String TAG = "com.example.pitti.event_planner";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Db_helper db = new Db_helper(this, null, null, 1);

        if(db.is_active_user_persent()){
            Intent i = new Intent(this,Home_page.class);
            startActivity(i);
        }else{
            Intent i = new Intent(this,Login_page.class);
            startActivity(i);
        }

    }

}
