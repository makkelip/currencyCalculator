package com.example.laskin;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.laskin.entity.Currency;

import org.w3c.dom.Text;

import java.util.Locale;

public class CalculatorFragment extends Fragment {


    public static final String UPPER_CURRENCY = "upper_currency";
    public static final String LOWER_CURRENCY = "lower_currency";
    public static final String UPPER_VALUE = "upper_value";
    public static final String LOWER_VALUE = "lower_value";
    public static final String TAG = "CalculatorFragment";

    private TextView upperText;
    private TextView lowerText;
    private EditText upperEditText;
    private EditText lowerEditText;

    private Currency upperCurrency;
    private Currency lowerCurrency;

    public CalculatorFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_calculator, container, false);

        upperText = view.findViewById(R.id.upperTextView);
        lowerText = view.findViewById(R.id.lowerTextView);
        upperEditText = view.findViewById(R.id.upperInput);
        lowerEditText = view.findViewById(R.id.lowerInput);

        // Bind functions to buttons
        MainActivity main = (MainActivity)getActivity();

        Button calculateButton = view.findViewById(R.id.calculateButton);
        calculateButton.setOnClickListener(v -> onCalculate(v));

        Button upperChangeButton = view.findViewById(R.id.upperChangeButton);
        upperChangeButton.setOnClickListener(v -> {
            main.setLowerValue(getValue(lowerEditText));
            main.setUpperValue(getValue(upperEditText));
            main.openListFragment(UPPER_CURRENCY);
        });

        Button lowerChangeButton = view.findViewById(R.id.lowerChangeButton);
        lowerChangeButton.setOnClickListener(v -> {
            main.setUpperValue(getValue(upperEditText));
            main.setLowerValue(getValue(lowerEditText));
            main.openListFragment(LOWER_CURRENCY);
        });

        Bundle args = getArguments();
        if (args == null) {
            Log.e(TAG, "No currencies given in arguments");
            return view;
        }

        //Set upper currency
        if (args.getSerializable(UPPER_CURRENCY) != null) {
            upperCurrency = (Currency) args.getSerializable(UPPER_CURRENCY);
            upperText.setText(upperCurrency.getCurrencyName());
        }

        //Set upper value
        double upperVal = args.getDouble(UPPER_VALUE);
        upperEditText.setText(String.format(Locale.US,"%.3f", upperVal));

        //Set lower currency
        if (args.getSerializable(LOWER_CURRENCY) != null) {
            lowerCurrency = (Currency) args.getSerializable(LOWER_CURRENCY);
            lowerText.setText(lowerCurrency.getCurrencyName());
        }

        //Set lower value
        double lowerVal = args.getDouble(LOWER_VALUE);
        lowerEditText.setText(String.format(Locale.US,"%.3f", lowerVal));

        //Add formatter watcher for inputs
        upperEditText.addTextChangedListener(createTextWatcher(upperEditText));
        lowerEditText.addTextChangedListener(createTextWatcher(lowerEditText));

        return view;
    }

    private double getValue(EditText editText) {
        String text = editText.getText().toString();
        if (text.matches("")) return 0;
        return Double.parseDouble(text);
    }

    public void onCalculate(View view) {
        if (upperCurrency == null || lowerCurrency == null) {
            Toast.makeText(
                    getContext(),
                    "Unable to calculate. Currency missing",
                    Toast.LENGTH_SHORT
            ).show();
            return;
        }

        if (upperEditText.hasFocus()) {
            double value = getValue(upperEditText);
            value /= upperCurrency.getCurrencyRelation();
            value *= lowerCurrency.getCurrencyRelation();
            lowerEditText.setText(String.format(Locale.US,"%.3f", value));
        } else if (lowerEditText.hasFocus()) {
            double value = getValue(lowerEditText);
            value /= lowerCurrency.getCurrencyRelation();
            value *= upperCurrency.getCurrencyRelation();
            upperEditText.setText(String.format(Locale.US, "%.3f", value));
        } else {
            Toast.makeText(
                    getContext(),
                    "Focus on field to calculate",
                    Toast.LENGTH_SHORT
            ).show();
        }
    }

    private TextWatcher createTextWatcher(EditText editText) {
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String input = s.toString();
                if (input.matches("")) return;
                int integerPlaces = input.indexOf('.');
                int decimalPlaces = input.length() - integerPlaces - 1;
                if (decimalPlaces > 3) {
                    input = String.format(Locale.US, "%.3f", Double.valueOf(input));
                    editText.setText(input);
                    editText.setSelection(input.length());
                }
            }

            @Override
            public void afterTextChanged(Editable s) { }
        };
    }
}
