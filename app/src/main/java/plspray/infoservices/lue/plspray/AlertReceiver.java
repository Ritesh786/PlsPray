package plspray.infoservices.lue.plspray;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

/**
 * Created by lue on 13-07-2017.
 */

public class AlertReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        CreatePrayNotification(context,"Pray TIme","Time For Pray","Alert");

    }

    private void CreatePrayNotification(Context context, String msg, String msgtxt, String alert) {

        PendingIntent prayintent = PendingIntent.getActivity(context,0
                ,new Intent (context,MainActivity.class),0);

        NotificationCompat.Builder mbuilder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.plspray_logo)
                .setContentTitle(msg)
                 .setTicker(alert)
                .setContentText(msgtxt);

        mbuilder.setContentIntent(prayintent);
        mbuilder.setDefaults(NotificationCompat.DEFAULT_SOUND);
        mbuilder.setAutoCancel(true);

        NotificationManager mnotifimanager = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
        mnotifimanager.notify(1,mbuilder.build());


    }
}
