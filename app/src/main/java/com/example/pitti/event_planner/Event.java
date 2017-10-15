package com.example.pitti.event_planner;

/**
 * Created by pitti on 29/9/17.
 */

public class Event {
    private String event_name;
    private String start_date;
    private String start_time;
    private String end_time;

    public void Event(){

    }

    public void Event(String name, String date, String start_time, String end_time){
        this.event_name = name;
        this.start_date = date;
        this.start_time = start_time;
        this.end_time = end_time;
    }

    public void set_details(String name, String date, String start_time, String end_time){
        this.event_name = name;
        this.start_date = date;
        this.start_time = start_time;
        this.end_time = end_time;
    }

    public String get_event_name(){
        return this.event_name;
    }

    public String get_start_date(){
        return this.start_date;
    }

    public String get_start_time(){
        return this.start_time;
    }

    public String get_end_time(){
        return this.end_time;
    }
}
