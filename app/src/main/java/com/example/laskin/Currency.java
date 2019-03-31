package com.example.laskin;

import android.provider.BaseColumns;

class Currency implements BaseColumns {
    public static final String TABLE_NAME = "currency";
    public static final String COL_NAME = "name";
    public static final String COL_RELATION = "relation";
    public static final String COL_DATE = "date";

    private String name;
    private float relation;
    private String date;

    public Currency(String name, float relation, String date) {
        this.name = name;
        this.relation = relation;
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public float getRelation() {
        return relation;
    }

    public String getDate() {
        return date;
    }
}