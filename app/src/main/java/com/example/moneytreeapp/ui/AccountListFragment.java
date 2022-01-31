package com.example.moneytreeapp.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.moneytreeapp.R;
import com.example.moneytreeapp.databinding.AccountListFragmentBinding;
import com.example.moneytreeapp.list.AccountItem;
import com.example.moneytreeapp.list.ListItem;
import com.example.moneytreeapp.list.adapter.AccountListAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Observer;

public class AccountListFragment extends Fragment {

    private AccountListViewModel mViewModel;
    private AccountListFragmentBinding binding;

    List<ListItem> consolidatedList = new ArrayList<>();
    private AccountListAdapter adapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = AccountListFragmentBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(AccountListViewModel.class);
        initView();
        // TODO: Use the ViewModel
        observeAccountList();
    }

    private void initView() {
        binding.recyclerview.setHasFixedSize(true);
        adapter = new AccountListAdapter(this.getContext(), consolidatedList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this.getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        binding.recyclerview.setLayoutManager(layoutManager);
        adapter.setOnItemClickListener((position, v) -> {


            moveToTransactionList(position);
        });
        binding.recyclerview.setAdapter(adapter);
    }

    private void moveToTransactionList(int position) {
        int accountId = ((AccountItem) adapter.getItem(position)).getAccount().getId();
        String institution = ((AccountItem) adapter.getItem(position)).getAccount().getInstitution();
        Bundle bundle = new Bundle();
        bundle.putInt(TransactionListFragment.ARG_ACCOUNT_ID, accountId);
        bundle.putString(TransactionListFragment.ARG_TITLE, institution);

        NavHostFragment.findNavController(AccountListFragment.this)
                .navigate(R.id.action_account_to_transaction, bundle);
    }

    @Override
    public void onResume() {
        super.onResume();
        mViewModel.observeAccountList(getViewLifecycleOwner());
    }

    private void observeAccountList() {
        mViewModel.getTotalAmount().observe(getViewLifecycleOwner(), integer -> {
            binding.tvTotalAmount.setText(integer + " JPY");
        });


        mViewModel.getAccountList().observe(getViewLifecycleOwner(), list -> {
            if (list != null && list.size() > 0) {
                consolidatedList = list;
                adapter.setList(list);
            }
        });
    }
}