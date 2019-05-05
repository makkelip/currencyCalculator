package com.example.laskin;

import android.os.Bundle;
import android.support.v4.app.Fragment;
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
        Button calculateButton = view.findViewById(R.id.calculateButton);
        calculateButton.setOnClickListener(v -> onCalculate(v));

        MainActivity main = (MainActivity)getActivity();

        Button upperChangeButton = view.findViewById(R.id.upperChangeButton);
        upperChangeButton.setOnClickListener(v -> main.openListFragment(UPPER_CURRENCY));

        Button lowerChangeButton = view.findViewById(R.id.lowerChangeButton);
        lowerChangeButton.setOnClickListener(v -> main.openListFragment(LOWER_CURRENCY));

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

        //Set lower currency
        if (args.getSerializable(LOWER_CURRENCY) != null) {
            lowerCurrency = (Currency) args.getSerializable(LOWER_CURRENCY);
            lowerText.setText(lowerCurrency.getCurrencyName());
        }

        return view;
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

        String editText;
        if (upperEditText.hasFocus()) {
            editText = upperEditText.getText().toString();
            if (!editText.matches("")) {
                double value = Double.parseDouble(editText);
                value /= upperCurrency.getCurrencyRelation();
                value *= lowerCurrency.getCurrencyRelation();
                lowerEditText.setText(String.format(Locale.US,"%.3f", value));
            }
        } else if (lowerEditText.hasFocus()) {
            editText = lowerEditText.getText().toString();
            if (!editText.matches("")) {
                double value = Double.parseDouble(lowerEditText.getText().toString());
                value /= lowerCurrency.getCurrencyRelation();
                value *= upperCurrency.getCurrencyRelation();
                upperEditText.setText(String.format(Locale.US, "%.3f", value));
            }
        } else {
            Toast.makeText(
                    getContext(),
                    "Focus on field to calculate",
                    Toast.LENGTH_SHORT
            ).show();
        }
    }
}
