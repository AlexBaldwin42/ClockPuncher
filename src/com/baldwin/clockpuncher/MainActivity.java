package com.baldwin.clockpuncher;

import java.util.Calendar;
import java.util.Date;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity implements OnClickListener {

	Button punchIn, punchOut, viewDb;
	TextView timeIn, timeOut, clockedTime;
	int timePunched;
	long punchedTime;
	long punchedHours;
	Date dateHelperIn, dateHelperOut;
	Calendar calHelperIn, calHelperOut;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		punchIn = (Button) findViewById(R.id.bPunchIn);
		punchOut = (Button) findViewById(R.id.bPunchOut);
		viewDb = (Button) findViewById(R.id.bViewDb);
		timeIn = (TextView) findViewById(R.id.tvTimeIn);
		timeOut = (TextView) findViewById(R.id.tvTimeOut);
		clockedTime = (TextView) findViewById(R.id.tvClockedTime);

		punchIn.setOnClickListener(this);
		punchOut.setOnClickListener(this);
		viewDb.setOnClickListener(this);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub


		switch (v.getId()) {

		case R.id.bPunchIn:

            calHelperIn = Calendar.getInstance();
			Shift shiftEntry = new Shift();
			shiftEntry.setTimeIn(calHelperIn.getTimeInMillis());

			Log.d("punchIn", Integer.toString(calHelperIn.get(Calendar.HOUR)));
			
			timeIn.setText(calHelperIn.getTime().toString().substring(4,19));

		break;

		case R.id.bPunchOut:
            ClockDbAdapter entry = new ClockDbAdapter(MainActivity.this);
            calHelperOut = Calendar.getInstance();
			calHelperOut.setTime(calHelperOut.getTime());
            //Set strings up to be sent to database.
            String strTimeIn = Long.toString(calHelperIn.getTimeInMillis());
            String strTimeOut = Long.toString(calHelperOut.getTimeInMillis());

            //Set the display taking off the first and last parts
            timeOut.setText(calHelperOut.getTime().toString().substring(4, 19));

            //create database entry
			entry.open();
			entry.createEntry(strTimeIn, strTimeOut);
			entry.close();
			
			Log.d("calhelperin", Long.toString(calHelperIn.getTimeInMillis()));
            Log.d("calgelperOut",Long.toString(calHelperOut.getTimeInMillis()));
            //Calculate the amount of time that's past between punches.
            punchedTime = calHelperOut.getTimeInMillis() - calHelperIn.getTimeInMillis();
            Log.d("punchout", Long.toString(punchedTime));
			//Converts punched time from milliseconds to minutes.
            punchedTime = punchedTime / 1000 / 60;

			 if (punchedTime > 60) {

				punchedHours = punchedTime / 60;
				punchedTime = punchedTime % 60;

				clockedTime.setText(String.valueOf(punchedHours) + ":"
						+ String.valueOf(punchedTime));
			} else {
				clockedTime.setText(String.valueOf(punchedTime) + " Mins");

			}
		break;
			
		case R.id.bViewDb:
			Intent i = new Intent("com.baldwin.clockpuncher.VIEWDB");
			startActivity(i);
			break;
		}

	}
}
