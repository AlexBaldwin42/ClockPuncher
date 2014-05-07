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
import android.widget.Toast;

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

    }

    @Override
    protected void onPause() {
        super.onPause();

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("strTimeIn", timeIn.getText().toString());
        outState.putString("strTimeOut", timeOut.getText().toString());
        outState.putString("strClockedTime", clockedTime.getText().toString());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        timeIn.setText(savedInstanceState.getString("strTimeIn"));
        timeOut.setText(savedInstanceState.getString("strTimeOut"));
        clockedTime.setText(savedInstanceState.getString("strClockedTime"));
    }

    @Override
    protected void onResume() {
        super.onResume();
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

        ClockDbAdapter db = new ClockDbAdapter(MainActivity.this);
        String timePunched;
        String output;
        long lTimeOut;

        switch (v.getId()) {

            case R.id.bPunchIn:
                db.open();
                lTimeOut = db.lastTimeOut();
                db.close();

                Log.d("Clockin in button clicked lTimeOut= ", lTimeOut + "");

                if (lTimeOut > 0) {
                    calHelperIn = Calendar.getInstance();
                    Shift shiftEntry = new Shift();
                    shiftEntry.setTimeIn(calHelperIn.getTimeInMillis());

                    db.open();
                    db.createEntryTimeIn(Long.toString(calHelperIn.getTimeInMillis()));
                    db.close();

                    Log.d("punchIn", Integer.toString(calHelperIn.get(Calendar.HOUR)));

                    timePunched = calHelperIn.getTime().toString().substring(4, 19);
                    output = timeIn.getText().toString() + "\n" + timePunched;
                    timeIn.setText(output);
                } else {
                    Toast.makeText(this, "You have not punched out", 2).show();


                }

                break;

            case R.id.bPunchOut:

                calHelperOut = Calendar.getInstance();
                //calHelperOut.setTime(calHelperOut.getTime());
                db.open();
                long lTimeIn = db.lastTimeIn();
                lTimeOut = db.lastTimeOut();
                Log.d("punchOut lTimeIn", "" + lTimeIn);
                Log.d("punchOut lTimeOut", "" + lTimeOut);
                db.close();
                if (lTimeOut > 0) {
                    Toast.makeText(this, "You have not punched in", 2).show();
                } else {
                    //Set strings up to be sent to database.

                    lTimeOut = calHelperOut.getTimeInMillis();
                    String strTimeOut = Long.toString(lTimeOut);

                    //Set the display taking off the first and last parts
                    timePunched = calHelperOut.getTime().toString().substring(4, 19);
                    output = timeOut.getText().toString() + "\n" + timePunched;
                    timeOut.setText(output);

                    //create database db
                    db.open();
                    db.createEntryTimeOut(strTimeOut);
                    db.close();

                    Log.d("lTimeIn", Long.toString(lTimeIn));
                    Log.d("calgelperOut", Long.toString(lTimeOut));
                    //Calculate the amount of time that's past between punches.
                    punchedTime = lTimeOut - lTimeIn;
                    Log.d("punchout", Long.toString(punchedTime));
                    //Converts punched time from milliseconds to minutes.
                    punchedTime = punchedTime / 1000 / 60;

                    if (punchedTime > 60) {

                        punchedHours = punchedTime / 60;
                        punchedTime = punchedTime % 60;

                        clockedTime.setText(clockedTime.getText() + "\n"
                                + String.valueOf(punchedHours) + ":"
                                + String.valueOf(punchedTime));
                    } else {
                        clockedTime.setText(clockedTime.getText() + "\n" + String.valueOf(punchedTime) + " Mins");

                    }
                }
                break;
        }

    }

    //Open the activity ViewDb
    public void viewDb(View v) {
        Intent i = new Intent(this, ViewDb.class);
        startActivity(i);

    }
}
