package com.example.moneytreeapp.ui;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.moneytreeapp.databinding.TransactionListFragmentBinding;
import com.example.moneytreeapp.list.ListItem;
import com.example.moneytreeapp.list.adapter.AccountListAdapter;

import java.util.List;


/**
 * A fragment representing a list of Items.
 */
public class TransactionListFragment extends Fragment {

    public static final String ARG_ACCOUNT_ID = "account_id";
    public static final String ARG_TITLE = "title";
    private int mAccountId = 1;
    private TransactionListFragmentBinding binding;
    private AccountListViewModel mViewModel;
    private List<ListItem> consolidatedList;
    private AccountListAdapter adapter;
    private String title;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public TransactionListFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mAccountId = getArguments().getInt(ARG_ACCOUNT_ID);
            title = getArguments().getString(ARG_TITLE);

            Toast.makeText(getContext(), "mAccountId - " + mAccountId, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = TransactionListFragmentBinding.inflate(inflater, container, false);
        this.setHasOptionsMenu(true);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

     //   ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(title);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setHomeButtonEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mViewModel = new ViewModelProvider(this).get(AccountListViewModel.class);
        initView();
        // TODO: Use the ViewModel
        observeTransactionList();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        //do something with your id
        return super.onOptionsItemSelected(item);
    }



    private void initView() {
        binding.recyclerview.setHasFixedSize(true);
        adapter = new AccountListAdapter(this.getContext(), consolidatedList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this.getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        binding.recyclerview.setLayoutManager(layoutManager);
        binding.recyclerview.setAdapter(adapter);
    }

    private void observeTransactionList() {

        mViewModel.getAccountData().observe(getViewLifecycleOwner(), account -> {
            if (account != null) {
                binding.tvAccountName.setText(account.getName());
                binding.tvBalance.setText(account.getCurrentBalance() + " " + account.getCurrency());
                binding.tvBalanceCurrency.setText(account.getCurrentBalanceInBase() + " JPY");
                binding.tvBalance.setVisibility(account.getCurrency().equalsIgnoreCase("JPY") ? View.GONE : View.VISIBLE);
            }

        });

        mViewModel.getTransactionList().observe(getViewLifecycleOwner(), list -> {
            if (list != null && list.size() > 0) {
                consolidatedList = list;
                adapter.setList(list);
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onResume() {
        super.onResume();
        mViewModel.observeTransactionList(getViewLifecycleOwner(), mAccountId);
    }
}