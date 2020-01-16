package com.asus.ecommerceapp.model.keranjang;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EditCart {
    public EditCart( String jumlah, String id_keranjang){
        this.jumlah = jumlah;
        this.id_keranjang = id_keranjang;
    }
//    public EditCart(String jumlah, String id_keranjang){
//        this.id = id;
//        this.jumlah = jumlah;
//        this.id_keranjang = id_keranjang;
  //  }
//    @SerializedName("id")
//    @Expose
//    private String id;
//    @SerializedName("id_pelanggan")
//    @Expose
//    private String id_pelanggan;
    @SerializedName("jumlah")
    @Expose
    private  String jumlah;
    @SerializedName("id_keranjang")
    @Expose
    private String id_keranjang;
//
//    public String getId() {
//        return id;
//    }
//
//    public void setId(String id) {
//        this.id = id;
//    }
//
//    public String getId_pelanggan() {
//        return id_pelanggan;
//    }
//
//    public void setId_pelanggan(String id_pelanggan) {
//        this.id_pelanggan = id_pelanggan;
//    }

    public String getJumlah() {
        return jumlah;
    }

    public void setJumlah(String jumlah) {
        this.jumlah = jumlah;
    }

    public String getId_keranjang() {
        return id_keranjang;
    }

    public void setId_keranjang(String id_keranjang) {
        this.id_keranjang = id_keranjang;
    }
}
