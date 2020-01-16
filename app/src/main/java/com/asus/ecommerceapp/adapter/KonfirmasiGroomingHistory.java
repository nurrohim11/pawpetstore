package com.asus.ecommerceapp.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.asus.ecommerceapp.R;
import com.asus.ecommerceapp.model.grooming.HistoryGrooming;

import java.util.ArrayList;
import java.util.List;

public class KonfirmasiGroomingHistory extends RecyclerView.Adapter<KonfirmasiGroomingHistoryViewHolder> {
    private List<HistoryGrooming> list = new ArrayList<>();
    public  KonfirmasiGroomingHistory(){

    }
    public void clearAll(){
        list.clear();
        notifyDataSetChanged();
    }
    public void replaceAll(List<HistoryGrooming> items){
        list.clear();
        list=items;
    }
    public void updateData(List<HistoryGrooming> items){
        list.addAll(items);
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public KonfirmasiGroomingHistoryViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new KonfirmasiGroomingHistoryViewHolder(
                LayoutInflater.from(viewGroup.getContext()).inflate(
                        R.layout.item_history_grooming, viewGroup, false
                )
        );
    }

    @Override
    public void onBindViewHolder(@NonNull KonfirmasiGroomingHistoryViewHolder bengkelViewHolder, int i) {
        bengkelViewHolder.bind(list.get(i));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

}
