package org.nullpointer.client;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
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
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    Bundle detailsImported;
    String mailIdImportedString = "",nameImportedString = "",JSON_STRING = "";
    private RecyclerView mRecyclerView;
    private Adapter mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        detailsImported=getIntent().getExtras();
        mailIdImportedString=detailsImported.getString("mailID");
        nameImportedString=detailsImported.getString("name");
        getJSON();

        mRecyclerView = (RecyclerView)findViewById(R.id.event_list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        updateUI();

    }
    private	class	Holder	extends	RecyclerView.ViewHolder{
        private TextView mNameTextView;
        private TextView mTimeTextView;
        public	Holder(View itemView)	{
            super(itemView);
            mNameTextView = (TextView) itemView.findViewById(R.id.name);
            mTimeTextView = (TextView) itemView.findViewById(R.id.time);
        }
    }
    private	class Adapter extends RecyclerView.Adapter<Holder>	{
        private List<Event> mEvents;
        public	Adapter(List<Event> events)
        {
            mEvents = events;
        }
        @Override
        public	Holder	onCreateViewHolder(ViewGroup parent, int	viewType)	{
            LayoutInflater layoutInflater	=	LayoutInflater.from(getApplicationContext());
            View view = layoutInflater.inflate(R.layout.list_item, parent, false);
            return	new	Holder(view);
        }
        @Override
        public	void	onBindViewHolder(Holder holder, int position)	{
            Event eve	=	mEvents.get(position);
            holder.mNameTextView.setText(eve.getName());
            holder.mTimeTextView.setText(eve.getTime());
        }
        @Override
        public	int	getItemCount()	{
            return	mEvents.size();
        }
    }
    private	void	updateUI()	{
        List<Event> events=new GetEvents(getApplicationContext()).getEveList();
        mAdapter = new Adapter(events);
        mRecyclerView.setAdapter(mAdapter);
    }
    public class Event{
        String mName,mTime;

        public Event(String name, String time) {
            mName = name;
            mTime = time;
        }

        public String getName() {
            return mName;
        }

        public void setName(String name) {
            mName = name;
        }

        public String getTime() {
            return mTime;
        }

        public void setTime(String time) {
            mTime = time;
        }
    }
    void getJSON(){
        new BackgroundTask(this).execute(mailIdImportedString);
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
            getIntent().putExtra("exit",true);
            this.finish();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onResume(){
        super.onResume();
        reset();
    }
    void reset(){
        NavigationView navigationView = (NavigationView)findViewById(R.id.nav_view);
        navigationView.getMenu().getItem(0).setChecked(true);
    }
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.up) {

        } else if (id == R.id.appoint) {
            Intent i =new Intent(this,BookAppointmentActivity.class);
            startActivity(i);
        } else if (id == R.id.logout) {
            Intent i=new Intent(this,LoginActivity.class);
            LoginPreferences.setMail(getApplicationContext(),null);
            startActivity(i);
            this.finish();
         // Handle the camera action
        }
        else
        {
            Intent i=new Intent(getApplicationContext(),ClientMedicalHistory.class);
            i.putExtra("JSON_STRING",JSON_STRING);
            startActivity(i);
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public class BackgroundTask extends AsyncTask<String,Void,String> {
        String jsonGetMedicalHistoryURL="";
        Context context;

        BackgroundTask(Context context){
            this.context = context;
        }

        @Override
        protected void onPreExecute(){
            jsonGetMedicalHistoryURL = "http://172.16.20.45/jsonGetMedicalHistory.php";
        }


        @Override
        protected String doInBackground(String... params) {
            String CLIENT_MAIL_ID=params[0];

            try {
                URL url = new URL(jsonGetMedicalHistoryURL);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));

                String data = URLEncoder.encode("CLIENT_MEDICAL_HISTORY_MAIL_ID", "UTF-8") +"="+URLEncoder.encode(CLIENT_MAIL_ID,"UTF-8");

                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();

                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));
                StringBuilder stringBuilder = new StringBuilder();
                while ((JSON_STRING=bufferedReader.readLine())!=null){
                   // Log.i("fsgg",JSON_STRING);
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

        }
    }

}
