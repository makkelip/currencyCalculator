package com.example.laskin.room;


import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import com.example.laskin.entity.Currency;

import java.util.Arrays;
import java.util.List;

@Database(entities = {Currency.class}, version = 1)
public abstract class CurrencyDatabase extends RoomDatabase {

    public abstract CurrencyDao currencyDao();

    private static CurrencyDatabase INSTANCE;

    public static CurrencyDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (CurrencyDatabase.class) {
                if (INSTANCE == null) {
                    // Create database here
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            CurrencyDatabase.class, "currency_database")
                            .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback sRoomDatabaseCallback =
            new RoomDatabase.Callback(){

                @Override
                public void onOpen (@NonNull SupportSQLiteDatabase db){
                    super.onOpen(db);
                    new PopulateDbAsync(INSTANCE).execute();
                }
            };

    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final CurrencyDao mDao;

        PopulateDbAsync(CurrencyDatabase db) {
            mDao = db.currencyDao();
        }

        @Override
        protected Void doInBackground(final Void... params) {
            mDao.deleteAll();
            List<Currency> currencies = Arrays.asList(
                    new Currency(
                            "USD",
                            1.0513, // 1 euro = 1,0513 dollars
                            "6.11.2018"),
                    new Currency(
                            "GBP",
                            0.844,
                            "6.11.2018"),
                    new Currency(
                            "SEK",
                            9.47,
                            "6.11.2018")
            );

            for (Currency c : currencies)
                mDao.insert(c);

            return null;
        }
    }
}

