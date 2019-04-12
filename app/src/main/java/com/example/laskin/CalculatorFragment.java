package com.example.laskin;

import android.arch.lifecycle.ViewModelProviders;
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
import com.example.laskin.room.CurrencyViewModel;

import java.util.Locale;

public class CalculatorFragment extends Fragment {


    public static final String ACTIVE_CURRENCY = "active_currency";
    private static final String TAG = "CalculatorFragment";

    private CurrencyViewModel viewModel;

    private TextView lowerText;
    private EditText upperEditText;
    private EditText lowerEditText;

    private Currency activeCurrency;

    public CalculatorFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_calculator, container, false);
        viewModel = ViewModelProviders.of(this).get(CurrencyViewModel.class);

        lowerText = view.findViewById(R.id.lowerTextView);
        upperEditText = view.findViewById(R.id.upperInput);
        lowerEditText = view.findViewById(R.id.lowerInput);

        // Bind functions to buttons
        Button testButton = view.findViewById(R.id.testButton);
        testButton.setOnClickListener(v -> test(v));
        Button calculateButton = view.findViewById(R.id.calculateButton);
        calculateButton.setOnClickListener(v -> onCalculate(v));

        //Set active currency
        if (getArguments().getSerializable(ACTIVE_CURRENCY) != null) {
            activeCurrency = (Currency) getArguments().getSerializable(ACTIVE_CURRENCY);
            lowerText.setText(activeCurrency.getCurrencyName());
        } else {
            Log.e(TAG, "No active currency given in arguments");
        }

        return view;
    }

    public void test(View view) {
        Toast.makeText(getContext(), "TEST", Toast.LENGTH_LONG).show();
    }

    public void onCalculate(View view) {
        if (activeCurrency == null) {
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
                value *= activeCurrency.getCurrencyRelation();
                lowerEditText.setText(String.format(Locale.US,"%.3f", value));
            }
        } else if (lowerEditText.hasFocus()) {
            editText = lowerEditText.getText().toString();
            if (!editText.matches("")) {
                double value = Double.parseDouble(lowerEditText.getText().toString());
                value /= activeCurrency.getCurrencyRelation();
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
