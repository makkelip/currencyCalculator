package com.example.laskin.room;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import java.util.List;

import com.example.laskin.entity.Currency;


@Dao
public interface CurrencyDao {

    @Insert
    void insert(Currency currency);

    @Query("DELETE FROM currencies")
    void deleteAll();

    @Query("SELECT * from currencies ORDER BY currencyName ASC")
    LiveData<List<Currency>> getAllCurrencies();

    @Query("DELETE FROM currencies WHERE currencyName=:name")
    void delete(String name);

}
