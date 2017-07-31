package plspray.infoservices.lue.plspray;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Calendar;

import plspray.infoservices.lue.plspray.utilities.SharedPreferenceClass;

public class SetReminderForGRoup extends AppCompatActivity implements View.OnClickListener{

    String msg_id="";
    String message="";
    String message_date="";
    String first_name="";
    String sender_id="";
    String chat_group_id="";
    String last_name="";
    String group_mobile="";
    TextView mondayTimeText,tuesdayTimeText,wednesdayTimeText,thursdayTimeText,fridayTimeText,saturdayTimeText,sundayTimeText;

    LinearLayout messageLayout;
    TextView senderName,datetimeText;
    Button acceptBtn,rejectBtn;
    JSONObject messageObj;
    CoordinatorLayout coordinatorLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_reminder_for_group);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        msg_id=getIntent().getStringExtra("msg_id");
        message=getIntent().getStringExtra("message");
        sender_id=getIntent().getStringExtra("sender_id");
        chat_group_id=getIntent().getStringExtra("chat_group_id");
        group_mobile=getIntent().getStringExtra("group_mobile");
        Log.d("gro00000","libiug"+message);

        messageLayout=(LinearLayout)findViewById(R.id.messageLayout);
        senderName=(TextView)findViewById(R.id.senderName);
        datetimeText=(TextView)findViewById(R.id.datetimeText);
        rejectBtn=(Button)findViewById(R.id.rejectBtn);
        acceptBtn=(Button)findViewById(R.id.acceptBtn);

        mondayTimeText=(TextView)findViewById(R.id.mondayTimeText);
        tuesdayTimeText=(TextView)findViewById(R.id.tuesdayTimeText);
        wednesdayTimeText=(TextView)findViewById(R.id.wednesdayTimeText);
        thursdayTimeText=(TextView)findViewById(R.id.thursdayTimeText);
        fridayTimeText=(TextView)findViewById(R.id.fridayTimeText);
        saturdayTimeText=(TextView)findViewById(R.id.saturdayTimeText);
        sundayTimeText=(TextView)findViewById(R.id.sundayTimeText);
        coordinatorLayout=(CoordinatorLayout)findViewById(R.id.coordinatorLayout);


        acceptBtn.setOnClickListener(this);
        rejectBtn.setOnClickListener(this);

        senderName.setText(first_name+" "+last_name);

        try {
            messageObj=new JSONObject(message);
            mondayTimeText.setText(messageObj.getString("Monday"));
            tuesdayTimeText.setText(messageObj.getString("Tuesday"));
            wednesdayTimeText.setText(messageObj.getString("Wednesday"));
            thursdayTimeText.setText(messageObj.getString("Thursday"));
            fridayTimeText.setText(messageObj.getString("Friday"));
            saturdayTimeText.setText(messageObj.getString("Saturday"));
            sundayTimeText.setText(messageObj.getString("Sunday"));



        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onClick(View v) {


        switch (v.getId())
        {
            case R.id.acceptBtn:
                SharedPreferenceClass.setSchedule(this,messageObj.toString());
                SetAlarmDate();
                new SendGroupUlta().execute();
                Log.d("schedule00","schedle11");
                Snackbar.make(coordinatorLayout,"Reminder Set Successfully",Snackbar.LENGTH_SHORT);
                SetReminderForGRoup.this.finish();
                break;
            case R.id.rejectBtn:
                finish();
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                super.onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void SetAlarmDate(){

        Context context = getApplicationContext();
        String mondayTime  = (SharedPreferenceClass.getSchedule(context).getMondayTime());
        String tuesdaytime  = (SharedPreferenceClass.getSchedule(context).getTuesDayTime());
        String wednesdaytime  = (SharedPreferenceClass.getSchedule(context).getWednesDayTime());
        String thrusdaytime  = (SharedPreferenceClass.getSchedule(context).getThursDayTime());
        String fridaytime  = (SharedPreferenceClass.getSchedule(context).getFriDayTime());
        String saturadytime  = (SharedPreferenceClass.getSchedule(context).getSaturDayTime());
        String sundaytime  = (SharedPreferenceClass.getSchedule(context).getSunDayTime());

        if(!(mondayTime.isEmpty())){

            String timeSplit[] = mondayTime.split(":");
            int hour  = Integer.parseInt(timeSplit[0]);
            int minute   = Integer.parseInt(timeSplit[1]);

            Intent intent1 = new Intent(getApplicationContext(),
                    AlertReceiver.class);

            PendingIntent pendingIntent1 = PendingIntent.getBroadcast(getApplicationContext(),
                    1,
                    intent1,
                    PendingIntent.FLAG_UPDATE_CURRENT);

            AlarmManager alarmManager1 = (AlarmManager)getSystemService(context.ALARM_SERVICE);

            java.util.Calendar calendar1 = java.util.Calendar.getInstance();

            calendar1.set(java.util.Calendar.DAY_OF_WEEK,
                    Calendar.MONDAY);
            calendar1.set(java.util.Calendar.HOUR_OF_DAY,
                    hour);
            calendar1.set(java.util.Calendar.MINUTE,
                    minute);
            calendar1.set(java.util.Calendar.SECOND,
                    0);

//            if(calendar1.before(Calendar.getInstance())) {
//                calendar1.add(Calendar.DATE, 1);
//            }

            if (Build.VERSION.SDK_INT >= 19) {

                alarmManager1.setExact(AlarmManager.RTC, calendar1.getTimeInMillis(), pendingIntent1);
            }else{
                alarmManager1.set(AlarmManager.RTC, calendar1.getTimeInMillis(), pendingIntent1);
            }

            Toast.makeText(this, "Notification Will Come at Time specified",
                    Toast.LENGTH_SHORT).show();

        }
        if(!(tuesdaytime.isEmpty())){

            String timeSplit[] = tuesdaytime.split(":");
            int hour  = Integer.parseInt(timeSplit[0]);
            int minute   = Integer.parseInt(timeSplit[1]);

            Intent intent1 = new Intent(getApplicationContext(),
                    AlertReceiver.class);

            PendingIntent pendingIntent1 = PendingIntent.getBroadcast(getApplicationContext(),
                    1,
                    intent1,
                    PendingIntent.FLAG_UPDATE_CURRENT);

            AlarmManager alarmManager1 = (AlarmManager)getSystemService(context.ALARM_SERVICE);

            java.util.Calendar calendar1 = java.util.Calendar.getInstance();

            calendar1.set(java.util.Calendar.DAY_OF_WEEK,
                    Calendar.TUESDAY);
            calendar1.set(java.util.Calendar.HOUR_OF_DAY,
                    hour);
            calendar1.set(java.util.Calendar.MINUTE,
                    minute);
            calendar1.set(java.util.Calendar.SECOND,
                    0);

            if(calendar1.before(Calendar.getInstance())) {
                calendar1.add(Calendar.DATE, 1);
            }

            if (Build.VERSION.SDK_INT >= 19) {

                alarmManager1.setExact(AlarmManager.RTC, calendar1.getTimeInMillis(), pendingIntent1);
            }else{
                alarmManager1.set(AlarmManager.RTC, calendar1.getTimeInMillis(), pendingIntent1);
            }

            Toast.makeText(this, "Notification Will Come at Time specified",
                    Toast.LENGTH_SHORT).show();

        }
        if(!(wednesdaytime.isEmpty())){

            String timeSplit[] = wednesdaytime.split(":");
            int hour  = Integer.parseInt(timeSplit[0]);
            int minute   = Integer.parseInt(timeSplit[1]);

            Intent intent1 = new Intent(getApplicationContext(),
                    AlertReceiver.class);

            PendingIntent pendingIntent1 = PendingIntent.getBroadcast(getApplicationContext(),
                    1,
                    intent1,
                    PendingIntent.FLAG_UPDATE_CURRENT);

            AlarmManager alarmManager1 = (AlarmManager)getSystemService(context.ALARM_SERVICE);

            java.util.Calendar calendar1 = java.util.Calendar.getInstance();

            calendar1.set(java.util.Calendar.DAY_OF_WEEK,
                    Calendar.WEDNESDAY);
            calendar1.set(java.util.Calendar.HOUR_OF_DAY,
                    hour);
            calendar1.set(java.util.Calendar.MINUTE,
                    minute);
            calendar1.set(java.util.Calendar.SECOND,
                    0);

            if(calendar1.before(Calendar.getInstance())) {
                calendar1.add(Calendar.DATE, 1);
            }

            if (Build.VERSION.SDK_INT >= 19) {

                alarmManager1.setExact(AlarmManager.RTC, calendar1.getTimeInMillis(), pendingIntent1);
            }else{
                alarmManager1.set(AlarmManager.RTC, calendar1.getTimeInMillis(), pendingIntent1);
            }

            Toast.makeText(this, "Notification Will Come at Time specified",
                    Toast.LENGTH_SHORT).show();


        }
        if(!(thrusdaytime.isEmpty())){

            String timeSplit[] = thrusdaytime.split(":");
            int hour  = Integer.parseInt(timeSplit[0]);
            int minute   = Integer.parseInt(timeSplit[1]);

            Intent intent1 = new Intent(getApplicationContext(),
                    AlertReceiver.class);

            PendingIntent pendingIntent1 = PendingIntent.getBroadcast(getApplicationContext(),
                    1,
                    intent1,
                    PendingIntent.FLAG_UPDATE_CURRENT);

            AlarmManager alarmManager1 = (AlarmManager)getSystemService(context.ALARM_SERVICE);

            java.util.Calendar calendar1 = java.util.Calendar.getInstance();

            calendar1.set(java.util.Calendar.DAY_OF_WEEK,
                    Calendar.THURSDAY);
            calendar1.set(java.util.Calendar.HOUR_OF_DAY,
                    hour);
            calendar1.set(java.util.Calendar.MINUTE,
                    minute);
            calendar1.set(java.util.Calendar.SECOND,
                    0);

            if(calendar1.before(Calendar.getInstance())) {
                calendar1.add(Calendar.DATE, 1);
            }

            if (Build.VERSION.SDK_INT >= 19) {

                alarmManager1.setExact(AlarmManager.RTC, calendar1.getTimeInMillis(), pendingIntent1);
            }else{
                alarmManager1.set(AlarmManager.RTC, calendar1.getTimeInMillis(), pendingIntent1);
            }

            Toast.makeText(this, "Notification Will Come at Time specified",
                    Toast.LENGTH_SHORT).show();


        }
        if(!(fridaytime.isEmpty())){

            String timeSplit[] = fridaytime.split(":");
            int hour  = Integer.parseInt(timeSplit[0]);
            int minute   = Integer.parseInt(timeSplit[1]);

            Intent intent1 = new Intent(getApplicationContext(),
                    AlertReceiver.class);

            PendingIntent pendingIntent1 = PendingIntent.getBroadcast(getApplicationContext(),
                    1,
                    intent1,
                    PendingIntent.FLAG_UPDATE_CURRENT);

            AlarmManager alarmManager1 = (AlarmManager)getSystemService(context.ALARM_SERVICE);

            java.util.Calendar calendar1 = java.util.Calendar.getInstance();

            calendar1.set(java.util.Calendar.DAY_OF_WEEK,
                    Calendar.FRIDAY);
            calendar1.set(java.util.Calendar.HOUR_OF_DAY,
                    hour);
            calendar1.set(java.util.Calendar.MINUTE,
                    minute);
            calendar1.set(java.util.Calendar.SECOND,
                    0);

            if(calendar1.before(Calendar.getInstance())) {
                calendar1.add(Calendar.DATE, 1);
            }

            if (Build.VERSION.SDK_INT >= 19) {

                alarmManager1.setExact(AlarmManager.RTC, calendar1.getTimeInMillis(), pendingIntent1);
            }else{
                alarmManager1.set(AlarmManager.RTC, calendar1.getTimeInMillis(), pendingIntent1);
            }

            Toast.makeText(this, "Notification Will Come at Time specified",
                    Toast.LENGTH_SHORT).show();


        }
        if(!(saturadytime.isEmpty())){

            String timeSplit[] = saturadytime.split(":");
            int hour  = Integer.parseInt(timeSplit[0]);
            int minute   = Integer.parseInt(timeSplit[1]);

            Intent intent1 = new Intent(getApplicationContext(),
                    AlertReceiver.class);

            PendingIntent pendingIntent1 = PendingIntent.getBroadcast(getApplicationContext(),
                    1,
                    intent1,
                    PendingIntent.FLAG_UPDATE_CURRENT);

            AlarmManager alarmManager1 = (AlarmManager)getSystemService(context.ALARM_SERVICE);

            java.util.Calendar calendar1 = java.util.Calendar.getInstance();

            calendar1.set(java.util.Calendar.DAY_OF_WEEK,
                    Calendar.SATURDAY);
            calendar1.set(java.util.Calendar.HOUR_OF_DAY,
                    hour);
            calendar1.set(java.util.Calendar.MINUTE,
                    minute);
            calendar1.set(java.util.Calendar.SECOND,
                    0);

            if(calendar1.before(Calendar.getInstance())) {
                calendar1.add(Calendar.DATE, 1);
            }

            if (Build.VERSION.SDK_INT >= 19) {

                alarmManager1.setExact(AlarmManager.RTC, calendar1.getTimeInMillis(), pendingIntent1);
            }else{
                alarmManager1.set(AlarmManager.RTC, calendar1.getTimeInMillis(), pendingIntent1);
            }

            Toast.makeText(this, "Notification Will Come at Time specified",
                    Toast.LENGTH_SHORT).show();

        }
        if(!(sundaytime.isEmpty())){

            String timeSplit[] = sundaytime.split(":");
            int hour  = Integer.parseInt(timeSplit[0]);
            int minute   = Integer.parseInt(timeSplit[1]);

            Intent intent1 = new Intent(getApplicationContext(),
                    AlertReceiver.class);

            PendingIntent pendingIntent1 = PendingIntent.getBroadcast(getApplicationContext(),
                    1,
                    intent1,
                    PendingIntent.FLAG_UPDATE_CURRENT);

            AlarmManager alarmManager1 = (AlarmManager)getSystemService(context.ALARM_SERVICE);

            java.util.Calendar calendar1 = java.util.Calendar.getInstance();

            calendar1.set(java.util.Calendar.DAY_OF_WEEK,
                    Calendar.SUNDAY);
            calendar1.set(java.util.Calendar.HOUR_OF_DAY,
                    hour);
            calendar1.set(java.util.Calendar.MINUTE,
                    minute);
            calendar1.set(java.util.Calendar.SECOND,
                    0);

            if(calendar1.before(Calendar.getInstance())) {
                calendar1.add(Calendar.DATE, 1);
            }

            if (Build.VERSION.SDK_INT >= 19) {

                alarmManager1.setExact(AlarmManager.RTC, calendar1.getTimeInMillis(), pendingIntent1);
            }else{
                alarmManager1.set(AlarmManager.RTC, calendar1.getTimeInMillis(), pendingIntent1);
            }

            Toast.makeText(this, "Notification Will Come at Time specified",
                    Toast.LENGTH_SHORT).show();
        }

    }


    private class SendGroupUlta extends AsyncTask<String, Void, String> {

        private ProgressDialog pDialog;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(SetReminderForGRoup.this);
            pDialog.setMessage("Inform Sender...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();

        }

        @Override
        protected String doInBackground(String... args) {
            String s = "";
            //  Log.d("Addno", "addno11 " + text);

            try {
                HttpClient httpClient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost("http://aceresults.info/api/send_message_group");
                JSONObject jsonObject = new JSONObject();

                jsonObject.accumulate("sender_id", SharedPreferenceClass.getUserInfo(getApplicationContext()).getPhone());
                jsonObject.accumulate("chat_group_id", chat_group_id);
                jsonObject.accumulate("group_mobile", sender_id+","+group_mobile);
                jsonObject.accumulate("pray_type", "pray_accept");
                jsonObject.accumulate("message","  Pray Accepted");
                Log.d("jhvbvibib1234","ibib222 "+SharedPreferenceClass.getUserInfo(getApplicationContext()).getPhone() + sender_id+","+group_mobile);

                StringEntity stringEntity = new StringEntity(jsonObject.toString());
                httpPost.setEntity(stringEntity);
                HttpResponse httpResponse = httpClient.execute(httpPost);
                HttpEntity httpEntity = httpResponse.getEntity();
                s = readResponse(httpResponse);
                Log.d("tag11245rec", " " + s);
            } catch (Exception exception) {
                exception.printStackTrace();
            }
            return s;

        }

        protected void onProgressUpdate(Integer... progress) {

        }

        @Override
        protected void onPostExecute(String json) {
            super.onPostExecute(json);
            Log.d("OnPOstrece", " " + json);
//            pDialog.dismiss();
            String fname = null;
            String lname = null;
//            try {
//                JSONObject jsonObject = new JSONObject(json);
//                if (!jsonObject.getBoolean("error")) {
//                    JSONArray jsonArray = jsonObject.getJSONArray("message");
//                    if (jsonArray.length() > 0) {
//                        for (int i = 0; i < jsonArray.length(); i++) {
//                            JSONObject movie = jsonArray.getJSONObject(i);
//                            fname  = movie.getString("first_name");
//                            lname  = movie.getString("last_name");
//                        }
//                        User user = new User(jsonArray.getJSONObject(0));
//                        if (user != null) {
//                            SharedPreferenceClass.setUserInfo(context, user);
//                            session.createUserLoginSession(lname);
//                            session.SaveName(fname);
//                            startActivity(new Intent(context, MainActivity.class));
//                            finish();
//
//                        }
//
//                    }
//                }
//
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
        }


        private String readResponse(HttpResponse httpResponse) {

            InputStream is = null;
            String return_text = "";
            try {
                is = httpResponse.getEntity().getContent();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is));
                String line = "";
                StringBuffer sb = new StringBuffer();
                while ((line = bufferedReader.readLine()) != null) {
                    sb.append(line);
                }
                return_text = sb.toString();
                Log.d("return_text", "" + return_text);
            } catch (Exception e) {

            }
            return return_text;
        }

    }


}
