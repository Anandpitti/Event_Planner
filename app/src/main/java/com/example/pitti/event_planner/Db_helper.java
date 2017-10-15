package com.example.pitti.event_planner;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.Cursor;
import android.content.Context;
import android.content.ContentValues;

import java.util.ArrayList;

public class Db_helper extends SQLiteOpenHelper {

    //details for the database
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "EventPlanner.db";

    //details for the active user
    public static final String TABLE_ACTIVE_USER = "active_user";
    public static final String COLUMN_ACTIVE_USER_MAILID = "user_mail_id";

    //details for all users
    public static final String TABLE_ALL_USERS = "all_users";
    public static final String COLUMN_USER_MAILID = "user_mail_id";
    public static final String COLUMN_PASSWORD = "password";
    public static final String COLUMN_USER_NAME = "user_name";
    public static final String COLUMN_PHONE_NUMBER = "user_ph_no";

    //details for events
    public static final String TABLE_EVENTS = "events";
    public static final String COLUMN_EVENT_NAME = "event_name";
    public static final String COLUMN_START_DATE = "start_date";
    public static final String COLUMN_START_TIME = "start_time";
    public static final String COLUMN_END_TIME = "end_time";

    //details for user_event mapping
    public static final String TABLE_USER_EVENTS = "user_events";
    //use the variables COLUMN_USER_MAILID and COLUMN_EVENT_NAME as same

    public Db_helper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //create active user table
        String query = "CREATE TABLE " + TABLE_ACTIVE_USER + " ("
                + COLUMN_ACTIVE_USER_MAILID + " TEXT PRIMARY KEY);";
        db.execSQL(query);

        //create all users table
        query = "CREATE TABLE " + TABLE_ALL_USERS + " ("
            + COLUMN_USER_MAILID + " TEXT PRIMARY KEY, "
            + COLUMN_PASSWORD + " TEXT, "
            + COLUMN_USER_NAME + " TEXT, "
            + COLUMN_PHONE_NUMBER + " TEXT);";
        db.execSQL(query);

        //create user_events table
        query = "CREATE TABLE " + TABLE_USER_EVENTS + " ("
        + COLUMN_USER_MAILID +" TEXT, "
          + COLUMN_EVENT_NAME + " TEXT);";
        db.execSQL(query);

        //create events table
        query = "CREATE TABLE " + TABLE_EVENTS + " ("
                + COLUMN_EVENT_NAME + " TEXT PRIMARY KEY, "
                + COLUMN_START_DATE + " TEXT, "
                + COLUMN_START_TIME + " TEXT, "
                + COLUMN_END_TIME + " TEXT);";
        db.execSQL(query);

        query = "CREATE TABLE user_jobs (user_mail_id TEXT, job_name TEXT);";
        db.execSQL(query);

        query = "CREATE TABLE job_events (job_name TEXT, event_name TEXT);";
        db.execSQL(query);

        query = "CREATE TABLE jobs (parent_event TEXT, job_name TEXT PRIMARY KEY, subject TEXT, " +
                "description TEXT, start_time TEXT, end_time TEXT, type TEXT, place TEXT, start_point TEXT, " +
                "destination TEXT, status TEXT)";
        db.execSQL(query);

        query = "CREATE TABLE notifications (user_mail_id TEXT, job_name TEXT, time TEXT)";
        db.execSQL(query);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        //delete active users table
        String query = "DROP TABLE IF EXISTS " + TABLE_ACTIVE_USER + ";";
        db.execSQL(query);

        //delete all users table
        query = "DROP TABLE IF EXISTS " + TABLE_ALL_USERS + ";";;
        db.execSQL(query);

