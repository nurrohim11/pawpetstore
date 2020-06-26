package com.asus.ecommerceapp.adapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.asus.ecommerceapp.R;
import com.asus.ecommerceapp.model.produk.HistoryProdukItem;

import java.util.ArrayList;
import java.util.List;

public class HistoryProdukAdapter extends RecyclerView.Adapter<HistoryProdukViewHolder> {
    private List<HistoryProdukItem> list = new ArrayList<>();
    public  HistoryProdukAdapter(){

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
    public HistoryProdukViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new HistoryProdukViewHolder(
                LayoutInflater.from(viewGroup.getContext()).inflate(
                        R.layout.item_history_produk, viewGroup, false
                )
        );
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryProdukViewHolder bengkelViewHolder, int i) {
        bengkelViewHolder.bind(list.get(i));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

}
