package com.example.laskin.room;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import com.example.laskin.entity.Currency;

import java.util.List;

public class CurrencyViewModel extends AndroidViewModel {

    private CurrencyRepository mRepository;
    private LiveData<List<Currency>> mAllCurrencies;
    private LiveData<Currency> activeCurrency;

    public CurrencyViewModel (Application application) {
        super(application);
        mRepository = new CurrencyRepository(application);
        mAllCurrencies = mRepository.getAllCurrenciesLive();
    }

    public LiveData<List<Currency>> getAllCurrencies() { return mAllCurrencies; }

    public Currency getCurrencyById() {
        return mRepository.getCurrencyById();
    }
    public void insert(Currency c) { mRepository.insert(c); }

    public void delete(int id) { mRepository.delete(id); }

    public LiveData<Currency> getActiveCurrency() {
        return activeCurrency;
    }
}
