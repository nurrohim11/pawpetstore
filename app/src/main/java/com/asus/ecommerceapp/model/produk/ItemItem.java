package com.asus.ecommerceapp.model.produk;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

public class ItemItem implements Serializable {

	@SerializedName("id_order")
	private String idOrder;

	@SerializedName("qty")
	private String qty;

	@SerializedName("id_produk")
	private String idProduk;

	@SerializedName("subtotal")
	private String subtotal;

	@SerializedName("id_pelanggan")
	private String idPelanggan;

	@SerializedName("no_order")
	private String noOrder;

	@SerializedName("id")
	private String id;

	@SerializedName("nama")
	private String nama;

	@SerializedName("kategori")
	private String kategori;

	@SerializedName("deskripsi")
	private String deskripsi;

	@SerializedName("harga")
	private String harga;

	@SerializedName("gambar")
	private String gambar;

	public void setIdOrder(String idOrder){
		this.idOrder = idOrder;
	}

	public String getIdOrder(){
		return idOrder;
	}

	public void setQty(String qty){
		this.qty = qty;
	}

	public String getQty(){
		return qty;
	}

	public void setIdProduk(String idProduk){
		this.idProduk = idProduk;
	}

	public String getIdProduk(){
		return idProduk;
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

	public void setNoOrder(String noOrder){
		this.noOrder = noOrder;
	}

	public String getNoOrder(){
		return noOrder;
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

	@Override
 	public String toString(){
		return 
			"ItemItem{" + 
			"id_order = '" + idOrder + '\'' + 
			",qty = '" + qty + '\'' + 
			",id_produk = '" + idProduk + '\'' + 
			",subtotal = '" + subtotal + '\'' + 
			",id_pelanggan = '" + idPelanggan + '\'' + 
			",no_order = '" + noOrder + '\'' + 
			",id = '" + id + '\'' + 
			",nama = '" + nama + '\'' + 
			",kategori = '" + kategori + '\'' + 
			",deskripsi = '" + deskripsi + '\'' + 
			",harga = '" + harga + '\'' + 
			",gambar = '" + gambar + '\'' + 
			"}";
		}
}