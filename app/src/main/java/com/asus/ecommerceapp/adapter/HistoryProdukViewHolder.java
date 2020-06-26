package com.asus.ecommerceapp.adapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.asus.ecommerceapp.R;
import com.asus.ecommerceapp.model.produk.HistoryProdukItem;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HistoryProdukViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.tv_status) TextView tvStatus;
    @BindView(R.id.tv_total) TextView tvTotal;
    @BindView(R.id.tv_no)TextView tvNama;
    @BindView(R.id.tv_tgl)TextView tvTgl;
    @BindView(R.id.btn_konfirmasi)
    Button btnKonfirmasi;
    private List<HistoryProdukItem> list;
    private void setResult(ArrayList<HistoryProdukItem> lists){
        list= lists;
    }
    public HistoryProdukViewHolder(@NonNull View itemView) {
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
        btnKonfirmasi.setVisibility(View.GONE);
//        itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(itemView.getContext(), DetailActivity.class);
//                intent.putExtra(DetailActivity.PRODUK_ITEM, new Gson().toJson(item));
//                itemView.getContext().startActivity(intent);
//            }
//        });
    }
}
