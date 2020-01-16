package com.asus.ecommerceapp.model.produk;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Checkout {
    public Checkout(String id_pelanggan){
        this.id_pelanggan = id_pelanggan;
    }
    public Checkout(String id, String id_pelanggan){
        this.id_pelanggan = id_pelanggan;
        this.id =id;
    }
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("id_pelanggan")
    @Expose
    private String id_pelanggan;
    @SerializedName("no_order")
    @Expose
    private String no_order;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId_pelanggan() {
        return id_pelanggan;
    }

    public void setId_pelanggan(String id_pelanggan) {
        this.id_pelanggan = id_pelanggan;
    }

    public String getNo_order() {
        return no_order;
    }

    public void setNo_order(String no_order) {
        this.no_order = no_order;
    }
}
