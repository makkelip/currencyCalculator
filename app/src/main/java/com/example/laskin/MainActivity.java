package com.example.laskin;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.laskin.ListFragment.CurrencyListFragment;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private static final int LIST_REQ_CODE = 1;
    private static final String SELECTION_INDEX = "index";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Fragment calculatorFragment = new CalculatorFragment();
        fragmentTransaction.add(R.id.content_main, calculatorFragment);
        //fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

        /*
        CalculatorFragment newFragment = new CalculatorFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        // Replace whatever is in the fragment_container view with this fragment,
        // and add the transaction to the back stack so the user can navigate back
        transaction.replace(R.id.fragmentContainer, newFragment);
        //transaction.addToBackStack(null); // to get back from ListFragment
        // Commit the transaction
        transaction.commit();
        */
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
            openListFragment();
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

    public void openListFragment() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        CurrencyListFragment listFragment = new CurrencyListFragment();

        transaction.addToBackStack(null);
        transaction.add(R.id.content_main, listFragment).commit();
    }
}
