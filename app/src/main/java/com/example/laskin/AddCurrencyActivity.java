package com.example.laskin;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

import com.example.laskin.entity.Currency;
import com.example.laskin.room.CurrencyViewModel;

public class AddCurrencyActivity extends AppCompatActivity {

    private CurrencyViewModel currencyViewModel;

    private EditText nameEdit;
    private EditText relationEdit;
    private EditText dateEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_currency);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        currencyViewModel = ViewModelProviders.of(this).get(CurrencyViewModel.class);

        nameEdit = findViewById(R.id.currencyNameInput);
        relationEdit = findViewById(R.id.relationInput);
        dateEdit = findViewById(R.id.dateInput);
    }

    public void addCurrency(View view) {
        String name = nameEdit.getText().toString();
        Double relation = Double.valueOf(relationEdit.getText().toString());
        String date = dateEdit.getText().toString();

        Currency currency = new Currency(name, relation, date);
        currencyViewModel.insert(currency);

        // finish the activity
        setResult(RESULT_OK);
        finish();
    }

}
