package org.nullpointer.doctor;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class AppointmentScreen extends AppCompatActivity {
    String JSON_STRING = "";

    JSONObject jsonObject;
    JSONArray jsonArray;
    AppointmentAdapter appointmentAdapter;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment_screen);

        appointmentAdapter = new AppointmentAdapter(this,R.layout.layout_appointment);
        listView = (ListView) findViewById(R.id.listViewAppointment);
        listView.setAdapter(appointmentAdapter);

        JSON_STRING = getIntent().getExtras().getString("JSON_STRING");

        try {
            jsonObject = new JSONObject(JSON_STRING);
            jsonArray = jsonObject.getJSONArray("Appointment_From_Server");
            int count = 0;
            String name, mail_id, contact;

            while(count<jsonArray.length()){
                JSONObject JO = jsonArray.getJSONObject(count);
                name = JO.getString("NAME");
                mail_id = JO.getString("MAIL_ID");
                contact = JO.getString("CONTACT");

                Appointment appointment = new Appointment(name, mail_id, contact);
                appointmentAdapter.add(appointment);
                count++;

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                Appointment entry = (Appointment) parent.getItemAtPosition(position);
                Intent intent = new Intent(getApplicationContext(), SetAppointmentTime.class);
                intent.putExtra("Name", entry.getName());
                intent.putExtra("Mail_Id", entry.getMail_id());
                intent.putExtra("Contact", entry.getContact());
                startActivity(intent);

            }
        });

    }
}
