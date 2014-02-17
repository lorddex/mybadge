package org.ldlabs.mybadge;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.example.mybadge.R;

import android.os.Bundle;
import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends Activity 
{
	
	private TextView lastInText;
	private TextView balance;
	private Calendar lastIn;
	private MyBadgeDatabase db;
			
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		db = new MyBadgeDatabase(this);
		lastInText = (TextView) findViewById(R.id.textViewLastIn);
		balance = (TextView) findViewById(R.id.textViewBalance);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) 
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public void badgeIn(View view)
	{
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
		lastIn = Calendar.getInstance();
		String currentDateandTime = sdf.format(lastIn.getTimeInMillis());
		lastInText.setText(currentDateandTime);
	}
	
	public void badgeOut(View view)
	{
		Calendar now = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss");
		String currentDateandTime = sdf.format(now.getTimeInMillis() - lastIn.getTimeInMillis());
		balance.setText(currentDateandTime);
	}
	
}
