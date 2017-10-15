package com.example.pitti.event_planner;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * Created by pitti on 29/9/17.
 */

public class job_list_Adapter extends ArrayAdapter<String> {

    public job_list_Adapter(Context context, String[] jobs) {
        super(context, R.layout.job_single ,jobs);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater myCustomInflater = LayoutInflater.from(getContext());
        View customView = myCustomInflater.inflate(R.layout.job_single, parent, false);

        String single_job_name = getItem(position);
        TextView job_name = (TextView) customView.findViewById(R.id.job_name);
        TextView job_start_time = (TextView) customView.findViewById(R.id.start_time);
        TextView people_count = (TextView) customView.findViewById(R.id.people_count);
        TextView job_status_bar = (TextView) customView.findViewById(R.id.job_status_display);

        Db_helper db = new Db_helper(getContext(), null, null, 1);
        Job job = new Job();
        job = db.get_job_details(single_job_name);

        job_name.setText(job.get_job_name());
        job_start_time.setText(job.get_start_time());
        people_count.setText(job.get_count().toString());


        if(job.get_status().equals(""))
            job_status_bar.setBackgroundResource(R.color.job_initial);
        else if(job.get_status().equals("done"))
            job_status_bar.setBackgroundResource(R.color.job_done);
        else if(job.get_status().equals("delay"))
            job_status_bar.setBackgroundResource(R.color.job_delay);
        else if(job.get_status().equals("not_done"))
            job_status_bar.setBackgroundResource(R.color.job_not_done);

        return customView;
    }
}
