package org.nullpointer.client;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Message;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
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

public class LoginActivity extends AppCompatActivity {
    TextInputEditText mailId, password;
    String mailIdString = "", passwordString = "";
    boolean isDataValid = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        TextView register = (TextView)findViewById(R.id.register);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),RegActivity.class);
                startActivity(i);
            }
        });
        mailId = (TextInputEditText) findViewById(R.id.mail_id_login);
        password = (TextInputEditText) findViewById(R.id.password_login);
    }
    private boolean validateDetails() {

        boolean validateData = true;
        // Reset errors.
        mailId.setError(null);
        password.setError(null);



        // Store values at the time of the login attempt.
        String mailIdString = mailId.getText().toString();
        String passwordString = password.getText().toString();

        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (TextUtils.isEmpty(passwordString)) {
            password.setError("This field is required");
            validateData = false;
            focusView = password;
        }        // Check for a valid Admission no, if the user entered one.
        if (TextUtils.isEmpty(mailIdString)) {
            mailId.setError("This field is required");
            validateData = false;
            focusView = mailId;
        }
        if (validateData==false) {
            focusView.requestFocus();
        }

        return validateData;
    }

    public void loginCheckAsPatient(View view){
        isDataValid = validateDetails();
        if (isDataValid == true) {
            mailIdString = mailId.getText().toString();
            passwordString = password.getText().toString();
            BackgroundTask backgroundTask = new BackgroundTask(this);
            backgroundTask.execute(mailIdString, passwordString);
        }
    }


    public class BackgroundTask extends AsyncTask<String,Void,String> {
        String loginAsClientURL;
        Context context;

        BackgroundTask(Context context){
            this.context = context;
        }

        @Override
        protected void onPreExecute(){
            loginAsClientURL = "http://172.16.20.45/login_as_client.php";
        }

        @Override
        protected String doInBackground(String... params) {
            String CLIENT_MAIL_ID=params[0];
            String CLIENT_PASSWORD=params[1];

            try {
                URL url = new URL(loginAsClientURL);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));

                String data = URLEncoder.encode("CLIENT_MAIL_ID", "UTF-8") +"="+URLEncoder.encode(CLIENT_MAIL_ID,"UTF-8")+"&"+
                        URLEncoder.encode("CLIENT_PASSWORD", "UTF-8") +"="+URLEncoder.encode(CLIENT_PASSWORD,"UTF-8");

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

            Boolean loginCheck = result.equals("Login Sucessful");
            if(!loginCheck)
            {
                org.nullpointer.client.Message.message(context,"INVALID LOGIN CREDENTIALS");
                mailId.setText("");
                password.setText("");
            }
            else
            {
                Intent intent = new Intent(context,MainActivity.class);
                intent.putExtra("mailID", mailIdString);
                intent.putExtra("name",result);
                mailId.setText("");
                password.setText("");
                mailId.requestFocus();
                startActivity(intent);
            }
        }
    }

}
