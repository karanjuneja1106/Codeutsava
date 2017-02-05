package org.nullpointer.doctor;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TimePicker;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

public class SetAppointmentTime extends AppCompatActivity {
    TimePicker timePicker;
    Bundle detailsImported;
    String Name, Mail_Id, Contact, Date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_appointment_time);

        timePicker = (TimePicker) findViewById(R.id.timePicker);
        detailsImported = getIntent().getExtras();
        Name = detailsImported.getString("Name");
        Mail_Id = detailsImported.getString("Mail_Id");
        Contact = detailsImported.getString("Contact");
        Date = detailsImported.getString("Date");
    }

    public void setTime(View v){
        String appointmentTime=""+timePicker.getHour()+" : "+timePicker.getMinute();

        BackgroundTask backgroundTask = new BackgroundTask(this);
        backgroundTask.execute(Name, Contact, appointmentTime, Date);
    }

    public class BackgroundTask extends AsyncTask<String, Void, String> {
        String sendSMSURL;
        Context context;

        BackgroundTask(Context context) {
            this.context = context;
        }

        @Override
        protected void onPreExecute() {
            sendSMSURL = "http://172.16.20.45/sendSMS.php";
        }

        @Override
        protected String doInBackground(String... params) {
            String STRING_NAME = params[0];
            String STRING_CONTACT = params[1];
            String STRING_TIME = params[2];
            String STRING_DATE = params[3];

            try {
                URL url = new URL(sendSMSURL);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));

                String data = URLEncoder.encode("STRING_NAME", "UTF-8") + "=" + URLEncoder.encode(STRING_NAME, "UTF-8") + "&" +
                        URLEncoder.encode("STRING_CONTACT", "UTF-8") + "=" + URLEncoder.encode(STRING_CONTACT, "UTF-8") + "&" +
                        URLEncoder.encode("STRING_TIME", "UTF-8") + "=" + URLEncoder.encode(STRING_TIME, "UTF-8")+ "&" +
                        URLEncoder.encode("STRING_DATE", "UTF-8") + "=" + URLEncoder.encode(STRING_DATE, "UTF-8");

                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();

                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
                String response = "";
                String line = "";
                while ((line = bufferedReader.readLine()) != null) {
                    response += line;
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return response.trim();

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return "";
        }

        @Override
        protected void onProgressUpdate(Void... avoid) {
            super.onProgressUpdate(avoid);
        }

        @Override
        protected void onPostExecute(String result) {

            Message.message(getApplicationContext(),result);
        }
    }
}
