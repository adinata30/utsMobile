package com.example.utsmobile.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.utsmobile.FinishOrderActivity;
import com.example.utsmobile.Order;
import com.example.utsmobile.R;
import com.example.utsmobile.Transaction;
import com.example.utsmobile.TransactionOrder;

import java.util.ArrayList;
import java.util.Date;

public class TransactionHistoryAdapter extends RecyclerView.Adapter<TransactionHistoryAdapter.ViewHolder> {
    private ArrayList<Transaction> orders;

    public TransactionHistoryAdapter(ArrayList<Transaction> orders) {
        this.orders = orders;
    }

    @NonNull
    @Override
    public TransactionHistoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.transaction_history_layout,parent,false);

        return new TransactionHistoryAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final TransactionHistoryAdapter.ViewHolder holder, final int position) {
        int totalPrice =0;
        for(TransactionOrder to: orders.get(position).getOrders())
        {
            totalPrice += to.getItem().getPrice() * to.getQty();
        }
        holder.price.setText("Total Price : Rp."+totalPrice);
        holder.rname.setText("Restaurant : "+orders.get(position).getRestaurant().getName());
        holder.addr.setText("Delivery Address : "+orders.get(position).getDeliveryAddress());
        long timestamp = orders.get(position).getTransactionDate();
        Date date = new Date(timestamp);
        holder.tdate.setText("Transaction Date : "+date.toGMTString());

        holder.viewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(holder.itemView.getContext(), FinishOrderActivity.class);
                intent.putExtra("tid",orders.get(position).getId());
                holder.itemView.getContext().startActivity(intent);

            }
        });
//        if (isFinished)holder.deleteBtn.setVisibility(View.GONE);
    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        public TextView price,rname,tdate,addr;
        public Button viewBtn;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            price = itemView.findViewById(R.id.tid_text);
            rname = itemView.findViewById(R.id.restaurant_text);
            tdate = itemView.findViewById(R.id.transaction_date_text);
            viewBtn = itemView.findViewById(R.id.view_details_btn);
            addr = itemView.findViewById(R.id.delivery_address);

        }
    }

}
