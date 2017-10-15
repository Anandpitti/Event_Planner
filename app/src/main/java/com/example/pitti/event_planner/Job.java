package com.example.pitti.event_planner;

import java.util.ArrayList;

/**
 * Created by pitti on 30/9/17.
 */

public class Job {

    private String parent_event;
    private String job_name;
    private String subject;
    private String description;
    private String start_time;
    private String end_time;
    private String type;
    private String place;
    private String start_point;
    private String destination;
    private String status;
    private ArrayList<String> people_involved;

    public Job(){

    }

    public void Job(
            String parent_event,
            String job_name,
            String subject,
            String description,
            String start_time,
            String end_time,
            String type,
            String place,
            String start_point,
            String destination,
            String status,
            ArrayList<String> people_involved
    ){
        this.parent_event = parent_event;
        this.job_name = job_name;
        this.subject = subject;
        this.description = description;
        this.start_time = start_time;
        this.end_time = end_time;
        this.type = type;
        this.place = place;
        this.start_point = start_point;
        this.destination = destination;
        this.status = status;
        this.people_involved = people_involved;
    }

    public void set_job_details(
            String parent_event,
            String job_name,
            String subject,
            String description,
            String start_time,
            String end_time,
            String type,
            String place,
            String start_point,
            String destination,
            String status,
            ArrayList<String> people_involved
    ){
        this.parent_event = parent_event;
        this.job_name = job_name;
        this.subject = subject;
        this.description = description;
        this.start_time = start_time;
        this.end_time = end_time;
        this.type = type;
        this.place = place;
        this.start_point = start_point;
        this.destination = destination;
        this.status = status;
        this.people_involved = people_involved;
    }


    public void set_parent_event(String parent_event){
        this.parent_event = parent_event;
    }

    public void set_job_name(String jobname){
        this.job_name = jobname;
    }

    public void set_subject(String subject){
        this.subject = subject;
    }

    public void set_description(String description){
        this.description = description;
    }

    public void set_start_time(String start_time){
        this.start_time = start_time;
    }

    public void set_end_time(String end_time){
        this.end_time = end_time ;
    }

    public void set_type(String type){
        this.type = type;
    }

    public void set_place(String place){
        this.place = place;
    }

    public void set_start_point(String start_point){
        this.start_point = start_point;
    }

    public void set_destination(String destination){
        this.destination = destination;
    }

    public void set_status(String status){
        this.status = status;
    }

    public void set_people_involved(ArrayList<String> peoplelist){
        this.people_involved = peoplelist;
    }

    public String get_parent_event(){ return this.parent_event;}

    public String get_job_name(){
        return this.job_name;
    }

    public String get_subject(){
        return this.subject;
    }

    public String get_description(){
        return this.description;
    }

    public String get_start_time(){
        return this.start_time;
    }

    public String get_end_time(){
        return this.end_time;
    }

    public String get_type(){
        return this.type;
    }

    public String get_place(){
        return this.place;
    }

    public String get_start_point(){
        return this.start_point;
    }

    public String get_destination(){
        return this.destination;
    }

    public String get_status(){
        return this.status;
    }

    public ArrayList<String > get_people_involved(){
        return this.people_involved;
    }

    public Integer get_count(){
        return this.people_involved.size();
    }

    public String get_people_involved_string(ArrayList<String> a){
        String result = "";
        for(int i=0;i<a.size();i++){
            result += a.get(i);
            result += '\n';
        }
        return result;
    }
}
