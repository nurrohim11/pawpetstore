package com.asus.ecommerceapp.adapter;

import android.app.Activity;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.asus.ecommerceapp.fragment.KonfirmasiPenitipanFragment;
import com.google.gson.Gson;
import com.asus.ecommerceapp.R;
import com.asus.ecommerceapp.activity.KonfirmasiPenitipanActivity;
import com.asus.ecommerceapp.model.penitipan.HistoryPenitipanItem;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class KonfirmasiPenitipanViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.tv_tgl)
    TextView tvTanggal;
    @BindView(R.id.tv_status) TextView tvStatus;
    @BindView(R.id.tv_total) TextView tvTotal;
    @BindView(R.id.tv_no)TextView tvNama;
    @BindView(R.id.tgl_dari)TextView tvDari;
    @BindView(R.id.tv_sampai) TextView tvSampai;
    @BindView(R.id.btn_konfirmasi)
    Button btnKonfirmasi;
    private List<HistoryPenitipanItem> list;
    private void setResult(ArrayList<HistoryPenitipanItem> lists){
        list= lists;
    }
    public KonfirmasiPenitipanViewHolder(@NonNull View itemView) {
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
    public void bind(final HistoryPenitipanItem item){
        tvTotal.setText(toRupiah(item.getTotal()));
        tvStatus.setText(item.getStatus());
        tvTanggal.setText(item.getTgl());
        tvNama.setText(item.getNoOrder());
        tvDari.setText(item.getTglDari());
        tvSampai.setText(item.getTglSampai());
        btnKonfirmasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(itemView.getContext(), KonfirmasiPenitipanActivity.class);
                intent.putExtra(KonfirmasiPenitipanActivity.KONFIRMASI_ITEM, new Gson().toJson(item));
                ((Activity) itemView.getContext()).startActivityForResult(intent, KonfirmasiPenitipanFragment.PICK_CONFIRMATION_PENITIPAN);
            }
        });
    }
}

