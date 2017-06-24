package com.example.android.medications;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import java.util.Calendar;

/**
 * Created by Abshafi on 6/22/2016.
 */
public class AlarmReceiver extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {

        String extraString = "";
        extraString = intent.getExtras().getString("extra");
        String medName = intent.getExtras().getString("medName");

        Log.e("We are on the Receiver",extraString);
        String extraSecString = "";
        if(extraString .equals("on"))
        {
            extraSecString = "on";
        }
        else
        {
            extraSecString = "off";
        }

        Intent serviceIntent = new Intent(context,RingtoneService.class);
        serviceIntent.putExtra("extra",extraSecString);
        serviceIntent.putExtra("medName",medName);
        context.startService(serviceIntent);
    }
}
