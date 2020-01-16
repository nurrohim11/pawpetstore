package com.asus.ecommerceapp.model.keranjang;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class KeranjangItem{
	public KeranjangItem(String id_keranjang){
		this.id_keranjang = id_keranjang;
	}
    @SerializedName("id_keranjang")
    @Expose
	private String id_keranjang;
    @SerializedName("idProduk")
    @Expose
	private String idProduk;
    @SerializedName("jumlah")
    @Expose
	private String jumlah;
    @SerializedName("subtotal")
    @Expose
	private String subtotal;
    @SerializedName("idPelanggan")
    @Expose
	private String idPelanggan;
    @SerializedName("flag")
    @Expose
	private String flag;
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

	public void setIdKeranjang(String id_keranjang){
		this.id_keranjang = id_keranjang;
	}

	public String getIdKeranjang(){
		return id_keranjang;
	}

	public void setIdProduk(String idProduk){
		this.idProduk = idProduk;
	}

	public String getIdProduk(){
		return idProduk;
	}

	public void setJumlah(String jumlah){
		this.jumlah = jumlah;
	}

	public String getJumlah(){
		return jumlah;
	}

	public void setSubtotal(String subtotal){
		this.subtotal = subtotal;
	}

	public String getSubtotal(){
		return subtotal;
	}

	public void setIdPelanggan(String idPelanggan){
		this.idPelanggan = idPelanggan;
	}

	public String getIdPelanggan(){
		return idPelanggan;
	}

	public void setFlag(String flag){
		this.flag = flag;
	}

	public String getFlag(){
		return flag;
	}

	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return id;
	}

	public void setNama(String nama){
		this.nama = nama;
	}

	public String getNama(){
		return nama;
	}

	public void setKategori(String kategori){
		this.kategori = kategori;
	}

	public String getKategori(){
		return kategori;
	}

	public void setDeskripsi(String deskripsi){
		this.deskripsi = deskripsi;
	}

	public String getDeskripsi(){
		return deskripsi;
	}

	public void setHarga(String harga){
		this.harga = harga;
	}

	public String getHarga(){
		return harga;
	}

	public void setGambar(String gambar){
		this.gambar = gambar;
	}

	public String getGambar(){
		return gambar;
	}

//	@Override
// 	public String toString(){
//		return
//			"KeranjangItem{" +
//			"id_keranjang = '" + idKeranjang + '\'' +
//			",id_produk = '" + idProduk + '\'' +
//			",jumlah = '" + jumlah + '\'' +
//			",subtotal = '" + subtotal + '\'' +
//			",id_pelanggan = '" + idPelanggan + '\'' +
//			",flag = '" + flag + '\'' +
//			",id = '" + id + '\'' +
//			",nama = '" + nama + '\'' +
//			",kategori = '" + kategori + '\'' +
//			",deskripsi = '" + deskripsi + '\'' +
//			",harga = '" + harga + '\'' +
//			",gambar = '" + gambar + '\'' +
//			"}";
//		}
}