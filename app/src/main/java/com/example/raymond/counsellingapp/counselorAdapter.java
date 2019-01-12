package com.example.raymond.counsellingapp;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;
public class counselorAdapter extends ArrayAdapter<Counselor>{

    private Image imgCounselor;
    public counselorAdapter(Activity context, int resource, List<Counselor> list){
        super(context, resource, list);
    }

    @SuppressLint("SetTextI18n")
    public View getView(int position, View convertView, ViewGroup parent){
        Counselor counselor = getItem(position);
        LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        @SuppressLint("ViewHolder") View rowView = inflater.inflate(R.layout.counselor_record, parent, false);

        TextView txtCounselorName, txtCounselorDOB, txtCounselorType,txtCounselorDesc,txtCounselorContact,txtCounselorEmail,txtCounselorVenue;

        txtCounselorName = rowView.findViewById(R.id.txtCounselorName);
        txtCounselorDOB = rowView.findViewById(R.id.txtCounselorDOB);
        txtCounselorType = rowView.findViewById(R.id.txtCounselorType);
        txtCounselorDesc = rowView.findViewById(R.id.txtCounselorDesc);
        txtCounselorContact = rowView.findViewById(R.id.txtCounselorContact);
        txtCounselorEmail = rowView.findViewById(R.id.txtCounselorEmail);
        txtCounselorVenue = rowView.findViewById(R.id.txtCounselorVenue);

        txtCounselorName.setText(txtCounselorName.getText() + ":" + counselor.getCounselorName());
        txtCounselorDOB.setText(txtCounselorDOB.getText() + ":" + counselor.getCounselorDOB());
        txtCounselorType.setText(txtCounselorType.getText() + ":" + counselor.getCounselorType());
        txtCounselorDesc.setText(txtCounselorDesc.getText() + ":" + counselor.getCounselorDesc());
        txtCounselorContact.setText(txtCounselorContact.getText() + ":" + counselor.getCounselorContact());
        txtCounselorEmail.setText(txtCounselorEmail.getText() + ":" + counselor.getCounselorEmail());
        txtCounselorVenue.setText(txtCounselorVenue.getText() + ":" + counselor.getCounselorVenue());


        return rowView;
    }
}
