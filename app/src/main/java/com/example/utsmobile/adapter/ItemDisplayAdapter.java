package com.example.utsmobile.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.utsmobile.DoOrderActivity;
import com.example.utsmobile.ItemDisplayActivity;
import com.example.utsmobile.R;
import com.example.utsmobile.items.Item;

import java.util.ArrayList;

public class ItemDisplayAdapter extends RecyclerView.Adapter<ItemDisplayAdapter.ViewHolder> {
    private ArrayList<Item> items;

    public ItemDisplayAdapter(ArrayList<Item> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_display_layout,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.nameView.setText(items.get(position).getName());
        holder.priceView.setText("Rp. "+items.get(position).getPrice());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String name = items.get(position).getName();
                int price = items.get(position).getPrice();

                Intent intent = new Intent(view.getContext(),DoOrderActivity.class);
                Item item = items.get(position);
                intent.putExtra("Item",item);
                view.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        public TextView nameView,priceView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nameView = itemView.findViewById(R.id.display_name);
            priceView = itemView.findViewById(R.id.display_price);


        }
    }



}
