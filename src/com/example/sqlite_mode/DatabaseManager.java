package com.example.sqlite_mode;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class DatabaseManager {
	
	private static final String TAG = "DatabaseManager";

	private SQLiteDatabase database;
	
//	private static final String TABLE_NAME = "tasks";
	
	private static final String SQL_DROP_TABLE = "drop table if exists " + DatabaseHelper.TABLE_NAME;

	private static final String SQL_INSERT = "insert into " + DatabaseHelper.TABLE_NAME + " values (NULL, ?, ?, ?)";
	
	private static final String SQL_QUERY = "select * from " + DatabaseHelper.TABLE_NAME;							 
		
	public DatabaseManager(Context context){
		DatabaseHelper databaseHelper = new DatabaseHelper(context);
		database = databaseHelper.getWritableDatabase();		
	}	
	
	public void close(){
		database.close();
	}
	
	public void dropTable(){
		database.execSQL(SQL_DROP_TABLE);
	}
	
	public boolean insert(Object[] values){
		database.execSQL(SQL_INSERT,values);
		return true;
	}
	
	public boolean insert(ContentValues contentValues){
		database.insert(DatabaseHelper.TABLE_NAME, null, contentValues);
		return true;
	}
	
	public boolean update(ContentValues contentValues, String whereClause, String[] whereArgs){
		database.update(DatabaseHelper.TABLE_NAME, contentValues, whereClause, whereArgs);
		return true;
	}
	
	public boolean delete(String whereClause, String[] whereArgs){		
		database.delete(DatabaseHelper.TABLE_NAME, whereClause, whereArgs);
		return true;
	}
	
	public List<Task> queryAll(){
		List<Task> list = new ArrayList<Task>();
		
		Cursor cursor = database.rawQuery(SQL_QUERY, null);
		if(cursor == null){
			
		}else if(!cursor.moveToFirst()){
			
		}else{
			int columnId = cursor.getColumnIndex("_id");
			int columnTitle = cursor.getColumnIndex("title");
			int columnContent = cursor.getColumnIndex("content");
			int columnFlagCompleted = cursor.getColumnIndex("flagCompleted");
			
			do{
				int id = cursor.getInt(columnId);
				String title = cursor.getString(columnTitle);
				String content = cursor.getString(columnContent);
				String flagCompleted = cursor.getString(columnFlagCompleted);
				
				Task task = new Task();
				task.setId(id);
				task.setTitle(title);
				task.setContent(content);
				task.setFlagCompleted(flagCompleted);
				
				list.add(task);
				
			}while(cursor.moveToNext());
		}
		cursor.close();
		Log.v(TAG, "Total Records : " + list.size());
		return list;
	}
}
