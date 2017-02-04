package org.nullpointer.doctor;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

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

/**
 * Created by Deepanshu on 04-Feb-17.
 */

public class get_Appointment {
    String JSON_STRING;
    Context context;

    get_Appointment(Context context, String userId) {
        this.context = context;

        BackgroundTask bt = new BackgroundTask(context);
        bt.execute(userId);

    }

    public void goToAppointmentScreen() {

        if(JSON_STRING==null){
            Message.message(context, "Appointment not loaded successfully... ");
        }
        else if (JSON_STRING == "") {
            Message.message(context, "No Appointment Available");
        } else {
            Intent intent = new Intent(context, AppointmentScreen.class);
            intent.putExtra("JSON_STRING", JSON_STRING);
            context.startActivity(intent);
        }

    }

    public class BackgroundTask extends AsyncTask<String, Void, String> {
        String jsonGetMedicalHistoryURL;
        Context context;

        BackgroundTask(Context context) {
            this.context = context;
        }

        @Override
        protected void onPreExecute() {
            jsonGetMedicalHistoryURL = "http://172.16.20.45/jsonGetAppointment.php";
        }

        @Override
        protected String doInBackground(String... params) {
            String DOC_USER_ID = params[0];

            try {
                URL url = new URL(jsonGetMedicalHistoryURL);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));

                String data = URLEncoder.encode("DOC_USER_ID", "UTF-8") + "=" + URLEncoder.encode(DOC_USER_ID, "UTF-8");

                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();

                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
                StringBuilder stringBuilder = new StringBuilder();
                while ((JSON_STRING = bufferedReader.readLine()) != null) {

                    stringBuilder.append(JSON_STRING + "\n");
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();

                return stringBuilder.toString().trim();

                ////////////////////////////////////////
//                String response = "";
//                String line = "";
//                while ((line=bufferedReader.readLine())!=null){
//                    response += line;
//                }
//                bufferedReader.close();
//                inputStream.close();
//                httpURLConnection.disconnect();
//                return response.trim();
                ///////////////////////////////////////

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Void... avoid) {
            super.onProgressUpdate(avoid);
        }

        @Override
        protected void onPostExecute(String result) {
            JSON_STRING = result;
        }
    }
}
