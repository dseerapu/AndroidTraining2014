package com.pcs.swipelistviewactivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;

public abstract class SwipeListViewActivity extends Activity{

	private ListView listview;
	private int REL_SWIPE_MIN_DISTANCE;
	private int REL_SWIPE_MAX_OFF_PATH;
	private int REL_SWIPE_THRESOLD_VELOCITY;

	//returns listview
	public abstract ListView getListView();

	//For each List Item which is swiped
	public void getSwipeItem(boolean isRight, int position) {

	}

	public abstract void onItemClickListener(ListAdapter adapter, int position);

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);


		DisplayMetrics displayMetrics = getResources().getDisplayMetrics();

		REL_SWIPE_MIN_DISTANCE = (int)(120.0f * displayMetrics.densityDpi/160.0f+0.5);
		REL_SWIPE_MAX_OFF_PATH = (int)(120.0f * displayMetrics.densityDpi/160.0f+0.5);
		REL_SWIPE_THRESOLD_VELOCITY = (int)(200.0f*displayMetrics.densityDpi/160.0f+0.5);


	}

	@Override
	protected void onResume() {
		super.onResume();

		listview = getListView();

		/***
		 * Checks for listview is null or not
		 * if null throws ListView not set Exception
		 */
		
		if(listview==null)
		{
			new Throwable("ListView not Set Exception");

		}
		
		
		/***
		 * Gesture Detector
		 */
		@SuppressWarnings("deprecation")
		final GestureDetector gestureDetector = new GestureDetector(new MyGestureDetector());

		View.OnTouchListener gestureListener  = new View.OnTouchListener() {

			@SuppressLint("ClickableViewAccessibility")
			@Override
			public boolean onTouch(View v, MotionEvent event) {


				return gestureDetector.onTouchEvent(event);
			}
		};

		listview.setOnTouchListener(gestureListener);



	}

	private void myOnItemClick(int position)
	{
		if(position <0)
		{
			return;
		}
		onItemClickListener(listview.getAdapter(), position);
	}


	class MyGestureDetector extends SimpleOnGestureListener{

		private int temp_position=1;

		public boolean onSingleTapUp(MotionEvent e)
		{

			int item_position = listview.pointToPosition((int)e.getX(), (int)e.getY());
			myOnItemClick(item_position);

			return true;

		}

		public boolean onDown(MotionEvent e)
		{

			temp_position = listview.pointToPosition((int)e.getX(), (int)e.getY());
			return super.onDown(e);
		}

		public boolean onFling(MotionEvent e1, MotionEvent e2, float VelocityX, float VelocityY)
		{

			if(Math.abs(e1.getY()-e2.getY()) > REL_SWIPE_MAX_OFF_PATH)
				return true;

			if(e1.getX() - e2.getX() > REL_SWIPE_MIN_DISTANCE && Math.abs(VelocityX)>REL_SWIPE_THRESOLD_VELOCITY)
			{
				int pos = listview.pointToPosition((int) e1.getX(), (int)e2.getY());
				if(pos>0 && temp_position==pos)
				{
					getSwipeItem(false, pos);
				}
			}
			
			
			else if(e2.getX() - e1.getX() > REL_SWIPE_MIN_DISTANCE
					&& Math.abs(VelocityX) > REL_SWIPE_THRESOLD_VELOCITY)
			{
				int pos = listview.pointToPosition((int) e1.getX(), (int) e2.getY());
						if (pos >= 0 && temp_position == pos)
						getSwipeItem(true, pos);
			}
				return false;
		}
	}
}
