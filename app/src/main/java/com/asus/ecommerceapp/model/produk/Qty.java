package com.asus.ecommerceapp.model.produk;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Qty {
    public Qty(String jumlah) {
        this.jumlah = jumlah;
    }

    public Qty(String idKeranjang, String jumlah) {
        this.idKeranjang = idKeranjang;
        this.jumlah = jumlah;
    }

    @SerializedName("id")
    @Expose
    private String idKeranjang;
    @SerializedName("jumlah")
    @Expose
    private String jumlah;

    public String getIdKeranjang() {
        return idKeranjang;
    }

    public void setIdKeranjang(String idKeranjang) {
        this.idKeranjang = idKeranjang;
    }

    public String getJumlah() {
        return jumlah;
    }

    public void setJumlah(String jumlah) {
        this.jumlah = jumlah;
    }


}
