package com.example.pitti.event_planner;

/**
 * Created by pitti on 28/9/17.
 */

public class User {
    private String mail_id;
    private String password;
    private String user_name;
    private String phone_number;

    public void User(){

    }

    public void User(String mail_id, String password, String user_name, String phone_number){
        this.mail_id = mail_id;
        this.password = password;
        this.phone_number= phone_number;
        this.user_name = user_name;
    }

    public void set_user_details(String mail_id, String password, String user_name, String phone_number){
        this.mail_id = mail_id;
        this.password = password;
        this.phone_number= phone_number;
        this.user_name = user_name;
    }

    public String get_mail_id(){
        return mail_id;
    }

    public String get_Password(){
        return password;
    }

    public String get_user_name(){
        return user_name;
    }

    public String get_phone_number(){ return phone_number; }
}
