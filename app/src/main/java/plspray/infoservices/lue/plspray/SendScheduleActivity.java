package plspray.infoservices.lue.plspray;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import plspray.infoservices.lue.plspray.Async.DownloadThread;
import plspray.infoservices.lue.plspray.Async.Urls;
import plspray.infoservices.lue.plspray.adapter.ScheduleListAdapter;
import plspray.infoservices.lue.plspray.databind.DayTime;
import plspray.infoservices.lue.plspray.databind.Schedule;
import plspray.infoservices.lue.plspray.databind.TimeSchedule;
import plspray.infoservices.lue.plspray.databind.User;
import plspray.infoservices.lue.plspray.utilities.AppConstants;
import plspray.infoservices.lue.plspray.utilities.SharedPreferenceClass;
import plspray.infoservices.lue.plspray.utilities.UtilityClass;

public class SendScheduleActivity extends AppCompatActivity {


    ListView listView;
    ScheduleListAdapter scheduleListAdapter;
    List<Schedule>  scheduleList;
    Context context;
    String userid="";
    String groupId="";
    TimeSchedule timeSchedule;
    String title="";
    String recieverId="";
    CoordinatorLayout coordinatorLayout;

    Button mcreteschedulebtn,getMcreteschedulebtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_schedule);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        context=this;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        listView=(ListView)findViewById(R.id.listView);
        coordinatorLayout=(CoordinatorLayout)findViewById(R.id.coordinatorLayout);
        scheduleList=new ArrayList<>();
        scheduleListAdapter=new ScheduleListAdapter(this,R.layout.schedule_layout,scheduleList);
        listView.setAdapter(scheduleListAdapter);

          try
          {
             userid= SharedPreferenceClass.getUserInfo(context).getId();
              groupId=getIntent().getStringExtra(AppConstants.GROUPID);
              title=getIntent().getStringExtra(AppConstants.CONTACT_NAME);
              recieverId=getIntent().getStringExtra(AppConstants.USERID);
              getSupportActionBar().setTitle(title);

          }catch (Exception e){}

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.bringToFront();
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent i=new Intent(context,AddTimeActivity.class);
//                startActivityForResult(i,1);
//            }
//        });

        mcreteschedulebtn = (Button) findViewById(R.id.creteschedule_btn);
        mcreteschedulebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i=new Intent(context,AddTimeActivity.class);
                 startActivityForResult(i,1);

            }
        });


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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        scheduleList.clear();
        if(resultCode==RESULT_OK && requestCode==1)
        {
         timeSchedule=data.getParcelableExtra(AppConstants.SCHEDULE);
            if(timeSchedule!=null)
            {
                List<DayTime> dayTimeList=new ArrayList<>();
                dayTimeList.add(new DayTime(getString(R.string.monday),timeSchedule.getMondayTime()));
                dayTimeList.add(new DayTime(getString(R.string.tuesday),timeSchedule.getTuesDayTime()));
                dayTimeList.add(new DayTime(getString(R.string.wednesday),timeSchedule.getWednesDayTime()));
                dayTimeList.add(new DayTime(getString(R.string.thursday),timeSchedule.getThursDayTime()));
                dayTimeList.add(new DayTime(getString(R.string.friday),timeSchedule.getFriDayTime()));
                dayTimeList.add(new DayTime(getString(R.string.saturday),timeSchedule.getSaturDayTime()));
                dayTimeList.add(new DayTime(getString(R.string.sunday),timeSchedule.getSunDayTime()));

                Schedule schedule=new Schedule();
                schedule.setDayTimeList(dayTimeList, UtilityClass.getTime());
                scheduleList.add(schedule);
                scheduleListAdapter.notifyDataSetChanged();
                sendMessage(schedule);

            }
        }
    }


    public void sendMessage(Schedule schedule) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.accumulate("sender_id", SharedPreferenceClass.getUserInfo(context).getId());
            jsonObject.accumulate("reciever_id", recieverId);
            jsonObject.accumulate("group_id", "");
            jsonObject.accumulate("pray_type", "pray_send");
            final JSONObject messageObj = new JSONObject();
            for (DayTime dayTime : schedule.getDayTimeList()) {
                messageObj.accumulate(dayTime.getDay(), dayTime.getTime());
            }
            jsonObject.accumulate("message", messageObj.toString());

            new DownloadThread(this, Urls.send_message, jsonObject.toString(), new DownloadThread.AsyncResponse() {
                @Override
                public void processFinish(String output) {
                    try {
                        JSONObject jsonObject = new JSONObject(output);
                        if (!jsonObject.getBoolean("error")) {
                            JSONArray jsonArray = jsonObject.getJSONArray("message");
                            if (jsonArray.length() > 0) {
                                SharedPreferenceClass.setSchedule(context,messageObj.toString());
                              Snackbar.make(coordinatorLayout,"Sent Successfully",Snackbar.LENGTH_SHORT);
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }, true).execute();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    }
