package com.example.laskin;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private static final int LIST_REQ_CODE = 1;
    private static final String SELECTION_INDEX = "index";

    private ArrayList<Currency> currencyList;
    private DatabaseAdapter dbAdapter = new DatabaseAdapter(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        dbAdapter.open();
        initDatabase();
        updateCurrencies();
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
                    Toast t = Toast.makeText(this, Integer.toString(currencyIndex), Toast.LENGTH_SHORT);
                    t.show();
                }
            }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dbAdapter.close();
    }

    public void openListActivity() {
        //Create intent to start CurrencyList activity
        Intent listIntent = new Intent(this, CurrencyList.class);

        //Add list information
        ArrayList<String> currencyNameList = new ArrayList<>();
        for (Currency c : currencyList)
            currencyNameList.add(c.getName());

        listIntent.putExtra(CurrencyList.CURRENCIES, currencyNameList);
        startActivityForResult(listIntent, LIST_REQ_CODE);
    }


    //Function for testing buttons
    public void toastMe(View view) {
        Toast myToast = Toast.makeText(this, "Hello toast!", Toast.LENGTH_SHORT);
        myToast.show();
    }

    //Adding some data to database
    private void initDatabase() {
        dbAdapter.open();
        dbAdapter.clearDatabase();
        dbAdapter.insertCurrency("KR",
                "10.231",
                "26.03.2019");
        dbAdapter.insertCurrency(
                "GBP",
                "0.844",
                "26.03.2019");
        dbAdapter.insertCurrency(
                "SEK",
                "9.47",
                "26.03.2019");
    }

    private void updateCurrencies() {
        currencyList = dbAdapter.getAllCurrencies();
    }
}
