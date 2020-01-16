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

    public Pelanggan(String id, String nama, String email, String alamat, String no_hp, String password) {
        this.id = id;
        this.nama = nama;
        this.email = email;
        this.alamat = alamat;
        this.no_hp = no_hp;
        this.password = password;
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

}
