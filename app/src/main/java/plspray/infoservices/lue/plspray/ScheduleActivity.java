package plspray.infoservices.lue.plspray;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import plspray.infoservices.lue.plspray.adapter.ScheduleListAdapter;
import plspray.infoservices.lue.plspray.databind.Schedule;

public class ScheduleActivity extends AppCompatActivity {

    ListView listView;
    ScheduleListAdapter scheduleListAdapter;
    List<Schedule> scheduleList;
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        context=this;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        scheduleList=new ArrayList<>();
        scheduleListAdapter=new ScheduleListAdapter(this,R.layout.schedule_layout,scheduleList);
        listView.setAdapter(scheduleListAdapter);

    }

}
