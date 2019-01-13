package com.example.raymond.counsellingapp;

import android.app.Activity;
import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class eventAdapter extends ArrayAdapter<Event> {

    private Image imgEvent;

    public eventAdapter(Activity context, int resource, List<Event> list) {
        super(context, resource, list);
    }


    public View getView(int position, View convertView, ViewGroup parent){
        Event event = getItem(position);
        LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.event_record, parent, false);

        TextView txtEvent, txtDate, txtFee;

        txtEvent = rowView.findViewById(R.id.txtEvent);
        txtDate = rowView.findViewById(R.id.txtDate);
        txtFee = rowView.findViewById(R.id.txtFee);

        txtEvent.setText(txtEvent.getText() + ":" + event.getEventName());
        txtDate.setText(txtDate.getText() + ":" + event.getEventDate());
        txtFee.setText(txtFee.getText() + ":" + event.getEventFee());

        return rowView;
    }
}
