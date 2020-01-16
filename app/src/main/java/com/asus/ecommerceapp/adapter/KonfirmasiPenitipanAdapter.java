package com.asus.ecommerceapp.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.asus.ecommerceapp.R;
import com.asus.ecommerceapp.model.penitipan.HistoryPenitipanItem;

import java.util.ArrayList;
import java.util.List;

public class KonfirmasiPenitipanAdapter extends RecyclerView.Adapter<KonfirmasiPenitipanViewHolder> {
    private List<HistoryPenitipanItem> list = new ArrayList<>();
    public  KonfirmasiPenitipanAdapter(){

    }
    public void clearAll(){
        list.clear();
        notifyDataSetChanged();
    }
    public void replaceAll(List<HistoryPenitipanItem> items){
        list.clear();
        list=items;
    }
    public void updateData(List<HistoryPenitipanItem> items){
        list.addAll(items);
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public KonfirmasiPenitipanViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new KonfirmasiPenitipanViewHolder(
                LayoutInflater.from(viewGroup.getContext()).inflate(
                        R.layout.item_history_penitipan, viewGroup, false
                )
        );
    }

    @Override
    public void onBindViewHolder(@NonNull KonfirmasiPenitipanViewHolder bengkelViewHolder, int i) {
        bengkelViewHolder.bind(list.get(i));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

}

