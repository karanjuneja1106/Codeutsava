package org.nullpointer.client;

import android.content.Context;
import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
import java.util.ArrayList;

/**
 * Created by karan1106 on 5/2/17.
 */

public class GetEvents {
    private String JSON_STRING="";
    private Context mContext;
    private ArrayList<MainActivity.Event> eventList;
    public GetEvents(Context context) {

            mContext=context;
            eventList = new ArrayList<MainActivity.Event>();
            new BackgroundTask().execute();

    }
    public class BackgroundTask extends AsyncTask<Void,Void,String> {
        String jsonGetDoctorsURL="";
        @Override
        protected void onPreExecute(){
            jsonGetDoctorsURL = "http://172.16.20.45/getPersonalApp.php";
        }

        @Override
        protected String doInBackground(Void...params) {

            try {
                URL url = new URL(jsonGetDoctorsURL);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));

                String data = URLEncoder.encode("USER_ID", "UTF-8") +"="+URLEncoder.encode(LoginPreferences.getStoredMail(mContext),"UTF-8");

                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();

                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));
                StringBuilder stringBuilder = new StringBuilder();
                while ((JSON_STRING=bufferedReader.readLine())!=null){
                    stringBuilder.append(JSON_STRING+"\n");
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return stringBuilder.toString().trim();

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return "";
        }

        @Override
        protected void onProgressUpdate(Void... avoid){
            super.onProgressUpdate(avoid);
        }

        @Override
        protected void onPostExecute(String result){
            JSON_STRING = result;
            try{
                JSONObject jsonObject = new JSONObject(JSON_STRING);
                JSONArray jsonArray = jsonObject.getJSONArray("Appointment_From_Server");
                String name,date;
                int count = 0;
                while(count<jsonArray.length()){
                    JSONObject JO = jsonArray.getJSONObject(count);
                    count++;
                    name = JO.getString("NAME");
                    date = JO.getString("TIME");
                    MainActivity.Event eve = new MainActivity().new Event(name,date);
                    eventList.add(eve);
                }
            }
            catch (JSONException e){
                e.printStackTrace();
            }
        }
    }

    public ArrayList<MainActivity.Event> getEveList() {
        return eventList;
    }
}
