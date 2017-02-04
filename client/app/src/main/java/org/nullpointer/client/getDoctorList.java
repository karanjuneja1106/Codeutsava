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
import java.util.List;

/**
 * Created by karan1106 on 4/2/17.
 */

public class getDoctorList {
    private String JSON_STRING="";
    private static ArrayList<Doctor> docList;
    public getDoctorList() {
        if(docList==null) {
            docList = new ArrayList<Doctor>();
            new BackgroundTask().execute();
        }
    }
    public class BackgroundTask extends AsyncTask<Void,Void,String> {
        String jsonGetDoctorsURL="";


        @Override
        protected void onPreExecute(){
            jsonGetDoctorsURL = "http://172.16.20.45/jsonGetDoctors.php";
        }

        @Override
        protected String doInBackground(Void...params) {

            try {
                URL url = new URL(jsonGetDoctorsURL);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);

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
                JSONArray jsonArray = jsonObject.getJSONArray("doctors_From_Server");
                String name,contact;
                int count = 0;
                while(count<jsonArray.length()){
                    JSONObject JO = jsonArray.getJSONObject(count);
                    count++;
                    name = JO.getString("Name");
                    contact = JO.getString("Contact");
                    Doctor doc = new Doctor(name,contact);
                    docList.add(doc);
                }
            }
            catch (JSONException e){
                e.printStackTrace();
            }
        }
    }

    public ArrayList<Doctor> getDocList() {
        return docList;
    }
}
