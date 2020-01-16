package com.asus.ecommerceapp.adapter;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import com.asus.ecommerceapp.BuildConfig;
import com.asus.ecommerceapp.R;
import com.asus.ecommerceapp.activity.DetailActivity;
import com.asus.ecommerceapp.model.produk.ProdukItem;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProdukViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.txtprice)TextView txtPrice;
    @BindView(R.id.txtshoes) TextView txtNama;
    @BindView(R.id.img_produk)ImageView imgProduk;
    private List<ProdukItem> list;
    private void setResult(ArrayList<ProdukItem> lists){
        list= lists;
    }
    public ProdukViewHolder(@NonNull View itemView) {
        super(itemView);
        ButterKnife.bind(this,itemView);
    }
    private String toRupiah(String nominal) {
        String hasil = "";
        DecimalFormat toRupiah = (DecimalFormat) DecimalFormat.getCurrencyInstance();
        DecimalFormatSymbols formatAngka = new DecimalFormatSymbols();
        formatAngka.setCurrencySymbol("Rp. ");
        formatAngka.setMonetaryDecimalSeparator(',');
        formatAngka.setMonetaryDecimalSeparator('.');
        toRupiah.setDecimalFormatSymbols(formatAngka);
        hasil = toRupiah.format(Double.valueOf(nominal));
        return hasil;
    }
    public void bind(final ProdukItem item){
        txtPrice.setText(toRupiah(item.getHarga()));
        txtNama.setText(item.getNama());
        Glide.with(itemView.getContext())
                .load(BuildConfig.BASE_URL_IMG+ item.getGambar())
                .apply(new RequestOptions()
                        .centerCrop())
                .into(imgProduk);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(itemView.getContext(), DetailActivity.class);
                intent.putExtra(DetailActivity.PRODUK_ITEM, new Gson().toJson(item));
                itemView.getContext().startActivity(intent);
            }
        });
    }
}
