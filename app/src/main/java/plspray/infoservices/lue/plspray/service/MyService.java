package plspray.infoservices.lue.plspray.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.util.Log;

import org.json.JSONObject;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import plspray.infoservices.lue.plspray.R;
import plspray.infoservices.lue.plspray.SetReminderActivity;
import plspray.infoservices.lue.plspray.databind.TimeSchedule;
import plspray.infoservices.lue.plspray.utilities.SharedPreferenceClass;


public class MyService extends Service {

    NotifyService notifyService;
    IntentFilter intentFilter;
    Timer timer;
    boolean isNotification;
    int h,m,pushupsCount;
    String name="";
    int day=-1;
    NotificationManager notificationManager;
    Intent intent;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

   /*    TimeInfo timeInfo=CommonVariables.getTimeInfo(getApplicationContext());
        h=timeInfo.getHours();
        m=timeInfo.getMinutes();

        UserInfo userInfo=CommonVariables.getUserInfo(getApplicationContext());
        name=userInfo.getName();
        pushupsCount=userInfo.getCount();*/


        TimeSchedule timeSchedule= SharedPreferenceClass.getSchedule(getApplicationContext());
        Date date=new Date();
        String timeText=null;
        if(timeSchedule!=null)
        {
         switch (date.getDay())
         {
             case 0: timeText=timeSchedule.getSunDayTime();
                 break;
             case 1:timeText=timeSchedule.getMondayTime();
                 break;
             case 2:timeText=timeSchedule.getTuesDayTime();
                 break;
             case 3:timeText=timeSchedule.getWednesDayTime();
                 break;
             case 4: timeText=timeSchedule.getThursDayTime();
                 break;
             case 5:timeText=timeSchedule.getFriDayTime();
                 break;
             case 6:timeText=timeSchedule.getSaturDayTime();
                 break;
         }

         if(timeText!=null)
         {
             String[] timeArray=timeText.split(":");
             h=Integer.parseInt(timeArray[0]);
             m=Integer.parseInt(timeArray[1]);
         }

            intent=new Intent(this, SetReminderActivity.class);
            intent.putExtra("timeschedule",timeSchedule);
            intent.putExtra("from_service",true);

        }

        try {
            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.cancel(1);
        }catch (Exception e){}
      startTimer();
       // return super.onStartCommand(intent, flags, startId);
        return START_STICKY;
    }

    private void startTimer()
    {
        timer=new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                Date date=new Date();
                if(h==date.getHours() && date.getMinutes()==m)
                {
                    soundNotification();
                    if(timer!=null) timer.cancel();
                    try {
                        stopService(new Intent(getApplicationContext(),MyService.class));
                    }catch (Exception e){}



                }
                Log.e("...................","timer");
            }
        },1000,1000);
    }

    @Override
    public void onCreate() {
        super.onCreate();



    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Intent broadcastIntent = new Intent();
        sendBroadcast(broadcastIntent);
        isNotification=false;
    }


    private void soundNotification() {

        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent,
                PendingIntent.FLAG_ONE_SHOT);
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.plspray_logo)
                .setContentTitle(getString(R.string.app_name))
                .setContentText("")
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);
        notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationBuilder.setPriority(Notification.PRIORITY_HIGH);
        if (Build.VERSION.SDK_INT >= 21) notificationBuilder.setVibrate(new long[0]);
        notificationManager.notify(0, notificationBuilder.build());
    }
}
