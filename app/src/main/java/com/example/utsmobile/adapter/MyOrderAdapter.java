package com.example.utsmobile.adapter;


import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.utsmobile.DoOrderActivity;
import com.example.utsmobile.Order;
import com.example.utsmobile.R;
import com.example.utsmobile.items.Item;

import java.util.ArrayList;

public class MyOrderAdapter extends RecyclerView.Adapter<MyOrderAdapter.ViewHolder> {
    private ArrayList<OrderBase> orders;
    private boolean isFinished = false;

    public MyOrderAdapter(ArrayList<OrderBase> orders) {
        isFinished = false;
        this.orders = orders;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_order_layout,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.nameView.setText(orders.get(position).getItem().getName());
        String price = orders.get(position).getQty() +" x Rp. "+ orders.get(position).getItem().getPrice();
        holder.priceView.setText(price);
        holder.deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Order.removeFromOrder(position);
                MyOrderAdapter.super.notifyDataSetChanged();

            }
        });
        if (isFinished)holder.deleteBtn.setVisibility(View.GONE);
    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        public TextView nameView,priceView;
        public Button deleteBtn;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nameView = itemView.findViewById(R.id.my_order_name);
            priceView = itemView.findViewById(R.id.my_order_qty_price);
            deleteBtn = itemView.findViewById(R.id.delete_order_btn);


        }
    }

    public void changeToFinishOrder(){
    isFinished = true;
    }



}