        onCreate(db);
    }

    public void register_user(User user){
        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_MAILID,user.get_mail_id());
        values.put(COLUMN_PASSWORD,user.get_Password());
        values.put(COLUMN_USER_NAME,user.get_user_name());
        values.put(COLUMN_PHONE_NUMBER,user.get_phone_number());

        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE_ALL_USERS, null, values);
        db.close();
    }

    public boolean is_user_valid(String mail_id){
        String query = "";

        query = "SELECT * FROM " + TABLE_ALL_USERS + " WHERE " + COLUMN_USER_MAILID + "=\"" + mail_id + "\";";
        SQLiteDatabase db1 = getWritableDatabase();
        Cursor c = db1.rawQuery(query,null);
        c.moveToFirst();

        if(c.getCount() == 1){
            c.close();
            db1.close();
            return true;
        }else{
            c.close();
            db1.close();
            return false;
        }
    }

    public boolean login_check(String mail_id, String password){
        SQLiteDatabase db = getWritableDatabase();
        String query = "";

        query = "SELECT * FROM " + TABLE_ALL_USERS + " WHERE " + COLUMN_USER_MAILID + "=\"" + mail_id + "\";";
        Cursor c = db.rawQuery(query,null);
        c.moveToFirst();

        if(c.getString(c.getColumnIndex(COLUMN_PASSWORD)).equals(password)){
            c.close();
            db.close();
            return true;
        }
        else {
            c.close();
            db.close();
            return false;
        }
    }

    public User get_user(String mail_id){
        SQLiteDatabase db = getWritableDatabase();
        String query = "";

        query = "SELECT * FROM " + TABLE_ALL_USERS + " WHERE " + COLUMN_USER_MAILID + "=\"" + mail_id + "\";";
        Cursor c = db.rawQuery(query,null);
        c.moveToFirst();

        User user = new User();
        user.set_user_details(
                c.getString(c.getColumnIndex(COLUMN_USER_MAILID)),
                c.getString(c.getColumnIndex(COLUMN_PASSWORD)),
                c.getString(c.getColumnIndex(COLUMN_USER_NAME)),
                c.getString(c.getColumnIndex(COLUMN_PHONE_NUMBER))
        );
        c.close();
        db.close();

        return user;
    }

    public void delete_user(User user){
        SQLiteDatabase db = getWritableDatabase();
        String query;

        query = "DELETE * FROM " + TABLE_ALL_USERS + " WHERE " + COLUMN_USER_MAILID + "=\"" + user.get_mail_id() + "\";";
        db.execSQL(query);
        db.close();
    }

    public boolean is_active_user_persent(){
        SQLiteDatabase db = getWritableDatabase();
        String active_user = "";
        String query = "";

        query = "SELECT * FROM " + TABLE_ACTIVE_USER + " WHERE 1;";
        Cursor c = db.rawQuery(query,null);
        c.moveToFirst();

        if(c.getCount() == 1){
            c.close();
            db.close();
            return true;
        }
        else {
            c.close();
            db.close();
            return false;
        }
    }

    public void set_active_user(String mail_id){
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_ACTIVE_USER_MAILID, mail_id);

        db.insert(TABLE_ACTIVE_USER, null ,values);
        db.close();
    }

    public String get_active_user(){
        SQLiteDatabase db = getWritableDatabase();
        String active_user = "";
        String query = "";

        query = "SELECT * FROM " + TABLE_ACTIVE_USER + " WHERE 1;";
        Cursor c = db.rawQuery(query,null);
        c.moveToFirst();
        active_user = c.getString(c.getColumnIndex(COLUMN_ACTIVE_USER_MAILID));
        c.close();
        db.close();

        return active_user;
    }

    public void unset_active_user(){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_ACTIVE_USER + " WHERE 1;");
        db.close();
    }

    public void register_new_event(Event event){
        //add event in event table and event_user mapping also
        ContentValues values = new ContentValues();
        values.put(COLUMN_EVENT_NAME,event.get_event_name());
        values.put(COLUMN_START_DATE,event.get_start_date());
        values.put(COLUMN_START_TIME,event.get_start_time());
        values.put(COLUMN_END_TIME,event.get_end_time());

        ContentValues values_u_e = new ContentValues();
        values_u_e.put(COLUMN_USER_MAILID,get_active_user());
        values_u_e.put(COLUMN_EVENT_NAME,event.get_event_name());

        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE_EVENTS, null, values);
        db.insert(TABLE_USER_EVENTS,null,values_u_e);
        db.close();
    }

    public String[] get_events_for_user(String mail_id){
        SQLiteDatabase db = getWritableDatabase();
        String query = "";

        query = "SELECT * FROM " + TABLE_USER_EVENTS + " WHERE " + COLUMN_USER_MAILID + "=\"" + mail_id + "\";";
        Cursor c = db.rawQuery(query,null);
        c.moveToFirst();

        ArrayList<String> events_list = new ArrayList<String>();
        while(!c.isAfterLast()){
            if(c.getString(c.getColumnIndex(COLUMN_EVENT_NAME)) != null)
                events_list.add(c.getString(c.getColumnIndex(COLUMN_EVENT_NAME)));
            c.moveToNext();
        }

        String[] result_list = new String[events_list.size()];
        int i = 0;
        for(i=0; i<events_list.size(); i++)
            result_list[i] = events_list.get(i);

        db.close();
        return result_list;
    }

    public Event get_event_details(String event_name){
        SQLiteDatabase db = getWritableDatabase();
        String query = "";

        query = "SELECT * FROM " + TABLE_EVENTS + " WHERE " + COLUMN_EVENT_NAME + "=\"" + event_name + "\";";
        Cursor c = db.rawQuery(query,null);
        c.moveToFirst();

        Event event = new Event();
        event.set_details(
                c.getString(c.getColumnIndex(COLUMN_EVENT_NAME)),
                c.getString(c.getColumnIndex(COLUMN_START_DATE)),
                c.getString(c.getColumnIndex(COLUMN_START_TIME)),
                c.getString(c.getColumnIndex(COLUMN_END_TIME))
        );
        c.close();
        db.close();

        return event;
    }


    public String get_events_user_display(String event_name){
        SQLiteDatabase db = getWritableDatabase();
        String query = "";

        query = "SELECT * FROM " + TABLE_USER_EVENTS + " WHERE " + COLUMN_USER_MAILID + "=\"" + get_active_user() + "\" AND " +
                COLUMN_EVENT_NAME + "=\"" + event_name + "\";";
        Cursor c = db.rawQuery(query,null);
        c.moveToFirst();

        String result = "";
        result += c.getString(c.getColumnIndex(COLUMN_USER_MAILID));
        result += " ";
        result += c.getString(c.getColumnIndex(COLUMN_EVENT_NAME));
        result += '\n';
        return result;

    }

    public void delete_event(String event_name){
        SQLiteDatabase db = getWritableDatabase();
        String query = "";

        query = "DELETE FROM " + "events" + " WHERE " + "event_name" +"=\""+ event_name +"\";";
        db.execSQL(query);

        String active_user = get_active_user();

        query = "DELETE FROM " + "user_events" + " WHERE " + "user_mail_id" + "=\""+ active_user
                + "\" AND "+ "event_name" +"=\""+ event_name +"\";";
        db.execSQL(query);

        db.close();
    }

    public String[] get_jobs_events_for_user(String mail_id){
        SQLiteDatabase db = getWritableDatabase();
        String query = "";

        query = "SELECT * FROM user_jobs WHERE user_mail_id=\"" + mail_id + "\";";
        Cursor c = db.rawQuery(query,null);
        c.moveToFirst();

        ArrayList<String> job_list = new ArrayList<String>();

        while(!c.isAfterLast()){
            job_list.add(c.getString(c.getColumnIndex("job_name")));
            c.moveToNext();
        }
        c.close();

        ArrayList<String> event_list = new ArrayList<String>();
        for(int i=0;i<job_list.size();i++){
            String job_name = job_list.get(i);

            query = "SELECT * FROM jobs WHERE job_name=\"" + job_name + "\";";
            Cursor d = db.rawQuery(query, null);

            String event_name = d.getString(d.getColumnIndex("parent_event"));
            if(event_list.indexOf(event_name) == -1)
                event_list.add(event_name);
        }

        String[] result = new String[event_list.size()];
        for(int i=0;i<event_list.size();i++)
            result[i] = event_list.get(i);
        return result;

    }

    public String[] get_jobs_for_event(String event_name){
        SQLiteDatabase db = getWritableDatabase();
        String query = "";

        query = "SELECT * FROM job_events WHERE event_name=\"" + event_name + "\";";
        Cursor c = db.rawQuery(query,null);
        c.moveToFirst();

        ArrayList<String> job_list = new ArrayList<String>();

        while(!c.isAfterLast()){
            job_list.add(c.getString(c.getColumnIndex("job_name")));
            c.moveToNext();
        }

        String[] result = new String[job_list.size()];
        for(int i=0;i<job_list.size();i++)
            result[i] = job_list.get(i);
        return result;
    }

    public Job get_job_details(String job_name){
        SQLiteDatabase db = getWritableDatabase();
        String query = "";

        query = "SELECT * FROM jobs WHERE job_name=\"" + job_name + "\";";
        Cursor c = db.rawQuery(query,null);
        c.moveToFirst();

        query = "SELECT * FROM user_jobs WHERE job_name=\"" + job_name + "\";";
        Cursor d = db.rawQuery(query, null);
        d.moveToFirst();

        ArrayList<String> people = new ArrayList<String>();

        while(!d.isAfterLast()){
            people.add(d.getString(d.getColumnIndex("user_mail_id")));
            d.moveToNext();
        }

        //ArrayList<String> people = new ArrayList<String>();
        Job job = new Job();
        job.set_job_details(
                c.getString(c.getColumnIndex("parent_event")),
                c.getString(c.getColumnIndex("job_name")),
                c.getString(c.getColumnIndex("subject")),
                c.getString(c.getColumnIndex("description")),
                c.getString(c.getColumnIndex("start_time")),
                c.getString(c.getColumnIndex("end_time")),
                c.getString(c.getColumnIndex("type")),
                c.getString(c.getColumnIndex("place")),
                c.getString(c.getColumnIndex("start_point")),
                c.getString(c.getColumnIndex("destination")),
                c.getString(c.getColumnIndex("status")),
                people
        );

        c.close();
        d.close();
        db.close();
        return job;
    }

    public void register_new_job(Job new_job){
        ContentValues values1 = new ContentValues();
        ContentValues values2 = new ContentValues();

        //job_events table
        values1.put("job_name",new_job.get_job_name());
        values1.put("event_name",new_job.get_parent_event());

        //jobs table
        values2.put("parent_event",new_job.get_parent_event());
        values2.put("job_name",new_job.get_job_name());
        values2.put("subject",new_job.get_subject());
        values2.put("description",new_job.get_description());
        values2.put("start_time",new_job.get_start_time());
        values2.put("end_time",new_job.get_end_time());
        values2.put("type",new_job.get_type());
        values2.put("place",new_job.get_place());
        values2.put("start_point",new_job.get_start_point());
        values2.put("destination",new_job.get_destination());
        values2.put("status",new_job.get_status());

        Event parent_event = get_event_details(new_job.get_parent_event());

        SQLiteDatabase db = getWritableDatabase();
        db.insert("job_events",null,values1);
        db.insert("jobs",null,values2);

        //user_job table
        for(int i=0;i<new_job.get_people_involved().size();i++){
            ContentValues values3 = new ContentValues();
            ContentValues values4 = new ContentValues();

            //user_jobs table
            values3.put("user_mail_id",new_job.get_people_involved().get(i));
            values3.put("job_name",new_job.get_job_name());

            //notifictions table
            values4.put("user_mail_id",new_job.get_people_involved().get(i));
            values4.put("job_name",new_job.get_job_name());

            values4.put("time",parent_event.get_start_date() + " " + new_job.get_start_time());

            db.insert("user_jobs",null,values3);
            db.insert("notifications",null,values4);

        }

        db.close();
    }

    public ArrayList<String> getnotifications_for_user(String user_name){
        SQLiteDatabase db = getWritableDatabase();
        String query = "";

        query = "SELECT * FROM notifications WHERE 1;";
        Cursor c = db.rawQuery(query,null);
        c.moveToFirst();

        ArrayList<String> jobs_time  = new ArrayList<String>();
        while (!c.isAfterLast()){
            String result = "";
            if(c.getString(c.getColumnIndex("user_mail_id")).equals(user_name)) {
                result += c.getString(c.getColumnIndex("job_name"));
                result += " ";
                result += c.getString(c.getColumnIndex("time"));
                result += "\n";
                jobs_time.add(result);
            }
            c.moveToNext();
        }

        db.close();
        return jobs_time;
    }
    
    public void set_job_status(String job_name, String status){
        SQLiteDatabase db = getWritableDatabase();
        String query = "";

        query = "UPDATE jobs SET status=\"" + status + "\" WHERE job_name=\"" + job_name + "\"";
        db.execSQL(query);
        db.close();
    }

    public String display_jobs_table(){
        SQLiteDatabase db = getWritableDatabase();
        String query = "";

        query = "SELECT * FROM jobs WHERE 1;";
        Cursor c = db.rawQuery(query,null);
        c.moveToFirst();

        String result = "";
        while (!c.isAfterLast()){
            if(c.getString(c.getColumnIndex("job_name")) != null) {
                result += c.getString(c.getColumnIndex("job_name"));
                result += " ";
                result += c.getString(c.getColumnIndex("status"));
                result += "\n";

            }
            c.moveToNext();
        }

        db.close();
        return result;
    }

}

