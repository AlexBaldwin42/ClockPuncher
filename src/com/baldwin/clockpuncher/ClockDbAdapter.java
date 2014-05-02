package com.baldwin.clockpuncher;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class ClockDbAdapter {



	private DbHelper ourHelper;
	private final Context ourContext;
	private SQLiteDatabase ourDatabase;
    private long rowId;

	private static class DbHelper extends SQLiteOpenHelper {
        public static final String KEY_ROWID = "_id";
        public static final String KEY_TIMEIN = "time_in";
        public static final String KEY_TIMEOUT = "time_out";
        public static final String KEY_TOTAL_TIME = "total_time";

        private static final String DATABASE_NAME = "timeClock";
        private static final String DATABASE_TABLE = "timeTable";
        private static final int DATABASE_VERSION = 1;
        public static  final String CREATE_TABLE = "CREATE TABLE " + DATABASE_TABLE + " (" + KEY_ROWID
                + " INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_TIMEIN
                + " TEXT NOT NULL, " + KEY_TIMEOUT
                + " TEXT);";

		public DbHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
			// TODO Auto-generated constructor stub
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			// TODO Auto-generated method stub
			// Creating the database

            try {
                db.execSQL(CREATE_TABLE);
            } catch (SQLException e) {
                e.printStackTrace();
            }


        }

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			// TODO Auto-generated method stub
			db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
			onCreate(db);
		}
	}

	public ClockDbAdapter(Context c) {
		ourContext = c;
	}

	public ClockDbAdapter open() throws SQLException {
		ourHelper = new DbHelper(ourContext);
		ourDatabase = ourHelper.getWritableDatabase();
		Log.d("open", "openingdb");
		return this;
	}

	public void close() {
		Log.d("close", "closing");
		ourDatabase.close();
		ourHelper.close();

	}

	public long createEntryTimeIn(String timeIn) {
		// TODO Auto-generated method stub
		Log.d("createEntryTimeIn", timeIn);
		ContentValues cv = new ContentValues();
		cv.put(DbHelper.KEY_TIMEIN, timeIn);
        rowId = ourDatabase.insert(DbHelper.DATABASE_TABLE, null, cv);
		return rowId;

	}
	
	//Need to have this method find the last record that doesn't have a punched time
	//And insert into that row
	public long createEntryTimeOut(String timeOut) {
		// TODO Auto-generated method stub
        Log.d("createEntryTimeOut", timeOut);
		ContentValues cv = new ContentValues();
		cv.put(DbHelper.KEY_TIMEOUT, timeOut);
        Log.d("createEntryTimeOut", "rowId= "+ Long.toString(rowId));

        return ourDatabase.update(DbHelper.DATABASE_TABLE, cv, "_id =" + rowId, null);

	}

    //Method that creates a row with both time in and time out
    public long createEntry(String timeIn, String timeOut){
        Log.d("Create Entry", timeIn + " - " + timeOut);
        ContentValues cv = new ContentValues();
        cv.put(DbHelper.KEY_TIMEIN, timeIn);
        cv.put(DbHelper.KEY_TIMEOUT, timeOut);

        return ourDatabase.insert(DbHelper.DATABASE_TABLE, null, cv);


    }



	public String getData() {
		// TODO Auto-generated method stub
		String[] columns = new String[] {DbHelper.KEY_ROWID, DbHelper.KEY_TIMEIN, DbHelper.KEY_TIMEOUT, DbHelper.KEY_TOTAL_TIME};
		Cursor c = ourDatabase.query(DbHelper.DATABASE_TABLE, columns, null, null, null, null,null);
		String result = "";
		
		int iRow = c.getColumnIndex(DbHelper.KEY_ROWID);
		int iTimeIn = c.getColumnIndex(DbHelper.KEY_TIMEIN);
		int iTimeOut = c.getColumnIndex(DbHelper.KEY_TIMEOUT);
		int iTotalTime = c.getColumnIndex(DbHelper.KEY_TOTAL_TIME);
		//need to return this as separate values of string and long
		for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()){
			result = result + c.getString(iRow) + " " + String.valueOf(c.getLong(iTimeIn)) + " " + String.valueOf(c.getLong(iTimeOut)) + " " + String.valueOf(c.getLong(iTotalTime)) +"\n";
			
			
		}
		return result;
	}
}
