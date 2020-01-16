package com.asus.ecommerceapp.model.grooming;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OrderGrooming {
    public OrderGrooming(String id_pelanggan,String tgl_booking, String jenis, String jenis_hewan, String ukuran) {
        this.id_pelanggan = id_pelanggan;
        this.tgl_booking = tgl_booking;
        this.jenis = jenis;
        this.jenis_hewan = jenis_hewan;
        this.ukuran = ukuran;
    }
    public OrderGrooming(String id, String id_pelanggan,String tgl_booking, String jenis, String jenis_hewan, String ukuran) {
        this.id = id;
        this.id_pelanggan = id_pelanggan;
        this.tgl_booking = tgl_booking;
        this.jenis = jenis;
        this.jenis_hewan = jenis_hewan;
        this.ukuran = ukuran;
    }

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("id_pelanggan")
    @Expose
    private String id_pelanggan;
    @SerializedName("tgl_booking")
    @Expose
    private String tgl_booking;
    @SerializedName("jenis")
    @Expose
    private String jenis;
    @SerializedName("jenis_hewan")
    @Expose
    private String jenis_hewan;
    @SerializedName("ukuran")
    @Expose
    private String ukuran;

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

    public String getTgl_booking() {
        return tgl_booking;
    }

    public void setTgl_booking(String tgl_booking) {
        this.tgl_booking = tgl_booking;
    }

    public String getJenis() {
        return jenis;
    }

    public void setJenis(String jenis) {
        this.jenis = jenis;
    }

    public String getJenis_hewan() {
        return jenis_hewan;
    }

    public void setJenis_hewan(String jenis_hewan) {
        this.jenis_hewan = jenis_hewan;
    }

    public String getUkuran() {
        return ukuran;
    }

    public void setUkuran(String ukuran) {
        this.ukuran = ukuran;
    }

}
