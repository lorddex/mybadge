package org.ldlabs.mybadge;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.format.Time;

public class MyBadgeDatabase extends SQLiteOpenHelper {

	private static final int DATABASE_VERSION = 2;
	
	private static final String DATABASE_NAME = "MyBadgeDatabase";
	private static final String TIMES_TABLE_NAME = "times";
	private static final String TIMES_KEY_ID = "id";
	private static final String TIMES_KEY_TYPE = "type";
	private static final String TIMES_KEY_DATE = "date";
	private static final String TIMES_KEY_IOTIME = "iotime";
    private static final String DICTIONARY_TABLE_CREATE =
                "CREATE TABLE " + TIMES_TABLE_NAME + " (" +
                TIMES_KEY_ID + " INTEGER PRIMARY KEY, " +
                TIMES_KEY_TYPE + " TEXT, " +
                TIMES_KEY_DATE + " TEXT, " +
                TIMES_KEY_IOTIME + "TEXT);";

	public MyBadgeDatabase(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(DICTIONARY_TABLE_CREATE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
		
	}
	
	public List<Calendar> getCalsOfTheDay(Calendar day) {
		SQLiteDatabase db = this.getReadableDatabase();
		List<Calendar> lst = new ArrayList<Calendar>();
		
	    Cursor cursor = db.query(TIMES_TABLE_NAME, new String[] { TIMES_KEY_ID,
	    		TIMES_KEY_TYPE, TIMES_KEY_DATE, TIMES_KEY_IOTIME }, TIMES_KEY_DATE + "=?",
	            new String[] { String.valueOf(day.get(Calendar.YEAR) + "/" + day.get(Calendar.MONTH) + "/" + day.get(Calendar.DAY_OF_MONTH)) }, null, null, null, null);
	    
	    if (cursor.moveToFirst())
	    {
	    	do 
	    	{
	    		Calendar c = Calendar.getInstance();
	    		c.setTimeInMillis(cursor.getInt(3));
	    		lst.add(c);
	    	} while(cursor.moveToNext());
	    }
	    
		return lst;
	}
	
	// Adding new In time
    public void addIn(Calendar in) {
        SQLiteDatabase db = this.getWritableDatabase();
 
        ContentValues values = new ContentValues();
        values.put(TIMES_KEY_TYPE, "in");
        values.put(TIMES_KEY_DATE, in.get(Calendar.YEAR) + "/" + in.get(Calendar.MONTH) + "/" + in.get(Calendar.DAY_OF_MONTH));
        values.put(TIMES_KEY_IOTIME, in.getTimeInMillis());

        db.insert(TIMES_TABLE_NAME, null, values);
        db.close();
    }

}

