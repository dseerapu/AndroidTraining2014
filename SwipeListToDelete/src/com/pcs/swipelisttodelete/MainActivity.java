package com.pcs.swipelisttodelete;

import java.util.ArrayList;
import java.util.Arrays;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends SwipeDismissListViewTouchListener{

	private ListView listView;
	private ArrayAdapter<String> adapter;
	private LayoutInflater layoutInflater;
	private AlertDialog.Builder builder;
	private AlertDialog alertDialog;
	private TextView deleteList_yes;
	private TextView deleteList_no;
	private ArrayList<String> data;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_main);
		
		
	String[] data = getResources().getStringArray(R.array.countries);
		 
		
		listView = (ListView)findViewById(R.id.list);
		
		
		adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,new ArrayList<String>(Arrays.asList(data)));
		
		listView.setAdapter(adapter);

	}
	
	public void getSwipeItem(boolean isRight,  final int position) {
		
		if(isRight)
		{
			
		
		//creating DialogBox
		builder = new AlertDialog.Builder(MainActivity.this);
		
		//creating Message to the Dialog Box
		builder.setMessage(getResources().getString(R.string.delete_title) 
				+" "  + adapter.getItem(position).toString())
		
		/***
		 * @param is position of list item
		 * position should bound out of array
		 * setting OnClickListener to the Positive Button in Dialog Box
		 * if Pressed Positive Button then ListView is Deleted from the list
		 */
		.setPositiveButton(getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				
				//Removes Current Item
				adapter.remove(adapter.getItem(position));
				
				//getListAdapter().getItem(position).toString() 
				Toast.makeText(getApplicationContext(), getResources().getString(R.string.deletion_true),Toast.LENGTH_LONG).show();
				
				//dismissing the DialogBox
				alertDialog.dismiss();
				
				}
			})
			
			
		/***
		 * Setting Negative Button to the Dialog Box
		 * creating OnClickListener to the Negative Button in Dialog Box
		 */
		.setNegativeButton(getResources().getString(R.string.no), new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				
				alertDialog.dismiss();
				Toast.makeText(getApplicationContext(),getResources().getString(R.string.deletion_cancel)+ adapter.getItem(position).toString(), Toast.LENGTH_LONG).show();
			}
		});
		
		//creating alertDialog using Builder
		alertDialog =builder.create();
		
		//displaying alertDialog
		alertDialog.show();
		}
		
		else
			Toast.makeText(this,getResources().getString(R.string.swipe_left), Toast.LENGTH_SHORT).show();
	}
	
	public void onItemClickListener(ListAdapter adapter,int position)
	{
		Toast.makeText(this,adapter.getItem(position).toString() ,Toast.LENGTH_SHORT).show();
	}

	@Override
	public ListView getListView() {
		// TODO Auto-generated method stub
		return listView;
	}
}
