package com.example.laskin.ListFragment;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.laskin.R;
import com.example.laskin.entity.Currency;

import java.util.List;
import java.util.Locale;

public class CurrencyListAdapter extends RecyclerView.Adapter<CurrencyListAdapter.CurrencyViewHolder> {

    class CurrencyViewHolder extends RecyclerView.ViewHolder {
        private final TextView currencyNameView;
        private final TextView currencyRelationView;
        private final TextView currencyDateView;

        private CurrencyViewHolder(View itemView) {
            super(itemView);
            currencyNameView = itemView.findViewById(R.id.currency_name);
            currencyRelationView = itemView.findViewById(R.id.currency_relation);
            currencyDateView = itemView.findViewById(R.id.currency_date);
        }
    }

    private final LayoutInflater mInflater;
    private List<Currency> mCurrencyList;

    CurrencyListAdapter(Context context) { mInflater = LayoutInflater.from(context); }

    @NonNull
    @Override
    public CurrencyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = mInflater.inflate(R.layout.fragment_currency, viewGroup, false);
        return new CurrencyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CurrencyViewHolder currencyViewHolder, int i) {
        if (mCurrencyList != null) {
            Currency current = mCurrencyList.get(i);
            currencyViewHolder.currencyNameView.setText(current.getCurrencyName());
            currencyViewHolder.currencyRelationView.setText(String.format(Locale.US,"%1$,.2f", current.getCurrencyRelation()));
            currencyViewHolder.currencyDateView.setText(current.getCurrencyDate());
        } else {
            // Covers the case of data not being ready yet.
            currencyViewHolder.currencyNameView.setText("No Currency");
        }
    }

    void setmCurrencyList(List<Currency> currencyList) {
        mCurrencyList = currencyList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (mCurrencyList != null)
            return mCurrencyList.size();
        else return 0;
    }

    public String getName(View itemView) {
        return ((TextView) itemView.findViewById(R.id.title)).getText().toString();
    }

    public Currency getCurrency(int index) {
        return mCurrencyList.get(index);
    }
}
