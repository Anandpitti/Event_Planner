package com.example.pitti.event_planner;

import java.util.Date;
import java.text.SimpleDateFormat;


/**
 * Created by pitti on 2/10/17.
 */

public class Formatchecker {

    public Formatchecker(){
    }

    public boolean is_valid_email_id(String mail_id){
        Integer pos_at = mail_id.indexOf('@');
        Integer pos_dot = mail_id.lastIndexOf('.');

        if(pos_at != -1 && pos_at < mail_id.length()){
            if(pos_dot != -1 && pos_dot <mail_id.length() && pos_dot > pos_at)
                return true;
        }

        return false;
    }

    public boolean is_valid_phone_number(String phone_number){
        if(phone_number.length() == 10){
            for(int i=0;i<10;i++){
                if(phone_number.charAt(i) < '0' || phone_number.charAt(i) > '9')
                    return false;
            }
            return  true;
        }

        return false;
    }

    public boolean is_valid_date_format(String value){
        //DD/MM/YY format
        String format = "dd/MM/yyyy";
        Date date = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            date = sdf.parse(value);
            if (!value.equals(sdf.format(date))) {
                date = null;
            }
        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }
        return date != null;
    }

    public boolean is_valid_time_format(String value){
        //HH:MM AM/PM format
        String format = "KK:mm aa";
        Date time = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            time = sdf.parse(value);
            if (!value.equals(sdf.format(time))) {
                time = null;
            }
        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }
        return time != null;

    }

}
