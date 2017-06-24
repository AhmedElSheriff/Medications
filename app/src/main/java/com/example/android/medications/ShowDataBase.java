package com.example.android.medications;

import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ShowDataBase extends AppCompatActivity {
    String[] data = {"Ahmed","Mohamed","El Sherif"};
    ListView l;


    String alarmName = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_data_base);

        myDataBase myDB = new myDataBase(this);

        Cursor res = myDB.getAllData();
        if(res.getCount() == 0)
        {
            showMessage("Error","No data Found");
            return ;
        }

        StringBuffer buffer = new StringBuffer();
        List<String> data2 = new ArrayList<>();
        while (res.moveToNext())
        {
           // buffer.append("ID: " + res.getString(0)+"\n");
           // buffer.append("Name: " + res.getString(1)+"\n");
          //  buffer.append("Time: " + res.getString(2)+"\n");

           // for(int i = 0;i<3;i++)
           // {
           // data2.add(res.getString(0));
            data2.add(res.getString(1));
           // data2.add(res.getString(2));

            //}

        }
        //showMessage("Data", buffer.toString());

       Intent intent = getIntent();
        alarmName = intent.getStringExtra("alarmname");

        l = (ListView) findViewById(R.id.listview);





//        List<String> data1 = new ArrayList<>();
//        data1.add(alarmName);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,R.layout.single_row,R.id.alarmText1,data2);
        l.setAdapter(adapter);
    }

    public void showMessage(String title, String message)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();

    }


}
