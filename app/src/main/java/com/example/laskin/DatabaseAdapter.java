package com.example.laskin;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;

import java.util.ArrayList;

public class DatabaseAdapter {

    private final Context context;
    private static final String TAG = "DatabaseHelper";
    private static final int DATABASE_VERSION = 2;
    public static final String KEY_ROWID = "_id";
    public static final String DATABASE_NAME = "calculator";

    private DatabaseHelper dbHelper;
    private SQLiteDatabase db;

    //HELPER
    private class DatabaseHelper extends SQLiteOpenHelper {

        private static final String DATABASE_CREATE =
                "CREATE TABLE " + Currency.TABLE_NAME + " ("+
                        Currency._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                        Currency.COL_NAME + " TEXT NOT NULL," +
                        Currency.COL_RELATION + " TEXT NOT NULL," +
                        Currency.COL_DATE + " TEXT NOT NULL);";

        //private SQLiteDatabase db;

        public DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(DATABASE_CREATE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.w(TAG, "Upgrading database from version " + oldVersion
                    + " to "
                    + newVersion + ", which will destroy all old data");

            db.execSQL("DROP TABLE IF EXISTS " + Currency.TABLE_NAME);
            onCreate(db);
        }

        public void clear() {
            db.execSQL("DROP TABLE IF EXISTS " + Currency.TABLE_NAME);
            onCreate(db);
        }
    }

    //Actual Adapter
    public DatabaseAdapter(Context context) {
        dbHelper = new DatabaseHelper(context);
        this.context = context;
    }

    public DatabaseAdapter open() {
        db = dbHelper.getReadableDatabase();
        return this;
    }

    public void close() {
        db.close();
    }

    public long insertCurrency(String name, String relation, String date) {
        ContentValues values = new ContentValues();
        //Map new values for insertion
        values.put(Currency.COL_NAME, name);
        values.put(Currency.COL_RELATION, relation);
        values.put(Currency.COL_DATE, date);
        //Insert the new row, returning the primary key value for the new row
        return db.insert(Currency.TABLE_NAME, null, values);
    }

    public void clearDatabase() {
        dbHelper.clear();
    }

    public ArrayList<Currency> getAllCurrencies() {

        //Projection is what to retrieve form database
        String[] projection = {
                BaseColumns._ID,
                Currency.COL_NAME,
                Currency.COL_RELATION,
                Currency.COL_DATE
        };

        Cursor c = db.query(
                Currency.TABLE_NAME,   // The table to query
                projection,                 // The array of columns to return (pass null to get all)
                null,
                null,
                null,
                null,
                null
        );

        //Go through cursor forming currencies
        ArrayList<Currency> currencyList = new ArrayList<>();
        c.moveToFirst();
        do {
            currencyList.add(new Currency(
                    c.getString(1),
                    Float.parseFloat(c.getString(2)),
                    c.getString(3)));
        } while (c.moveToNext());

        return currencyList;
    }
}
