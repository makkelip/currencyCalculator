package com.example.laskin.room;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.example.laskin.entity.Currency;

import java.util.List;

// repo adds a Currency and get all the Currencies
public class CurrencyRepository {

    private CurrencyDao mCurrencyDao;
    private LiveData<List<Currency>> mAllCurrencies;

    CurrencyRepository(Application application) {
        CurrencyDatabase db = CurrencyDatabase.getDatabase(application);
        mCurrencyDao = db.currencyDao();
        mAllCurrencies = mCurrencyDao.getAllCurrencies();
    }

    LiveData<List<Currency>> getAllCurrenciesLive() {
        return mAllCurrencies;
    }

    public void insert (Currency c) {
        new insertAsyncTask(mCurrencyDao).execute(c);
    }

    public void delete (String name) { new deleteAsyncTask(mCurrencyDao).execute(name); }

    public LiveData<Currency> getCurrencyById() {
        return mCurrencyDao.getCurrencyById();
    }

    private static class insertAsyncTask extends AsyncTask<Currency, Void, Void> {
        private CurrencyDao mAsyncTaskDao;
        insertAsyncTask(CurrencyDao dao) {
            mAsyncTaskDao = dao;
        }
        @Override
        protected Void doInBackground(final Currency... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }

    private static class deleteAsyncTask extends AsyncTask<String, Void, Void> {

        private CurrencyDao mAsyncTaskDao;

        deleteAsyncTask(CurrencyDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final String... params) {
            mAsyncTaskDao.delete(params[0]);
            return null;
        }
    }

}