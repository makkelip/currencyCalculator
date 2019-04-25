package com.example.laskin.ListFragment;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.laskin.CalculatorFragment;
import com.example.laskin.MainActivity;
import com.example.laskin.R;
import com.example.laskin.entity.Currency;
import com.example.laskin.room.CurrencyViewModel;

/**
 * A fragment representing a list of Items.
 * <p/>
 */
public class CurrencyListFragment extends Fragment {

    public static final String TAG = "CurrencyListFragment";
    private CurrencyListAdapter listAdapter;
    private CurrencyViewModel mViewModel;
    private RecyclerView recyclerView;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public CurrencyListFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        listAdapter = new CurrencyListAdapter(inflater.getContext());
        mViewModel = ViewModelProviders.of(this).get(CurrencyViewModel.class);

        mViewModel.getAllCurrencies().observe(this, currencies -> {
            listAdapter.setmCurrencyList(currencies);
        });

        View view = inflater.inflate(R.layout.fragment_currency_list, container, false);
        recyclerView = view.findViewById(R.id.currency_recycle_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(listAdapter);

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), recyclerView,
                new RecyclerTouchListener.ClickListener() {
                    @Override
                    public void onClick(View view, int position) {
                        Currency currency = listAdapter.getCurrency(position);
                        try {
                            MainActivity main = (MainActivity)getActivity();
                            main.openCalculatorFragment(currency);
                        } catch (Exception e) {
                            throw new ClassCastException(getActivity().toString() + "Activity is other than MainActivity");
                        }
                    }

                    @Override
                    public void onLongClick(View view, int position) {
                        int id = listAdapter.getCurrency(position).getCurrencyId();
                        mViewModel.delete(id);
                        Toast.makeText(getContext(), "Currency deleted " + id, Toast.LENGTH_LONG).show();
                    }
        }));

        return view;
    }

    public void openCalculatorFragment(Intent intent) {
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        CalculatorFragment calculatorFragment = new CalculatorFragment();

        transaction.addToBackStack(null);
        transaction.replace(R.id.content_main, calculatorFragment).commit();
    }
}