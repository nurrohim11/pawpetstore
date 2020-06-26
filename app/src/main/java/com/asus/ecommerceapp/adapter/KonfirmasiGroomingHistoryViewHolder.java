package com.asus.ecommerceapp.adapter;

import android.app.Activity;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.asus.ecommerceapp.fragment.KonfirmasiGroomingFragment;
import com.google.gson.Gson;
import com.asus.ecommerceapp.R;
import com.asus.ecommerceapp.activity.KonfirmasiGroomingActivity;
import com.asus.ecommerceapp.model.grooming.HistoryGrooming;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class KonfirmasiGroomingHistoryViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.tv_tgl)
    TextView tvTanggal;
    @BindView(R.id.tv_status) TextView tvStatus;
    @BindView(R.id.tv_total) TextView tvTotal;
    @BindView(R.id.tv_no)TextView tvNama;
    @BindView(R.id.tv_hewan) TextView tvHewan;
    @BindView(R.id.tv_jenishewan)TextView tvJenisHewan;
    @BindView(R.id.tv_ukuran)TextView tvUkuran;
    @BindView(R.id.ll_jenis)
    LinearLayout llJenis;
    @BindView(R.id.btn_konfirmasi)
    Button btnKonfirmasi;
    @BindView(R.id.ll_ukuran) LinearLayout llUkuran;
    private List<HistoryGrooming> list;
    private void setResult(ArrayList<HistoryGrooming> lists){
        list= lists;
    }
    public KonfirmasiGroomingHistoryViewHolder(@NonNull View itemView) {
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
    public void bind(final HistoryGrooming item){
        tvJenisHewan.setVisibility(View.GONE);
        tvUkuran.setVisibility(View.GONE);
        llJenis.setVisibility(View.GONE);
        llUkuran.setVisibility(View.GONE);
        if (item.getJenisHewan().equals("")){
            tvUkuran.setVisibility(View.VISIBLE);
            tvUkuran.setText(item.getUkuran());
            llUkuran.setVisibility(View.VISIBLE);
        }else if(item.getUkuran().equals("")){
            tvJenisHewan.setVisibility(View.VISIBLE);
            tvJenisHewan.setText(item.getJenisHewan());
            tvJenisHewan.setVisibility(View.VISIBLE);
        }
        tvHewan.setText(item.getJenis());
        tvTotal.setText(toRupiah(item.getTotal()));
        tvStatus.setText(item.getStatus());
        tvTanggal.setText(item.getTgl());
        tvNama.setText(item.getNoOrder());
        tvHewan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(itemView.getContext(),"Hewan"+item.getUkuran(),Toast.LENGTH_SHORT).show();
            }
        });
        btnKonfirmasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(itemView.getContext(), KonfirmasiGroomingActivity.class);
                intent.putExtra(KonfirmasiGroomingActivity.KONFIRMASI_ITEM, new Gson().toJson(item));
                ((Activity) itemView.getContext()).startActivityForResult(intent, KonfirmasiGroomingFragment.PICK_CONFIRMATION_GROMING);
            }
        });
    }
}
