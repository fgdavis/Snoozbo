package com.example.snoozbo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class SnoDbHandler extends SQLiteOpenHelper{

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "snoDB.db";
    private static final String TABLE_ALARM = "ALARM";
    private static final String COLUMN_ID = "ID";
    private static final String COLUMN_SNOOZE = "SNOOZE";

    public SnoDbHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version)
    {
        //super calls SQLiteOpenHelper
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_ALARM_TABLE = "CREATE TABLE " +
                TABLE_ALARM + "(" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"+ COLUMN_SNOOZE
                + " INT DEFAULT 0," + ")";
        db.execSQL(CREATE_ALARM_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ALARM);
        onCreate(db);
    }

    public void addSnooze(Alarm alarm) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_SNOOZE, alarm.getSnooze());

        db.insert(TABLE_ALARM, null, values);
        db.close();
    }//end of addHandler

    public String loadAlarm() {
        String result = "";
        String query = "Select*FROM " + TABLE_ALARM;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        while (cursor.moveToNext()) {
            String snooze = cursor.getString(0);

            result += snooze + System.getProperty("line.separator");
        }
        cursor.close();
        db.close();
        return result;
    }//end of load

    public boolean updateSnooze(int snooze) {

        String id = String.valueOf(countAlarm());
        int pri = countAlarm();
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_ID, pri);
        cv.put(COLUMN_SNOOZE, snooze);

        return db.update(TABLE_ALARM, cv, "ID =?" + id, new String[] {id}) > 0;


    }

    public int countAlarm() {
        int count = 0;

        String sql = "SELECT COUNT(*) FROM " + TABLE_ALARM;

        Cursor cursor = getReadableDatabase().rawQuery(sql, null);

        if(cursor.getCount() > 0) {
            cursor.moveToFirst();
            count = cursor.getInt(0);
        }
        cursor.close();
        return count;
    }

    public boolean checkSnooze() {
        boolean tf = false;
        int total = countAlarm();
        String sql = "SELECT SUM(" + COLUMN_SNOOZE + ") FROM " + TABLE_ALARM + " WHERE COLUMN_ID >= total - 5";
        String que = "SELECT SUM(" + COLUMN_SNOOZE + ") FROM " + TABLE_ALARM + " WHERE COLUMN_ID >= total - 3";

        Cursor cursor = getReadableDatabase().rawQuery(sql, null);
        Cursor cur = getReadableDatabase().rawQuery(que, null);
        if(cur.moveToFirst()){
            if(cur.getInt(0) > 2) {
                tf = true;
            }
        }
        if(cursor.moveToFirst()){
            if(cursor.getInt(0) > 3) {
                tf = true;
            }
        }
        cursor.close();
        cur.close();

        return tf;
    }


}
