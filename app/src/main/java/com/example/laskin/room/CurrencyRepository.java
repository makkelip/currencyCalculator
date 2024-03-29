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

    public void delete (int id) { new deleteAsyncTask(mCurrencyDao).execute(id); }

    public void insertMultiple(List<Currency> currencyList) { new insertMultipleAsyncTask(mCurrencyDao).execute(currencyList); }

    private static class insertAsyncTask extends AsyncTask<Currency, Void, Void> {
        private CurrencyDao mAsyncTaskDao;
        insertAsyncTask(CurrencyDao dao) {
            mAsyncTaskDao = dao;
        }
        @Override
        protected Void doInBackground(final Currency... params) {
            Currency dbCurr = mAsyncTaskDao.getCurrencyByName(params[0].getCurrencyName());
            if (dbCurr == null)
                mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }

    private static class insertMultipleAsyncTask extends AsyncTask<List<Currency>, Void, Void> {
        private CurrencyDao mAsyncTaskDao;
        insertMultipleAsyncTask(CurrencyDao dao) {
            mAsyncTaskDao = dao;
        }
        @Override
        protected Void doInBackground(final List<Currency>... params) {
            Currency dbCurr;
            for (Currency curr: params[0]) {
                dbCurr = mAsyncTaskDao.getCurrencyByName(curr.getCurrencyName());
                if (dbCurr == null)
                    mAsyncTaskDao.insert(curr);
            }
            return null;
        }
    }

    private static class deleteAsyncTask extends AsyncTask<Integer, Void, Void> {

        private CurrencyDao mAsyncTaskDao;

        deleteAsyncTask(CurrencyDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Integer... params) {
            mAsyncTaskDao.delete(params[0]);
            return null;
        }
    }

}