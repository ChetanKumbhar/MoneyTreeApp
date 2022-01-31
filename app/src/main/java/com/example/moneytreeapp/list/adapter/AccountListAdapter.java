package com.example.moneytreeapp.list.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.moneytreeapp.R;
import com.example.moneytreeapp.list.AccountItem;
import com.example.moneytreeapp.list.HeaderItem;
import com.example.moneytreeapp.list.ListItem;
import com.example.moneytreeapp.list.TransactionItem;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class AccountListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private Context mContext;
    List<ListItem> consolidatedList = new ArrayList<>();
    private static ClickListener clickListener;

    public AccountListAdapter(Context context, List<ListItem> consolidatedList) {
        this.consolidatedList = consolidatedList;
        this.mContext = context;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        RecyclerView.ViewHolder viewHolder = null;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        switch (viewType) {

            case ListItem.TYPE_ACCOUNT:
                View v1 = inflater.inflate(R.layout.layout_account_item, parent,
                        false);
                viewHolder = new AccountViewHolder(v1);
                break;

            case ListItem.TYPE_HEADER:
                View v2 = inflater.inflate(R.layout.layout_header_item, parent, false);
                viewHolder = new HeaderViewHolder(v2);
                break;

            case ListItem.TYPE_TRANSACTION:
                View v3 = inflater.inflate(R.layout.layout_transaction_item, parent, false);
                viewHolder = new TransactionViewHolder(v3);
                break;
        }

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {

        switch (viewHolder.getItemViewType()) {

            case ListItem.TYPE_ACCOUNT:

                AccountItem accountItem   = (AccountItem) consolidatedList.get(position);
                AccountViewHolder generalViewHolder= (AccountViewHolder) viewHolder;
                generalViewHolder.tvAccountName.setText(accountItem.getAccount().getName());
                generalViewHolder.tvAccountBalance.setText(accountItem.getAccount().getCurrency()+" "+String.valueOf(accountItem.getAccount().getCurrentBalance()));

                break;

            case ListItem.TYPE_TRANSACTION:

                TransactionItem transactionItem   = (TransactionItem) consolidatedList.get(position);
                TransactionViewHolder transactionViewHolder= (TransactionViewHolder) viewHolder;

                SimpleDateFormat df=new SimpleDateFormat("dd'th'");
                transactionViewHolder.tvTransactionDate.setText(df.format(transactionItem.getTransaction().getDate()));
                transactionViewHolder.tvTransactionName.setText(transactionItem.getTransaction().getDescription());
                transactionViewHolder.tvTransactionMoney.setText(String.valueOf(transactionItem.getTransaction().getAmount()));

                break;

            case ListItem.TYPE_HEADER:
                HeaderItem headerItem = (HeaderItem) consolidatedList.get(position);
                HeaderViewHolder headerViewHolder = (HeaderViewHolder) viewHolder;

                headerViewHolder.tvHeader.setText(headerItem.getHeader());
                // Populate date item data here

                break;
        }
    }

    public void setList(List<ListItem> list) {
        this.consolidatedList = list;
        notifyDataSetChanged();
    }


    // ViewHolder for date row item
    class HeaderViewHolder extends RecyclerView.ViewHolder {
        protected TextView tvHeader;

        public HeaderViewHolder(View v) {
            super(v);
            this.tvHeader = (TextView) v.findViewById(R.id.tv_header);

        }
    }

    // View holder for general row item
    class AccountViewHolder extends RecyclerView.ViewHolder{
        protected TextView tvAccountName;
        protected TextView tvAccountBalance;

        public AccountViewHolder(View v) {
            super(v);
            this.tvAccountName = (TextView) v.findViewById(R.id.tv_account_name);
            this.tvAccountBalance = (TextView) v.findViewById(R.id.tv_account_balance);
            v.setOnClickListener(v1 -> clickListener.onItemClick(getBindingAdapterPosition(),v1));
        }
    }

    // View holder for general row item
    class TransactionViewHolder extends RecyclerView.ViewHolder{
        protected TextView tvTransactionDate;
        protected TextView tvTransactionName;
        protected TextView tvTransactionMoney;

        public TransactionViewHolder(View v) {
            super(v);
            this.tvTransactionDate = (TextView) v.findViewById(R.id.tv_transaction_date);
            this.tvTransactionName = (TextView) v.findViewById(R.id.tv_transaction_name);
            this.tvTransactionMoney = (TextView) v.findViewById(R.id.tv_transaction_money);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return consolidatedList.get(position).getType();
    }

    public ListItem getItem(int position) {
        return consolidatedList.get(position);
    }

    @Override
    public int getItemCount() {
        return consolidatedList != null ? consolidatedList.size() : 0;
    }

    public void setOnItemClickListener(ClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public interface ClickListener {
        void onItemClick(int position, View v);
    }
}
