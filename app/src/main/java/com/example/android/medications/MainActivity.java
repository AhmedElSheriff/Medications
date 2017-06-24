package com.example.android.medications;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
import android.net.Uri;
import android.provider.AlarmClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    myDataBase myDB;

    TextView alarmName;
    AlarmManager alarmManager;
    TimePicker timePicker;
    Calendar calendar;
    Context context = this;
    PendingIntent pendingIntent;
    Intent i;
    public Boolean flag1 = false;
    static int counter = 0;
    String alarm_Name;
    String hourText;
    String minText;
    Button addButton;
    Button showButton;
    Button stopButton;
    TextView deleteDataName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myDB = new myDataBase(this);

        this.context = this;
        alarmName = (TextView) findViewById(R.id.MedNameX);
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        calendar = Calendar.getInstance();
        timePicker = (TimePicker) findViewById(R.id.timePicker1);
        addButton = (Button) findViewById(R.id.Add);
        showButton = (Button) findViewById(R.id.Show);
        stopButton = (Button) findViewById(R.id.Stop);
        deleteDataName = (TextView) findViewById(R.id.Delete);
        //startAlarm();
        // showData();

    }

    public void startAlarm(View view) {

        //addButton.setOnClickListener(
        //  new View.OnClickListener() {
        //    @Override
        //    public void onClick(View v) {

        counter++;
        String am_pm;
        calendar.set(Calendar.HOUR_OF_DAY, timePicker.getHour());
        calendar.set(Calendar.MINUTE, timePicker.getMinute());
        if (calendar.get(Calendar.AM_PM) == Calendar.AM) {
            am_pm = "AM";
        } else if (calendar.get(Calendar.AM_PM) == Calendar.PM) {
            am_pm = "PM";
        }


        i = new Intent(this.context, AlarmReceiver.class);


        int hourInt = timePicker.getHour();
        int minInt = timePicker.getMinute();
        hourText = String.valueOf(hourInt);
        minText = String.valueOf(minInt);
        String showTime = calendar.getTime().toString();

        if (hourInt > 12) {
            hourText = String.valueOf(hourInt - 12);
        }
        if (minInt < 10) {
            minText = "0" + String.valueOf(minInt);
        }
        String finalTime = hourText + ":" + minText;


        alarm_Name = alarmName.getText().toString();

        if (alarm_Name.equals(""))
            alarm_Name = "Alarm";

        //String test = alarmName.getText().toString();
        boolean isInserted = myDB.insertData(alarm_Name, finalTime);
        if (isInserted == true)
            Toast.makeText(context,/*alarm_Name*/ " Data inserted ", Toast.LENGTH_LONG).show();
        else
            Toast.makeText(context, " Data not inserted ", Toast.LENGTH_LONG).show();

        Toast.makeText(context, alarm_Name + " set to " + finalTime, Toast.LENGTH_LONG).show();
        i.putExtra("extra", "on");
        i.putExtra("medName", alarm_Name);

        //ContentValues myContent = new ContentValues();

        pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 0, i, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);

    }
    //   }


    // );
    // }

    public void stopAlarm(View view) {

        if (counter >= 1) {
            alarmManager.cancel(pendingIntent);
            i.putExtra("extra", "off");
            sendBroadcast(i);
        }

        Toast.makeText(context, "Alarm off", Toast.LENGTH_LONG).show();
    }

    public void showData(View view) {
        //  showButton.setOnClickListener(
        //        new View.OnClickListener() {
        //          @Override
        //     public void onClick(View v) {

//                        Cursor res = myDB.getAllData();
//                        if(res.getCount() == 0)
//                        {
//                            showMessage("Error","No data Found");
//                            return ;
//                        }
//
//                        StringBuffer buffer = new StringBuffer();
//                        while (res.moveToNext())
//                        {
//                            buffer.append("ID: " + res.getString(0)+"\n");
//                            buffer.append("Name: " + res.getString(1)+"\n");
//                             buffer.append("Time: " + res.getString(2)+"\n");
//
//                        }
//                        showMessage("Data",buffer.toString());

//        showMessage("Data",buffer.toString());
        Intent showDatab = new Intent(this, ShowDataBase.class);
//        showDatab.putExtra("alarmname",alarm_Name);
//        showDatab.putExtra("hourtext",hourText);
        startActivity(showDatab);

    }
    //    }


    //  );
    //  }

    public void showMessage(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();

    }

    public void deleteData(View view) {
        Integer deletedRows = myDB.deleteDB(alarmName.getText().toString());
        if (deletedRows > 0) {
            Toast.makeText(context,/*alarm_Name*/ alarmName.getText().toString() + " Deleted ", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(context,/*alarm_Name*/ alarmName.getText().toString() + " Not Deleted ", Toast.LENGTH_LONG).show();
        }

    }

    public void clearData(View view)
    {
        Integer clearedRows = myDB.clearDB();
        if(clearedRows > 0)
        {
            Toast.makeText(context,/*alarm_Name*/   " All Data Deleted ", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(context,/*alarm_Name*/  " No Data Deleted ", Toast.LENGTH_LONG).show();
        }


    }




}
