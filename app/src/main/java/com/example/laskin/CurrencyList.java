package com.example.laskin;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class CurrencyList extends ListActivity {

    private static final String CURRENCIES = "currencies";
    private static final String SELECTION_INDEX = "index";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_currency_list);

        String[] currencies = getIntent().getStringArrayExtra(CURRENCIES);

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1, //takes predefined list into use
                currencies
        );

        setListAdapter(arrayAdapter);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        //Pass selection information back to list caller
        Intent resultIntent = new Intent();
        resultIntent.putExtra(SELECTION_INDEX, position);

        setResult(RESULT_OK, resultIntent);
        finish();
    }
}
