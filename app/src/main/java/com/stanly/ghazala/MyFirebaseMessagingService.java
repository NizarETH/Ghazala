package com.stanly.ghazala;


import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.stanly.ghazala.Activities.MainActivity;

import static android.content.Context.NOTIFICATION_SERVICE;


public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "MyFirebaseMsgService";
    //
    /**
     * Called when message is received.
     *
     * @param remoteMessage Object representing the message received from Firebase Cloud Messaging.
     */
    // [START receive_message]
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {


        Log.d(TAG, "From: " + remoteMessage.getFrom());


        if (remoteMessage.getNotification() != null && remoteMessage.getNotification().getBody() != null) {
            sendNotification(remoteMessage.getNotification().getBody());
        }

         }




    /**
     * Handle time allotted to BroadcastReceivers.
     */
    private void handleNow() {
        Log.d(TAG, "Short lived task is done.");
    }

    /**
     * Create and show a simple notification containing the received FCM message.
     *
     * @param messageBody FCM message body received.
     */
    private void sendNotification(String messageBody) {

        Uri sound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setContentTitle("Message");
        builder.setContentText(messageBody);
        builder.setSound(sound);

        {
            Intent intent = new Intent(this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);
            builder.setContentIntent(pendingIntent);
        }

        builder.setSmallIcon(R.drawable.info_icon);
        NotificationManager  notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        if(notificationManager != null)
            notificationManager.notify(0, builder.build());


    }


}