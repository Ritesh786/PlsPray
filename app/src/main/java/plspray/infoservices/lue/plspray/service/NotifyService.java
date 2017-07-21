package plspray.infoservices.lue.plspray.service;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;

import java.util.Date;

/**
 * Created by lue on 21-04-2017.
 */

public class NotifyService extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        final String action = intent.getAction();

        context.startService(new Intent(context, MyService.class));
  /*     if (action.equals(Intent.ACTION_TIME_TICK))
        {
            Date date=new Date();
            if(CommonVariables.h==date.getHours() && date.getMinutes()==CommonVariables.m)
            {
          showNotification(context);

            }

       }*/
    }


    private void showNotification(Context c)
    {
  /*      NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(c)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle("Pushups")
                        .setContentText("Dear "+CommonVariables.userName+", you have "+CommonVariables.pushupCount+" Pushups to do");

        Intent resultIntent = new Intent(c, MainActivity.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(c);
        stackBuilder.addParentStack(MainActivity.class);

       stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(
                        0,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );
        mBuilder.setContentIntent(resultPendingIntent);
        NotificationManager mNotificationManager =
                (NotificationManager)c.getSystemService(Context.NOTIFICATION_SERVICE);

        mNotificationManager.notify(1, mBuilder.build());
        CommonVariables.isNotification=true;*/


    }

}
