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



    public ClockDbAdapter(Context c) {
        ourContext = c;
    }

    public ClockDbAdapter open() throws SQLException {
        ourHelper = new DbHelper(ourContext);
        ourDatabase = ourHelper.getWritableDatabase();
        Log.d("ClockDBAdapter", "openingdb");
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
        cv.put(DbHelper.KEY_TIME_IN, timeIn);
        rowId = ourDatabase.insert(DbHelper.DATABASE_TABLE, null, cv);
        return rowId;

    }

    //
    public long createEntryTimeOut(String timeOut) {
        // TODO Need to have this method find the last record that doesn't have a punched time And insert into that row
        //
        Log.d("createEntryTimeOut", timeOut);
        ContentValues cv = new ContentValues();
        cv.put(DbHelper.KEY_TIME_OUT, timeOut);
        Log.d("createEntryTimeOut", "rowId= " + Long.toString(rowId));

        return ourDatabase.update(DbHelper.DATABASE_TABLE, cv, "_id =" + rowId, null);
    }
    //Method that creates a row with both time in and time out
    public long createEntry(String timeIn, String timeOut) {
        Log.d("Create Entry", timeIn + " - " + timeOut);
        ContentValues cv = new ContentValues();
        cv.put(DbHelper.KEY_TIME_IN, timeIn);
        cv.put(DbHelper.KEY_TIME_OUT, timeOut);

        return ourDatabase.insert(DbHelper.DATABASE_TABLE, null, cv);


    }


    public String getAllData() {
        // TODO could simplify this with a while loop.
        String[] columns = new String[]{DbHelper.KEY_ROW_ID, DbHelper.KEY_TIME_IN, DbHelper.KEY_TIME_OUT};
        Cursor c = ourDatabase.query(DbHelper.DATABASE_TABLE, columns, null, null, null, null, null);
        String result = "";

        int iRow = c.getColumnIndex(DbHelper.KEY_ROW_ID);
        int iTimeIn = c.getColumnIndex(DbHelper.KEY_TIME_IN);
        int iTimeOut = c.getColumnIndex(DbHelper.KEY_TIME_OUT);

        //need to return this as separate values of string and long
        //TODO Possibly return a Shift object array or list.
        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            result = result + c.getString(iRow) + " " + String.valueOf(c.getLong(iTimeIn)) + " " + String.valueOf(c.getLong(iTimeOut)) +  "\n";

        }
        return result;
    }

    private static class DbHelper extends SQLiteOpenHelper {
        public static final String KEY_ROW_ID = "_id";
        public static final String KEY_TIME_IN = "time_in";
        public static final String KEY_TIME_OUT = "time_out";

        private static final String DATABASE_NAME = "timeClock";
        private static final String DATABASE_TABLE = "timeTable";
        private static final int DATABASE_VERSION = 1;
        public static final String CREATE_TABLE = "CREATE TABLE " + DATABASE_TABLE + " (" + KEY_ROW_ID
                + " INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_TIME_IN
                + " TEXT NOT NULL, " + KEY_TIME_OUT
                + " TEXT);";

        public DbHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
            // TODO Auto-generated constructor stub
        }

        @Override
        public void onCreate(SQLiteDatabase db) {

            // Creating the database
            try {
                db.execSQL(CREATE_TABLE);
            } catch (SQLException e) {
                e.printStackTrace();
            }


        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

            try {
                db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
                onCreate(db);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}