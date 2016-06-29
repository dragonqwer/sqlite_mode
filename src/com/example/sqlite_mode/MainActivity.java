package com.example.sqlite_mode;


import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener{
	
	private static final String TAG = "MainActivity";
		
	private DatabaseManager dbManager;
	
	private Button btnInsert, btnUpdate;
	
	private ListView lvTasks;
	
	private List<Task> tasks = new ArrayList<Task>();
	
	private List<String> selectedTasks = new ArrayList<String>();
	
	private TaskAdapter taskAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		dbManager = new DatabaseManager(this);
		//1) Query the data
		initData();
		//2) Initialize the components
		initComponents();
		
	}
	
	protected void onDestroy(){
		super.onDestroy();
		dbManager.close();
	}
	
	private void initComponents(){
		btnInsert = (Button)findViewById(R.id.btnInsert);
		btnUpdate = (Button)findViewById(R.id.btnUpdate);
		btnInsert.setOnClickListener(this);
		btnUpdate.setOnClickListener(this);
		
		lvTasks = (ListView)findViewById(R.id.lvTasks);
		lvTasks.setAdapter(taskAdapter);
		lvTasks.setOnItemLongClickListener(onItemLongClickListener);
		lvTasks.setOnItemClickListener(onItemClickListener);
	}		
	
	private void initData(){
		tasks = dbManager.queryAll();
		taskAdapter = new TaskAdapter();		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	private boolean insert(){			
		//Using SQL to insert data
//		database.execSQL(SQL_INSERT, new Object[] {task.getTitle(), task.getContent()});
		//Using ContentValues to insert data
		ContentValues contentValues = new ContentValues();
		contentValues.put("title","TestTask");
		contentValues.put("content", "I'm going to introduce SQLiteDatabase");
		contentValues.put("flagCompleted","N");
		return dbManager.insert(contentValues);
	}
	
	private boolean update(){
		if(selectedTasks.size() > 0){
			for(String taskId : selectedTasks){
				Log.v(TAG, "taskId = " + taskId);
				ContentValues contentValues = new ContentValues();
				contentValues.put("title","Update Task");
				dbManager.update(contentValues, "_id = ? ", new String[] {taskId});
			}
			return true;
		}else{
			Toast.makeText(this, "No selected items!", Toast.LENGTH_SHORT).show();;
			return false;
		}		
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.btnInsert:
			if(insert()){
				tasks = dbManager.queryAll();
				taskAdapter.notifyDataSetChanged();
				Toast.makeText(this, "Insert Successfully", Toast.LENGTH_SHORT).show();
			}
			break;
		case R.id.btnUpdate:
			if(update()){
				tasks = dbManager.queryAll();
				taskAdapter.notifyDataSetChanged();
				Toast.makeText(this, "Update Successfully", Toast.LENGTH_SHORT).show();
			}
			break;
		}
	}	
	
	OnItemLongClickListener onItemLongClickListener = new OnItemLongClickListener() {
		@Override
		public boolean onItemLongClick(AdapterView<?> parent, View view,
				int position, long id) {
			if(dbManager.delete("_id = ? ", new String[] {String.valueOf(id)})){
				tasks = dbManager.queryAll();
				taskAdapter.notifyDataSetChanged();
				Toast.makeText(MainActivity.this, "Delete Successfully", Toast.LENGTH_SHORT).show();
			}
			return false;
		}
	};
	
	OnItemClickListener onItemClickListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			ViewHolder viewHolder = (ViewHolder) view.getTag();
			viewHolder.cbCompleted.toggle();		
			String taskId = String.valueOf(id);
			if(viewHolder.cbCompleted.isChecked()){
				if(!selectedTasks.contains(taskId)){
					selectedTasks.add(taskId);
				}
			}else{
				if(selectedTasks.contains(taskId)){
					selectedTasks.remove(taskId);
				}
			}
		}
	};
	
	class TaskAdapter extends BaseAdapter{
		
		private LayoutInflater layoutInflater;
		
		public TaskAdapter(){
			layoutInflater = LayoutInflater.from(MainActivity.this);
		}
		
		@Override
		public int getCount() {
			return tasks.size();
		}

		@Override
		public Object getItem(int position) {
			return tasks.get(position);
		}

		@Override
		public long getItemId(int position) {
			return tasks.get(position).getId();
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder viewHolder;
			if(convertView == null){
				viewHolder = new ViewHolder();
				convertView = layoutInflater.inflate(R.layout.task_item, null);
				viewHolder.cbCompleted = (CheckBox)convertView.findViewById(R.id.cbCompleted);
				viewHolder.tvTitle = (TextView)convertView.findViewById(R.id.tvTitle);
				convertView.setTag(viewHolder);
			}else{
				viewHolder = (ViewHolder)convertView.getTag();
			}
			viewHolder.cbCompleted.setChecked("Y".equals(tasks.get(position).getFlagCompleted()));
			viewHolder.tvTitle.setText(tasks.get(position).getTitle());			
			return convertView;
		}
		
	}
	
	static class ViewHolder{
		CheckBox cbCompleted;
		TextView tvTitle;		
	}

}

