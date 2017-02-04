package org.nullpointer.client;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Toast;

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
import java.util.Date;

public class CnfAppointmentActivity extends AppCompatActivity {
    Button book;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cnf_appointment);
        DatePicker date_picker = (DatePicker) findViewById(R.id.date_picker);
        date_picker.setMinDate(System.currentTimeMillis() - 1000);
        int month=date_picker.getMonth();
        int year=date_picker.getYear();
        int dayOfMonth=date_picker.getDayOfMonth();
        final String fd=String.format("%02d",dayOfMonth)+"/"+String.format("%02d",month+1)+"/"+year;
        book=(Button) findViewById(R.id.book_appointment);
        final String uid=getIntent().getExtras().getString("UID");
        book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               new BackgroundTask(getApplicationContext()).execute(uid,fd);

            }
        });

    }
    public class BackgroundTask extends AsyncTask<String,Void,String> {
        String loginAsClientURL;
        Context context;

        BackgroundTask(Context context){
            this.context = context;
        }

        @Override
        protected void onPreExecute(){
            loginAsClientURL = "http://172.16.20.45/setAppointment.php";
        }

        @Override
        protected String doInBackground(String... params) {
            String DOC_ID=params[0];
            String PREFER_DATE=params[1];
            String CLIENT_MAIL=LoginPreferences.getStoredMail(getApplicationContext());

            try {
                URL url = new URL(loginAsClientURL);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));

                String data = URLEncoder.encode("DOC_ID", "UTF-8") +"="+URLEncoder.encode(DOC_ID,"UTF-8")+"&"+
                        URLEncoder.encode("CLIENT_MAIL", "UTF-8") +"="+URLEncoder.encode(CLIENT_MAIL,"UTF-8")+"&"+
                        URLEncoder.encode("PREFER_DATE", "UTF-8") +"="+URLEncoder.encode(PREFER_DATE,"UTF-8");

                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();

                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));
                String response = "";
                String line = "";
                while ((line=bufferedReader.readLine())!=null){
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
        protected void onProgressUpdate(Void... avoid){
            super.onProgressUpdate(avoid);
        }

        @Override
        protected void onPostExecute(String result){
            if(result.equals("New record created successfully")){
                Toast.makeText(getApplicationContext(),result,Toast.LENGTH_LONG).show();
            }
            else{
                Toast.makeText(getApplicationContext(),"Error",Toast.LENGTH_LONG).show();
            }

        }
    }

}
