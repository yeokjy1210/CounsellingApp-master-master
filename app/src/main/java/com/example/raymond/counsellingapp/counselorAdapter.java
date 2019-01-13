package com.example.raymond.counsellingapp;


import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class counselorAdapter extends ArrayAdapter<Counselor> {

    private ImageView imgCounselor;

    public counselorAdapter(Activity context, int resource, List<Counselor> list) {
        super(context, resource, list);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        Counselor counselor = getItem(position);
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.counselor_record, parent, false);

        TextView txtName, txtAge, txtYear, txtVenue, txtDesc;

        imgCounselor = rowView.findViewById(R.id.imgCounselor);
        txtName = rowView.findViewById(R.id.txtName);
        txtAge = rowView.findViewById(R.id.txtAge);
        txtYear = rowView.findViewById(R.id.txtYear);
        txtVenue = rowView.findViewById(R.id.txtVenue);
        txtDesc = rowView.findViewById(R.id.txtDesc);

        showImage(counselor.getCounselorImg());
        txtName.setText(counselor.getCounselorName());
        txtAge.setText(counselor.getCounselorAge() + "");
        txtYear.setText(counselor.getCounselorExp()+"");
        txtVenue.setText(counselor.getCounselorVenue());
        txtDesc.setText(counselor.getCounselorType());

        return rowView;
    }

    private void showImage(String image) {

        byte[] decodedString = Base64.decode(image, Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString,
                0, decodedString.length);

        if (decodedByte != null) {
            imgCounselor.setImageBitmap(decodedByte);
        } else {
            imgCounselor.setImageResource(R.drawable.hope_icon);
        }

    }
}
