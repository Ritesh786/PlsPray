package plspray.infoservices.lue.plspray;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import com.afollestad.materialdialogs.MaterialDialog;

import plspray.infoservices.lue.plspray.databind.TimeSchedule;
import plspray.infoservices.lue.plspray.utilities.AppConstants;

public class AddTimeActivity extends AppCompatActivity implements View.OnClickListener {


    TextView mondayTimeText,tuesdayTimeText,wednesdayTimeText,thursdayTimeText,fridayTimeText,saturdayTimeText,sundayTimeText;
    ImageButton mondayTimeBtn,tuesdayTimeBtn,wednesdayTimeBtn,thursdayTimeBtn,fridayTimeBtn,saturdayTimeBtn,sundayTimeBtn;

    TimeSchedule timeSchedule;
    Button sendBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_time);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        initialize();
    }

    private void initialize()
    {
        mondayTimeText=(TextView)findViewById(R.id.mondayTimeText);
        tuesdayTimeText=(TextView)findViewById(R.id.tuesdayTimeText);
        wednesdayTimeText=(TextView)findViewById(R.id.wednesdayTimeText);
        thursdayTimeText=(TextView)findViewById(R.id.thursdayTimeText);
        fridayTimeText=(TextView)findViewById(R.id.fridayTimeText);
        saturdayTimeText=(TextView)findViewById(R.id.saturdayTimeText);
        sundayTimeText=(TextView)findViewById(R.id.sundayTimeText);

        mondayTimeBtn=(ImageButton)findViewById(R.id.mondayTimeBtn);
        tuesdayTimeBtn=(ImageButton)findViewById(R.id.tuesdayTimeBtn);
        wednesdayTimeBtn=(ImageButton)findViewById(R.id.wednesdayTimeBtn);
        thursdayTimeBtn=(ImageButton)findViewById(R.id.thursdayTimeBtn);
        fridayTimeBtn=(ImageButton)findViewById(R.id.fridayTimeBtn);
        saturdayTimeBtn=(ImageButton)findViewById(R.id.saturdayTimeBtn);
        sundayTimeBtn=(ImageButton)findViewById(R.id.sundayTimeBtn);

        sendBtn=(Button) findViewById(R.id.sendBtn);

        mondayTimeBtn.setOnClickListener(this);
        tuesdayTimeBtn.setOnClickListener(this);
        wednesdayTimeBtn.setOnClickListener(this);
        thursdayTimeBtn.setOnClickListener(this);
        fridayTimeBtn.setOnClickListener(this);
        saturdayTimeBtn.setOnClickListener(this);
        sundayTimeBtn.setOnClickListener(this);
        sendBtn.setOnClickListener(this);

        timeSchedule=new TimeSchedule();

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
    public void onClick(View v) {

        switch (v.getId())
        {
            case R.id.mondayTimeBtn:
                 addTime(mondayTimeText,1);
                break;
            case R.id.tuesdayTimeBtn:
                addTime(tuesdayTimeText,2);
                break;
            case R.id.wednesdayTimeBtn:
                addTime(wednesdayTimeText,3);
                break;
            case R.id.thursdayTimeBtn:
                addTime(thursdayTimeText,4);
                break;
            case R.id.fridayTimeBtn:
                addTime(fridayTimeText,5);
                break;
            case R.id.saturdayTimeBtn:
                addTime(saturdayTimeText,6);
                break;
            case R.id.sundayTimeBtn:
                addTime(sundayTimeText,7);
                break;

            case R.id.sendBtn:
               Intent i=new Intent();
                i.putExtra(AppConstants.SCHEDULE, timeSchedule);
                setResult(RESULT_OK,i);
                finish();
                break;
        }
    }

    private void addTime(final TextView textView, final int day)
    {
        final MaterialDialog materialDialog=new MaterialDialog.Builder(this)
                .customView(R.layout.time_layout, false)
                .backgroundColor(getResources().getColor(android.R.color.white)).build();
        final TimePicker timePicker=(TimePicker)materialDialog.findViewById(R.id.timePicker);
        Button setBtn=(Button)materialDialog.findViewById(R.id.setBtn);
        materialDialog.show();

        setBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int h=timePicker.getCurrentHour();
                int m=timePicker.getCurrentMinute();
                String timeString=h+":"+m;
                textView.setText(timeString);
                switch (day)
                {
                    case 1: timeSchedule.setMondayTime(timeString); break;
                    case 2: timeSchedule.setTuesDayTime(timeString); break;
                    case 3: timeSchedule.setWednesDayTime(timeString); break;
                    case 4: timeSchedule.setThursDayTime(timeString); break;
                    case 5: timeSchedule.setFriDayTime(timeString); break;
                    case 6: timeSchedule.setSaturDayTime(timeString); break;
                    case 7: timeSchedule.setSunDayTime(timeString); break;

                }
                materialDialog.dismiss();
            }
        });
    }
}
