package com.example.laskin;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import com.example.laskin.ListFragment.CurrencyListFragment;
import com.example.laskin.entity.Currency;
import com.example.laskin.room.CurrencyViewModel;
import org.xmlpull.v1.XmlPullParserException;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private static final int ADD_REQ_CODE = 1;

    private CurrencyViewModel currencyViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        currencyViewModel = ViewModelProviders.of(this).get(CurrencyViewModel.class);

        // Open calculator fragment first
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        Bundle bundle = new Bundle();
        bundle.putSerializable(CalculatorFragment.ACTIVE_CURRENCY, null);

        CalculatorFragment calculatorFragment = new CalculatorFragment();
        calculatorFragment.setArguments(bundle);

        transaction.replace(R.id.content_main, calculatorFragment)
                .commit();
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

        if (id == R.id.action_list) {
            openListFragment();
        } else if (id == R.id.action_update) {
            new DownloadCurrenciesTask().execute("https://www.ecb.europa.eu/stats/eurofxref/eurofxref-daily.xml");
        } else if (id == R.id.action_add) {
            Intent intent = new Intent(this, AddCurrencyActivity.class);
            startActivityForResult(intent, ADD_REQ_CODE);
        } else if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void openListFragment() {
        if (getSupportFragmentManager().getBackStackEntryCount() < 1) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            CurrencyListFragment listFragment = new CurrencyListFragment();

            transaction.replace(R.id.content_main, listFragment)
                    .addToBackStack(CurrencyListFragment.TAG)
                    .commit();
        }
    }

    public void openCalculatorFragment(Currency currency) {
        getSupportFragmentManager().popBackStack(); //Remove list fragment from stack

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        Bundle bundle = new Bundle();
        bundle.putSerializable(CalculatorFragment.ACTIVE_CURRENCY, currency);

        CalculatorFragment calculatorFragment = new CalculatorFragment();
        calculatorFragment.setArguments(bundle);
        transaction.replace(R.id.content_main, calculatorFragment)
                .commit();
    }

    // Uses AsyncTask to create a task away from the main UI thread. This task takes a
    // URL string and uses it to create an HttpUrlConnection. Once the connection
    // has been established, the AsyncTask downloads the contents of the webpage as
    // an InputStream. Finally, the InputStream is converted into a string, which is
    // displayed in the UI by the AsyncTask's onPostExecute method.
    private class DownloadCurrenciesTask extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... url) {
            InputStream stream;
            // params comes from the execute() call: params[0] is the url.
            try {
                stream = downLoadUrl(url[0]);
                XmlParser parser = new XmlParser();
                List<Currency> currencyList;

                currencyList = parser.parse(stream);
                currencyViewModel.insertMultiple(currencyList);

                stream.close();
            } catch (IOException e) {
                return null;
            } catch (XmlPullParserException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            Toast.makeText(getBaseContext(), "Currencies updated", Toast.LENGTH_LONG).show();
        }
    }

    private InputStream downLoadUrl(String myUrl) throws IOException {

        URL url = new URL(myUrl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setReadTimeout(10000 /* milliseconds */);
        conn.setConnectTimeout(15000 /* milliseconds */);
        conn.setRequestMethod("GET");
        conn.setDoInput(true);
        // Starts the query
        conn.connect();
        int response = conn.getResponseCode();
        Log.d(TAG, "The response is: " + response);
        return conn.getInputStream();
    }
}
