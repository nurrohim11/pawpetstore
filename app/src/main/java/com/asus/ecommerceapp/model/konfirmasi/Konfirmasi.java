package com.asus.ecommerceapp.model.konfirmasi;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Konfirmasi {
    public Konfirmasi(String no_order, String id_pelanggan, String jumlah, String ke_rekening, String dari_rekening, String an_rekening, String bukti_transfer) {
        this.no_order = no_order;
        this.id_pelanggan = id_pelanggan;
        this.jumlah = jumlah;
        this.ke_rekening = ke_rekening;
        this.dari_rekening = dari_rekening;
        this.an_rekening = an_rekening;
        this.bukti_transfer = bukti_transfer;
    }
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("no_order")
    @Expose
    private  String no_order;
    @SerializedName("id_pelanggan")
    private String id_pelanggan;
    @SerializedName("jumlah")
    @Expose
    private String  jumlah;
    @SerializedName("ke_rekening")
    @Expose
    private String  ke_rekening;
    @SerializedName("dari_rekening")
    @Expose
    private String  dari_rekening;
    @SerializedName("an_rekening")
    @Expose
    private String  an_rekening;
    @SerializedName("bukti_transfer")
    @Expose
    private String  bukti_transfer;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNo_order() {
        return no_order;
    }

    public void setNo_order(String no_order) {
        this.no_order = no_order;
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

    public String getKe_rekening() {
        return ke_rekening;
    }

    public void setKe_rekening(String ke_rekening) {
        this.ke_rekening = ke_rekening;
    }

    public String getDari_rekening() {
        return dari_rekening;
    }

    public void setDari_rekening(String dari_rekening) {
        this.dari_rekening = dari_rekening;
    }

    public String getAn_rekening() {
        return an_rekening;
    }

    public void setAn_rekening(String an_rekening) {
        this.an_rekening = an_rekening;
    }

    public String getBukti_transfer() {
        return bukti_transfer;
    }

    public void setBukti_transfer(String bukti_transfer) {
        this.bukti_transfer = bukti_transfer;
    }
}
