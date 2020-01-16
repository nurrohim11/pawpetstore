package com.asus.ecommerceapp.model.penitipan;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OrderPenitipan {
    public OrderPenitipan(String id_pelanggan,String tgl_dari, String tgl_sampai) {
        this.id_pelanggan = id_pelanggan;
        this.tgl_dari = tgl_dari;
        this.tgl_sampai = tgl_sampai;
    }

    public OrderPenitipan(String id, String id_pelanggan, String tgl_dari, String tgl_sampai) {
        this.id = id;
        this.id_pelanggan = id_pelanggan;
        this.tgl_dari = tgl_dari;
        this.tgl_sampai = tgl_sampai;
    }

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("id_pelanggan")
    @Expose
    private String id_pelanggan;
    @SerializedName("tgl_dari")
    @Expose
    private String tgl_dari;
    @SerializedName("tgl_sampai")
    @Expose
    private String tgl_sampai;

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

    public String getTgl_dari() {
        return tgl_dari;
    }

    public void setTgl_dari(String tgl_dari) {
        this.tgl_dari = tgl_dari;
    }

    public String getTgl_sampai() {
        return tgl_sampai;
    }

    public void setTgl_sampai(String tgl_sampai) {
        this.tgl_sampai = tgl_sampai;
    }

}

