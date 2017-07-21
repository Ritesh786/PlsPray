package plspray.infoservices.lue.plspray.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;

import java.util.List;


import plspray.infoservices.lue.plspray.R;
import plspray.infoservices.lue.plspray.databind.DayTime;
import plspray.infoservices.lue.plspray.databind.Schedule;

/**
 * Created by lue on 08-06-2017.
 */

public class ScheduleListAdapter extends ArrayAdapter<Schedule> {

     private List<Schedule> scheduleList;
    Context context;
    int layoutResourceId;


    public ScheduleListAdapter(Context context, int layoutResourceId, List<Schedule> scheduleList)
    {
        super(context, layoutResourceId, scheduleList);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.scheduleList=scheduleList;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View row = convertView;
        ImageHolder holder = null;
        if (row == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);
            holder = new ImageHolder();
            holder.scheduleLayout = (LinearLayout) row.findViewById(R.id.scheduleLayout);
            holder.datetimeText = (TextView) row.findViewById(R.id.datetimeText);

            row.setTag(holder);
        } else {
            holder = (ImageHolder) row.getTag();
        }

        final Schedule schedule = scheduleList.get(position);
        holder.datetimeText.setText(schedule.getTime());


         for(DayTime dayTime:schedule.getDayTimeList())
         {
             View scheduleView = ((LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.schedule_view, null);
             TextView dayText=(TextView)scheduleView.findViewById(R.id.dayText);
             TextView timeText=(TextView)scheduleView.findViewById(R.id.timeText);
             dayText.setText(dayTime.getDay());
             timeText.setText(dayTime.getTime());
             holder.scheduleLayout.addView(scheduleView);
         }

        row.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

        holder = (ImageHolder) row.getTag();
        return row;

    }

    static class ImageHolder {
     LinearLayout scheduleLayout;
        TextView datetimeText;
    }


    @Override
    public int getItemViewType(int position) {

        return position;
    }


}
