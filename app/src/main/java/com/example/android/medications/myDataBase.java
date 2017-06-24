package com.example.android.medications;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Abshafi on 6/25/2016.
 */
public class myDataBase extends SQLiteOpenHelper {

    public static final String dbName = "Alarms.db";
    public static final String tableName = "Alarms_table";

    public static final String COL_1 = "ID";
    public static final String COL_2 = "Name";
    public static final String COL_3 = "Time";




    public myDataBase(Context context)
    {
        super(context,dbName,null,1);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("Create table " + tableName + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, NAME TEXT, TIME STRING ) ");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF IT EXISTS "+tableName);
        onCreate(db);

    }

    public boolean insertData(String name, String time)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2,name);
        contentValues.put(COL_3, time);
        long result = db.insert(tableName,null,contentValues);

        if(result == -1)
            return false;
        else
            return true;

    }

    public Cursor getAllData()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+tableName,null);

        return res;

    }

    public Integer deleteDB(String name)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(tableName,"Name = ?",new String[] { name});
    }

    public Integer clearDB()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(tableName,null,null);
    }
}
