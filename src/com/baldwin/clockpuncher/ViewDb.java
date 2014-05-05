package com.baldwin.clockpuncher;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.util.Calendar;

public class ViewDb extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        long lRow;
        long lTimeIn;
        long lTimeOut;
        String sTimeIn;
        String sTimeOut;
        String sTotalTime;
        long lClockedTime;
        long lTotalTime;
        long lTotalHours;

        String display = "";
        Calendar calHelptimeIn = Calendar.getInstance();
        Calendar calHelptimeOut = Calendar.getInstance();

        setContentView(R.layout.viewdb);
        TextView tvTotal = (TextView) findViewById(R.id.tvTotal);
        TextView tvIn = (TextView) findViewById(R.id.tvIn);
        TextView tvOut = (TextView) findViewById(R.id.tvOut);

        ClockDbAdapter info = new ClockDbAdapter(this);

        //Retrieve all the data in the database
        info.open();
        String data = info.getAllData();
        info.close();

        //TODO loop through data string extracting the dates,
        //First extract each row. Then Split that into row timeIn timeOut
        // change time in and out into dates and times.
        String[] Entries = data.split("\n");
        for (int i = 0; i < Entries.length; i++) {

            String[] sRow = Entries[i].split(" ");
            if (sRow.length == 3) {

                lRow = Long.parseLong(sRow[0]);
                lTimeIn = Long.parseLong(sRow[1]);
                lTimeOut = Long.parseLong(sRow[2]);
                lTotalTime = lTimeOut - lTimeIn;


                calHelptimeIn.setTimeInMillis(lTimeIn);
                calHelptimeOut.setTimeInMillis(lTimeOut);
                sTimeIn = calHelptimeIn.getTime().toString().substring(4, 19);
                sTimeOut = calHelptimeOut.getTime().toString().substring(4, 19);

                Log.d("viewdb lTimeOut=", ""+ lTimeOut);
                tvIn.setText(tvIn.getText() + "\n" + sTimeIn);
                if(lTimeOut > 0) {
                    tvOut.setText(tvOut.getText() + "\n" + sTimeOut);

                    lTotalTime = lTotalTime / 1000 / 60;
                    if (lTotalTime > 60) {

                        lTotalHours = lTotalTime / 60;
                        lTotalTime = lTotalTime % 60;

                        tvTotal.setText(tvTotal.getText() + "\n"
                                + String.valueOf(lTotalHours) + ":"
                                + String.valueOf(lTotalTime));
                    } else {
                        tvTotal.setText(tvTotal.getText() + "\n"
                                + String.valueOf(lTotalTime) + " Minutes");

                    }
                }else{
                    //There is no time out.
                    tvOut.setText(tvOut.getText() + "\n" + "Still Clocked in");
                    tvTotal.setText(tvTotal.getText() + "\n" );

                }


            }

        }
    }
}
