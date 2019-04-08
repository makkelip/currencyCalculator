package com.example.laskin;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.laskin.entity.Currency;

import java.util.Locale;

public class CalculatorFragment extends Fragment {

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
        lowerText = view.findViewById(R.id.lowerTextView);
        upperEditText = view.findViewById(R.id.upperInput);
        lowerEditText = view.findViewById(R.id.lowerInput);

        Button button = (Button) view.findViewById(R.id.testButton);
        button.setOnClickListener(v -> test(v));

        return view;
    }

    public void test(View view) {
        Toast.makeText(getContext(), "TEST", Toast.LENGTH_LONG).show();
    }

    private void setActiveCurrency(Currency c) {
        if (c != null) {
            activeCurrency = c;
            lowerText.setText(activeCurrency.getCurrencyName());
            lowerEditText.setText("");
        } else {
            Toast.makeText(getContext(), "Null currency", Toast.LENGTH_SHORT);
        }
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
                    getContext(),
                    "Focus on field to calculate",
                    Toast.LENGTH_SHORT
            ).show();
        }
    }
}
