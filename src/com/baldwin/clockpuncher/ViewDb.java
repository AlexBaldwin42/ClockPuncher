package com.baldwin.clockpuncher;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class ViewDb extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

        setContentView(R.layout.viewdb);
		TextView tv = (TextView) findViewById(R.id.tvSQLinfo);
		ClockDbAdapter info = new ClockDbAdapter(this);

        //Retrieve all the data in the database
        info.open();
		String data = info.getAllData();
		info.close();

        //Set the text in the view to the data Would be better to display in a list
        //TODO Display in a date format instead of milliseconds.
		tv.setText(data);
	}
}
