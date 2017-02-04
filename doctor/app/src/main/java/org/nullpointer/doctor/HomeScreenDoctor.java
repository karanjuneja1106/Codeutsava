package org.nullpointer.doctor;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

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

public class HomeScreenDoctor extends AppCompatActivity {

    Bundle detailsImported;
    String userIdImportedString = "", mail_id_client_String = "",doctorNameImportedString = "";
    TextInputEditText mail_id_client;
    TextView welcomeDoc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen_doctor);

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        detailsImported = getIntent().getExtras();
        userIdImportedString = detailsImported.getString("DOCTOR_ID");
        doctorNameImportedString = detailsImported.getString("DOCTOR_NAME");

        mail_id_client = (TextInputEditText) findViewById(R.id.mail_id_home_screen_doctor);
        welcomeDoc = (TextView) findViewById(R.id.welocome_doc);
        welcomeDoc.setText("Welcome,    Mr. " + doctorNameImportedString);
    }

    public void logout(View view){
        Intent intent = new Intent(this,loginAsDoctor.class);
        startActivity(intent);
    }

    public void checkIfMailIdExist(View view){
        mail_id_client_String = mail_id_client.getText().toString();
        BackgroundTask backgroundTask = new BackgroundTask(this);
        backgroundTask.execute(mail_id_client_String);

    }

    public class BackgroundTask extends AsyncTask<String,Void,String> {
        String checkIfMailIdExistURL;
        Context context;

        BackgroundTask(Context context){
            this.context = context;
        }

        @Override
        protected void onPreExecute(){
            checkIfMailIdExistURL = "http://172.16.20.45/checkIfClientExists.php";
        }

        @Override
        protected String doInBackground(String... params) {
            String CLIENT_MAIL_ID=params[0];

            try {
                URL url = new URL(checkIfMailIdExistURL);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));

                String data = URLEncoder.encode("CLIENT_MAIL_ID", "UTF-8") +"="+URLEncoder.encode(CLIENT_MAIL_ID,"UTF-8");

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

            Boolean loginCheck = result.equals("CLIENT EXIST");
            if(loginCheck)
            {
                Intent intent = new Intent(context,diagnosisScreen.class);
                intent.putExtra("MAIL_ID_CLIENT", mail_id_client_String);
                intent.putExtra("DOCTOR_ID",userIdImportedString);
                intent.putExtra("DOCTOR_NAME",doctorNameImportedString);
                mail_id_client.setText("");
                startActivity(intent);
            }
            else
            {
                Message.message(context, result);
                mail_id_client.setText("");
            }
        }
    }
}