package com.asus.ecommerceapp.model.keranjang;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Cart {
    public Cart(String id_produk, String id_pelanggan, String jumlah, String rego){
        this.id_pelanggan = id_pelanggan;
        this.id_produk = id_produk;
        this.jumlah = jumlah;
        this.rego = rego;
    }
    public Cart(String  id, String id_produk, String id_pelanggan, String jumlah, String rego){
        this.id = id;
        this.id_pelanggan = id_pelanggan;
        this.id_produk = id_produk;
        this.jumlah = jumlah;
        this.rego = rego;
    }
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("id_produk")
    @Expose
    private String id_produk;
    @SerializedName("id_pelanggan")
    @Expose
    private String id_pelanggan;
    @SerializedName("jumlah")
    @Expose
    private  String jumlah;
    @SerializedName("rego")
    @Expose
    private String rego;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId_produk() {
        return id_produk;
    }

    public void setId_produk(String id_produk) {
        this.id_produk = id_produk;
    }

    public String getId_pelanggan() {
        return id_pelanggan;
    }

    public void setId_pelanggan(String id_pelanggan) {
        this.id_pelanggan = id_pelanggan;
    }

    public String getJumlah() {
        return jumlah;
    }

    public void setJumlah(String jumlah) {
        this.jumlah = jumlah;
    }

    public String getRego() {
        return rego;
    }

    public void setRego(String rego) {
        this.rego = rego;
    }
}
