package com.asus.ecommerceapp.adapter;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;
import com.asus.ecommerceapp.R;
import com.asus.ecommerceapp.activity.DetailOrderActivity;
import com.asus.ecommerceapp.activity.KonfirmasiProdukActivity;
import com.asus.ecommerceapp.model.produk.HistoryProdukItem;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class KonfirmasiProdukViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.tv_status)
    TextView tvStatus;
    @BindView(R.id.tv_total) TextView tvTotal;
    @BindView(R.id.tv_no)TextView tvNama;
    @BindView(R.id.tv_tgl)TextView tvTgl;
    @BindView(R.id.btn_konfirmasi)
    Button btnKonfirmasi;
    private List<HistoryProdukItem> list;
    private void setResult(ArrayList<HistoryProdukItem> lists){
        list= lists;
    }
    public KonfirmasiProdukViewHolder(@NonNull View itemView) {
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
    public void bind(final HistoryProdukItem item){
        tvTotal.setText(toRupiah(item.getTotal()));
        tvStatus.setText(item.getStatus());
        tvNama.setText(item.getNoOrder());
        tvTgl.setText(item.getTgl());
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(itemView.getContext(), DetailOrderActivity.class);
                intent.putExtra(DetailOrderActivity.DETAIL_ITEM, new Gson().toJson(item));
                itemView.getContext().startActivity(intent);
            }
        });
        btnKonfirmasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(itemView.getContext(), KonfirmasiProdukActivity.class);
                intent.putExtra(KonfirmasiProdukActivity.KONFIRMASI_ITEM, new Gson().toJson(item));
                itemView.getContext().startActivity(intent);
            }
        });
    }
}
