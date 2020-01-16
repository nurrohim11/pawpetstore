package com.asus.ecommerceapp.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.asus.ecommerceapp.R;
import com.asus.ecommerceapp.model.produk.HistoryProdukItem;

import java.util.ArrayList;
import java.util.List;

public class KonfirmasiProdukAdapter extends RecyclerView.Adapter<KonfirmasiProdukViewHolder> {
    private List<HistoryProdukItem> list = new ArrayList<>();
    public  KonfirmasiProdukAdapter(){

    }
    public void clearAll(){
        list.clear();
        notifyDataSetChanged();
    }
    public void replaceAll(List<HistoryProdukItem> items){
        list.clear();
        list=items;
    }
    public void updateData(List<HistoryProdukItem> items){
        list.addAll(items);
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public KonfirmasiProdukViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new KonfirmasiProdukViewHolder(
                LayoutInflater.from(viewGroup.getContext()).inflate(
                        R.layout.item_history_produk, viewGroup, false
                )
        );
    }

    @Override
    public void onBindViewHolder(@NonNull KonfirmasiProdukViewHolder bengkelViewHolder, int i) {
        bengkelViewHolder.bind(list.get(i));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

}
