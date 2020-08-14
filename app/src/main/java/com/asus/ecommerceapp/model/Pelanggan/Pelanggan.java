package com.asus.ecommerceapp.model.Pelanggan;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Pelanggan {
    public Pelanggan(String nama, String email, String alamat, String no_hp, String password) {
        this.nama = nama;
        this.email = email;
        this.alamat = alamat;
        this.no_hp = no_hp;
        this.password = password;
    }

    public Pelanggan(String id, String nama, String email, String alamat, String no_hp, String password,String id_provinsi, String id_kota, String id_kecamatan, String kode_pos) {
        this.id = id;
        this.nama = nama;
        this.email = email;
        this.alamat = alamat;
        this.no_hp = no_hp;
        this.password = password;
        this.id_provinsi = id_provinsi;
        this.id_kota = id_kota;
        this.id_kecamatan = id_kecamatan;
        this.kode_pos = kode_pos;
    }

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("nama")
    @Expose
    private String nama;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("alamat")
    @Expose
    private String alamat;
    @SerializedName("no_hp")
    @Expose
    private String no_hp;
    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("id_provinsi")
    @Expose
    private String id_provinsi;
    @SerializedName("id_kota")
    @Expose
    private String id_kota;
    @SerializedName("id_kecamatan")
    @Expose
    private String id_kecamatan;
    @SerializedName("kode_pos")
    @Expose
    private String kode_pos;

    @SerializedName("provinsi")
    @Expose
    private String provinsi;
    @SerializedName("kota")
    @Expose
    private String kota;
    @SerializedName("kecamatan")
    @Expose
    private String kecamatan;

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getNo_hp() {
        return no_hp;
    }

    public void setNo_hp(String no_hp) {
        this.no_hp = no_hp;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getId_provinsi() {
        return id_provinsi;
    }

    public void setId_provinsi(String id_provinsi) {
        this.id_provinsi = id_provinsi;
    }

    public String getId_kota() {
        return id_kota;
    }

    public void setId_kota(String id_kota) {
        this.id_kota = id_kota;
    }

    public String getId_kecamatan() {
        return id_kecamatan;
    }

    public void setId_kecamatan(String id_kecamatan) {
        this.id_kecamatan = id_kecamatan;
    }

    public String getKode_pos() {
        return kode_pos;
    }

    public void setKode_pos(String kode_pos) {
        this.kode_pos = kode_pos;
    }

    public String getProvinsi() {
        return provinsi;
    }

    public void setProvinsi(String provinsi) {
        this.provinsi = provinsi;
    }

    public String getKota() {
        return kota;
    }

    public void setKota(String kota) {
        this.kota = kota;
    }

    public String getKecamatan() {
        return kecamatan;
    }

    public void setKecamatan(String kecamatan) {
        this.kecamatan = kecamatan;
    }
}
