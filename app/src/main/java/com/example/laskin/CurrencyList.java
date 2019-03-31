package com.example.laskin;

import android.app.ListActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;

public class CurrencyList extends ListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_currency_list);

        String[] currencies = new String[] {"USD", "HUF", "GBP"};

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1, //takes predefined list into use
                currencies
        );

        setListAdapter(arrayAdapter);
    }
}
