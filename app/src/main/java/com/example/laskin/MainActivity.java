package com.example.laskin;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.laskin.entity.Currency;
import com.example.laskin.room.CurrencyViewModel;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private static final int LIST_REQ_CODE = 1;
    private static final String SELECTION_INDEX = "index";

    private TextView lowerText;
    private EditText upperEditText;
    private EditText lowerEditText;

    private CurrencyViewModel currencyViewModel;
    private Currency activeCurrency;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        lowerText = findViewById(R.id.lowerCurrText);
        upperEditText = findViewById(R.id.upperInput);
        lowerEditText = findViewById(R.id.lowerInput);

        currencyViewModel = ViewModelProviders.of(this).get(CurrencyViewModel.class);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_list) {
            openListActivity();
        } else if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //If the closing activity was Currency list
        if (resultCode == RESULT_OK)
            if (requestCode == LIST_REQ_CODE) {
                int currencyIndex = data.getIntExtra(SELECTION_INDEX, -1);
                if (currencyIndex >= 0) {
                    //SET CURR
                }
            }
    }

    public void openListActivity() {
        //Create intent to start CurrencyList activity
        Intent listIntent = new Intent(this, CurrencyList.class);

        //Add list information

    }

    public void onCalculate(View view) {
        if (upperEditText.hasFocus()) {
            double value = Double.parseDouble(upperEditText.getText().toString());
            //value *= activeCurrency.getCurrencyRelation();
            lowerEditText.setText(String.format(Locale.US,"%.3f", value));
        } else if (lowerEditText.hasFocus()) {
            double value = Double.parseDouble(lowerEditText.getText().toString());
            //value /= activeCurrency.getCurrencyRelation();
            upperEditText.setText(String.format(Locale.US,"%.3f", value));
        } else {
            Toast.makeText(
                    this,
                    "Focus on field to calculate",
                    Toast.LENGTH_SHORT
            ).show();
        }
    }

    private void setActiveCurrency(Currency c) {
        if (c != null) {
            activeCurrency = c;
            lowerText.setText(activeCurrency.getCurrencyName());
            lowerEditText.setText("");
        } else {
            Toast.makeText(this, "Null currency", Toast.LENGTH_SHORT);
        }
    }

    //Function for testing buttons
    public void test(View view) {

    }
}
