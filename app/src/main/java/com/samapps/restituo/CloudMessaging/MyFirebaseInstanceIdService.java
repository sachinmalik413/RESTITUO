package com.samapps.restituo.CloudMessaging;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.samapps.restituo.R;

import org.jetbrains.annotations.NotNull;


//the class extending FirebaseInstanceIdService
public class MyFirebaseInstanceIdService extends FirebaseMessagingService {


    //this method will be called
    //when the token is generated

    @Override
    public void onNewToken(String s) {
        super.onNewToken(s);
        Log.d("NEW_TOKEN", s);
    }
// below is token
    //  D/NEW_TOKEN: eln1genyRdOmV_Z_6_h2Oz:APA91bGgbCwsrvPqodqLnkUCh2fbtbupFpe8rdBiscYOvuMH5zeplk1YJ9hZPKHQ_7u5cvQx9xFDir9uKh54RdpAEeAon_BPaxr_ID6Utmp224Fj_1vq6w1EnQhtHqmCNwMgg0sVQKO0

    @Override
    public void onMessageReceived(@NotNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        //if the message contains data payload
        //It is a map of custom keyvalues
        //we can read it easily
        if (remoteMessage.getData().size() > 0) {
            //handle the data message here
        }

        //getting the title and the body
        String title = remoteMessage.getNotification().getTitle();
        String body = remoteMessage.getNotification().getBody();
        Log.d("Message : ",title+body);

        MyNotificationManager.getInstance(this).displayNotification(title,body);

    }

}
