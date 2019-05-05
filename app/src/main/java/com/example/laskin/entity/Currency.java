package com.example.laskin.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Calendar;

@Entity(tableName = "currencies")
public class Currency implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private int currencyId;

    @NonNull
    private String currencyName;

    @NonNull
    private Double currencyRelation;

    @NonNull
    private String currencyDate;

    public Currency(@NonNull String currencyName, @NonNull Double currencyRelation, String currencyDate) {
        this.currencyName = currencyName;
        this.currencyRelation = currencyRelation;
        this.currencyDate = currencyDate;
    }

    @Ignore
    public Currency(String currencyName, Double currencyRelation) {
        this.currencyName = currencyName;
        this.currencyRelation = currencyRelation;
        LocalDateTime date = LocalDateTime.now();
        this.currencyDate = date.getYear()  + "-" + date.getMonth().getValue() + "-" + date.getDayOfMonth();
    }

    public int getCurrencyId() {
        return this.currencyId;
    }

    public String getCurrencyName() {
        return this.currencyName;
    }

    public Double getCurrencyRelation() {
        return this.currencyRelation;
    }

    public String getCurrencyDate() {
        return this.currencyDate;
    }

    public void setCurrencyId(int id) {
        this.currencyId = id;
    }

    public void setCurrencyName(String name) {
        this.currencyName = name;
    }

    public void setCurrencyRelation(double relation) {
        this.currencyRelation = relation;
    }
}
