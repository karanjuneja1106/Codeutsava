package org.nullpointer.doctor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Deepanshu on 04-Feb-17.
 */

public class AppointmentAdapter extends ArrayAdapter {
    List list = new ArrayList();

    public AppointmentAdapter(Context context, int resource) {
        super(context, resource);
    }

    public void add(Appointment object) {
        super.add(object);
        list.add(object);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row;
        row = convertView;
        AppointmentAdapter.AppointmentHolder appointmentHolder;
        if(row == null)
        {
            LayoutInflater layoutInflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = layoutInflater.inflate(R.layout.layout_appointment,parent,false);
            appointmentHolder = new AppointmentHolder();
            appointmentHolder.showName = (TextView) row.findViewById(R.id.name);
            appointmentHolder.showMailId = (TextView) row.findViewById(R.id.mail_id);
            appointmentHolder.showContact = (TextView) row.findViewById(R.id.contact);
            row.setTag(appointmentHolder);


        }
        else
        {
            appointmentHolder = (AppointmentAdapter.AppointmentHolder) row.getTag();
        }

        Appointment appointment = (Appointment) getItem(position);
        appointmentHolder.showMailId.setText(appointment.getMail_id());
        appointmentHolder.showName.setText(appointment.getName());
        appointmentHolder.showContact.setText(appointment.getContact());

        return row;
    }

    static class AppointmentHolder
    {
        TextView showMailId, showName, showContact;
    }
}
