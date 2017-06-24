package com.example.android.medications;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by Abshafi on 6/24/2016.
 */
public class RingtoneService extends Service {

    MediaPlayer mediaSound;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        String extraString = "";
        extraString = intent.getExtras().getString("extra");
        assert extraString != null;
        if(extraString .equals("on"))
        mediaSound = MediaPlayer.create(this, R.raw.alarm);
        String MedName = intent.getExtras().getString("medName");

        NotificationManager notifyManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Intent mainIntent = new Intent(this.getApplicationContext(),MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,mainIntent,0);
        Notification notification_popup = new Notification.Builder(this)
                .setContentTitle("It's time to take your "+ MedName)
                .setContentText("Click to hide")
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)
                .setSmallIcon(R.drawable.noticon)
                .build();

        notifyManager.notify(0,notification_popup);
        Boolean isOff = false;
        assert extraString != null;
        if(extraString .equals("off"))
        {
            isOff = true;
            Log.e("Extra is: ", "isoff initilaized");
        }

        if(isOff == true)
        {
            mediaSound.stop();
            Log.e("Extra is: ", "isOff is true");

        }
        else if(isOff == false)
        {

            mediaSound.start();
            Log.e("Extra is: ", "isOff is false");

        }






        return START_NOT_STICKY;
    }


    @Override
    public void onDestroy()
    {

    }
}
