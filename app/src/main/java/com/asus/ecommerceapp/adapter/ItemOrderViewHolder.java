package com.asus.ecommerceapp.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.asus.ecommerceapp.BuildConfig;
import com.asus.ecommerceapp.R;
import com.asus.ecommerceapp.model.produk.ItemItem;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;

public class ItemOrderViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.img_barang)
    ImageView imgBarang;
    @BindView(R.id.tv_nama)
    TextView tvNama;
    @BindView(R.id.tv_qty)
    TextView tvQty;
    @BindView(R.id.tv_harga)
    TextView tvHarga;
    @BindView(R.id.tv_kategori)
    TextView tvKategori;
    private List<ItemItem> list;
    Call<ItemItem> itemCall;
    private void setResult(ArrayList<ItemItem> lists){
        list= lists;
    }
    public ItemOrderViewHolder(@NonNull View itemView) {
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
    public  void bind(final ItemItem item){
        Glide.with(itemView.getContext())
                .load(BuildConfig.BASE_URL_IMG+ item.getGambar())
                .apply(new RequestOptions()
                        .centerCrop())
                .into(imgBarang);
        tvHarga.setText(toRupiah(item.getHarga()));
        tvKategori.setText(item.getKategori());
        tvNama.setText(item.getNama());
        tvQty.setText(item.getQty());
    }
}
