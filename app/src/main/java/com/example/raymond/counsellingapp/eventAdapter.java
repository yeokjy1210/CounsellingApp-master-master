package com.example.raymond.counsellingapp;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class eventAdapter extends ArrayAdapter<Event> {

    private ImageView imgEvent;

    public eventAdapter(Activity context, int resource, List<Event> list) {
        super(context, resource, list);
    }


    public View getView(int position, View convertView, ViewGroup parent){
        Event event = getItem(position);
        LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.event_record, parent, false);

        TextView txtEvent, txtDate, txtFee;

        imgEvent = rowView.findViewById(R.id.imgEvent);
        txtEvent = rowView.findViewById(R.id.txtEvent);
        txtDate = rowView.findViewById(R.id.txtDate);
        txtFee = rowView.findViewById(R.id.txtFee);

        showImage(event.getEventImg());
        txtEvent.setText(txtEvent.getText() + ":" + event.getEventName());
        txtDate.setText(txtDate.getText() + ":" + event.getEventDate());
        txtFee.setText(txtFee.getText() + ":" + event.getEventFee());

        return rowView;
    }

    private void showImage(String image) {

        byte[] decodedString = Base64.decode(image, Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString,
                0, decodedString.length);

        if (decodedByte != null) {
            imgEvent.setImageBitmap(decodedByte);
        } else {
            imgEvent.setImageResource(R.drawable.hope_icon);
        }

    }
}
