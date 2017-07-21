package plspray.infoservices.lue.plspray.fcm;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;


import plspray.infoservices.lue.plspray.AdminMessage;
import plspray.infoservices.lue.plspray.MainActivity;
import plspray.infoservices.lue.plspray.R;
import plspray.infoservices.lue.plspray.SetReminderActivity;
import plspray.infoservices.lue.plspray.utilities.AppConstants;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "MyFirebaseMsgService";
    NotificationManager notificationManager;
    Intent intent;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
       Log.d("Firebase notification","received");
        Map<String, String> params = remoteMessage.getData();
        Log.d(TAG, "Notification Message Body: " + params.toString());
        JSONObject jsonObject=new JSONObject(params);
        try {

            String msgtype =jsonObject.getString("type");

            if(msgtype.equals("user")) {
                intent = new Intent(this, SetReminderActivity.class);
                intent.putExtra("msg_id", jsonObject.getString("msg_id"));
                JSONObject messageObj = new JSONObject(jsonObject.getString("message"));
                intent.putExtra("message", messageObj.getString("message"));
                intent.putExtra("sender_id", messageObj.getString("sender_id"));
                intent.putExtra("message_date", messageObj.getString("message_date"));
                intent.putExtra("first_name", messageObj.getString("first_name"));
                intent.putExtra("last_name", messageObj.getString("last_name"));

                soundNotification("Pray Request");

            }

          if(msgtype.equals("admin")){

              intent = new Intent(this, AdminMessage.class);
              intent.putExtra("msg_id", jsonObject.getString("msg_id"));
              JSONObject messageObj = new JSONObject(jsonObject.getString("message"));
              intent.putExtra("title", messageObj.getString("title"));
              intent.putExtra("description", messageObj.getString("description"));

              soundNotification("Admin Message");

          }

        } catch (JSONException e) {
            e.printStackTrace();
        }





    }

    private void soundNotification(String msg) {

        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent,
                PendingIntent.FLAG_ONE_SHOT);
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.plspray_logo)
                .setContentTitle(getString(R.string.app_name))
                .setContentText(msg)
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