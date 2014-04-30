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
		Clock info = new Clock(this);
		info.open();
		String data = info.getData();
		info.close();
		tv.setText(data);
	}
}
