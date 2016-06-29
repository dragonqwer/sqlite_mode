package com.example.sqlite_mode;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper{
    
    private static final String TAG = "DatabaseHelper";

    private static final String DB_NAME = "tiles.db";
    
    public static final String TABLE_NAME = "tile";
    
    private static final String SQL_CREATE_TABLE = "create table if not exists " + TABLE_NAME 
                         + " ( _id integer primary key autoincrement, "
                         + "   title varchar(30),"
                         + "   content varchar(200), "
                         + "   flagCompleted char(1) "
                         + "  )";
    
    private static final int DATABASE_VERSION = 1;  
    
    public DatabaseHelper(Context context){     
        super(context, DB_NAME, null, DATABASE_VERSION);
        Log.v(TAG, "DatabaseHelper Contructor.");
    }
    
    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.v(TAG, "DatabaseHelper On Create.");
        db.execSQL(SQL_CREATE_TABLE);
    }   

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Now you don't need to anything
    }

}
