package com.asus.ecommerceapp.model.produk;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProdukDetailById {
    public ProdukDetailById(String nama, String kategori, String deskripsi, String harga, String gambar) {
        this.nama = nama;
        this.gambar = gambar;
        this.kategori = kategori;
        this.deskripsi = deskripsi;
        this.harga = harga;
    }

    public ProdukDetailById(String id, String nama, String kategori, String deskripsi, String harga, String gambar) {
        this.id = id;
        this.nama = nama;
        this.kategori = kategori;
        this.deskripsi = deskripsi;
        this.harga = harga;
        this.gambar = gambar;
    }

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("nama")
    @Expose
    private String nama;
    @SerializedName("kategori")
    @Expose
    private String kategori;
    @SerializedName("deskripsi")
    @Expose
    private String deskripsi;
    @SerializedName("harga")
    @Expose
    private String harga;
    @SerializedName("gambar")
    @Expose
    private String gambar;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getKategori() {
        return kategori;
    }

    public void setKategori(String kategori) {
        this.kategori = kategori;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public String getHarga() {
        return harga;
    }

    public void setHarga(String harga) {
        this.harga = harga;
    }

    public String getGambar() {
        return gambar;
    }

    public void setGambar(String gambar) {
        this.gambar = gambar;
    }
}
