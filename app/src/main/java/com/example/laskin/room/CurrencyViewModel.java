package com.example.laskin.room;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import com.example.laskin.entity.Currency;

import java.util.List;

public class CurrencyViewModel extends AndroidViewModel {

    private CurrencyRepository mRepository;
    private LiveData<List<Currency>> mAllCurrencies;

    public CurrencyViewModel (Application application) {
        super(application);
        mRepository = new CurrencyRepository(application);
        mAllCurrencies = mRepository.getAllCurrencies();
    }

    public LiveData<List<Currency>> getAllCurrencies() { return mAllCurrencies; }

    public void insert(Currency c) { mRepository.insert(c); }

    public void delete(String name) { mRepository.delete(name); }
}
