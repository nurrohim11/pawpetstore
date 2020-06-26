package com.asus.ecommerceapp.adapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.asus.ecommerceapp.R;
import com.asus.ecommerceapp.model.produk.ProdukItem;

import java.util.ArrayList;
import java.util.List;

public class ProdukAdapter extends RecyclerView.Adapter<ProdukViewHolder> {
    private List<ProdukItem> list = new ArrayList<>();
    public  ProdukAdapter(){

    }
    public void clearAll(){
        list.clear();
        notifyDataSetChanged();
    }
    public void replaceAll(List<ProdukItem> items){
        list.clear();
        list=items;
    }
    public void updateData(List<ProdukItem> items){
        list.addAll(items);
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public ProdukViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ProdukViewHolder(
                LayoutInflater.from(viewGroup.getContext()).inflate(
                        R.layout.list_produk, viewGroup, false
                )
        );
    }

    @Override
    public void onBindViewHolder(@NonNull ProdukViewHolder bengkelViewHolder, int i) {
        bengkelViewHolder.bind(list.get(i));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

}
