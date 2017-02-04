package org.nullpointer.client;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;

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

public class RegActivity extends AppCompatActivity {
    TextInputEditText name,email,password,contact,week,city,state;
    boolean isDataValid = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        name = (TextInputEditText) findViewById(R.id.name);
        email = (TextInputEditText) findViewById(R.id.mail);
        password = (TextInputEditText) findViewById(R.id.password);
        contact = (TextInputEditText) findViewById(R.id.contact);
        week = (TextInputEditText) findViewById(R.id.weeks);
        city = (TextInputEditText) findViewById(R.id.city);
        state = (TextInputEditText) findViewById(R.id.state);
    }

    public void registerAsPatient(View view){

        //isDataValid = validateDetails();
        if (isDataValid == true) {
            String nameString = name.getText().toString();
            String emailString = email.getText().toString();
            String passwordString = password.getText().toString();
            String contactString = contact.getText().toString();
            String weekString = week.getText().toString();
            String cityString = city.getText().toString();
            String stateString = state.getText().toString();


            BackgroundTask backgroundTask = new BackgroundTask(this);
            backgroundTask.execute(nameString, emailString, passwordString, contactString, weekString, cityString, stateString);
            finish();
        }
    }

    public class BackgroundTask extends AsyncTask<String,Void,String> {
        String registerAsPatientURL;
        Context context;

        BackgroundTask(Context context){
            this.context = context;
        }

        @Override
        protected void onPreExecute(){
            registerAsPatientURL = "http://http://172.16.20.45/new_registeration.php";
        }

        @Override
        protected String doInBackground(String... params) {
            String CLIENT_NAME=params[0];
            String CLIENT_MAIL_ID=params[1];
            String CLIENT_PASSWORD=params[2];
            String CLIENT_CONTACT=params[3];
            String CLIENT_WEEKS=params[4];
            String CLIENT_CITY=params[5];
            String CLIENT_STATE=params[6];
            try {
                URL url = new URL(registerAsPatientURL);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String data = null;
                data = URLEncoder.encode("CLIENT_NAME", "UTF-8") +"="+URLEncoder.encode(CLIENT_NAME,"UTF-8")+"&"+
                        URLEncoder.encode("CLIENT_MAIL_ID","UTF-8") +"="+URLEncoder.encode(CLIENT_MAIL_ID,"UTF-8")+"&"+
                        URLEncoder.encode("CLIENT_PASSWORD", "UTF-8") +"="+URLEncoder.encode(CLIENT_PASSWORD,"UTF-8")+"&"+
                        URLEncoder.encode("CLIENT_CONTACT","UTF-8") +"="+URLEncoder.encode(CLIENT_CONTACT,"UTF-8")+"&"+
                        URLEncoder.encode("CLIENT_WEEKS", "UTF-8") +"="+URLEncoder.encode(CLIENT_WEEKS,"UTF-8")+"&"+
                        URLEncoder.encode("CLIENT_CITY","UTF-8") +"="+URLEncoder.encode(CLIENT_CITY,"UTF-8")+"&"+
                        URLEncoder.encode("CLIENT_STATE", "UTF-8") +"="+URLEncoder.encode(CLIENT_STATE,"UTF-8");


                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));
                String response = "";
                response = bufferedReader.readLine();
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return response;

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return "Not Registered!!!";
        }

        @Override
        protected void onProgressUpdate(Void... avoid){
            super.onProgressUpdate(avoid);
        }

        @Override
        protected void onPostExecute(String result){
            Message.message(context,result);
        }
    }
}
