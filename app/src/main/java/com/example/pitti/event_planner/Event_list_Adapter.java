package com.example.pitti.event_planner;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import static java.security.AccessController.getContext;

/**
 * Created by pitti on 29/9/17.
 */

public class Event_list_Adapter extends ArrayAdapter<String> {

    public Event_list_Adapter(Context context, String[] events) {
        super(context, R.layout.home_event_single ,events);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater myCustomInflater = LayoutInflater.from(getContext());
        View customView = myCustomInflater.inflate(R.layout.home_event_single, parent, false);

        String single_event_name = getItem(position);
        TextView event_name = (TextView) customView.findViewById(R.id.event_name);
        TextView event_start_date = (TextView) customView.findViewById(R.id.start_date);

        Db_helper db = new Db_helper(getContext(), null, null, 1);
        Event event = new Event();
        event = db.get_event_details(single_event_name);

        event_name.setText(event.get_event_name());
        event_start_date.setText(event.get_start_date());

        return customView;
    }
}
