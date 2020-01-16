package com.asus.ecommerceapp.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.asus.ecommerceapp.R;
import com.asus.ecommerceapp.model.produk.ItemItem;

import java.util.ArrayList;
import java.util.List;

public class ItemOrderProdukAdapter extends RecyclerView.Adapter<ItemOrderViewHolder> {
    List<ItemItem> list = new ArrayList<>();
    Context context;

    public ItemOrderProdukAdapter(){

    }

    public void clearAll(){
        list.clear();
        notifyDataSetChanged();
    }
    public void replaceAll(List<ItemItem> items){
        list.clear();
        list=items;
    }
    public void updateData(List<ItemItem> items){
        list.addAll(items);
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public ItemOrderViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ItemOrderViewHolder(
                LayoutInflater.from(viewGroup.getContext()).inflate(
                        R.layout.item_orderproduk, viewGroup, false
                )
        );
    }

    @Override
    public void onBindViewHolder(@NonNull ItemOrderViewHolder keranjangViewHolder, int i) {
        keranjangViewHolder.bind(list.get(i));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

}

